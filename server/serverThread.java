package server;

/**
 * Task : design a thread server object
 * Author : Yoosuk Sim
 * Date : 2018/03/19
 * Detail : This is a part of lab 5, Java, 2017-2018 Winter
 */
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.*;
import java.math.BigDecimal;

import BankClasses.*;
import accountType.*;

/**
 * A server thread for networked banking app
 * 
 * @author yoosuk
 *
 */
public class serverThread extends Thread {
	private Socket conn;
	private Bank bank;
	private String user;
	private DataOutputStream send;
	private DataInputStream recv;
	private ObjectOutputStream sendObj;
	private LocalDateTime now = LocalDateTime.now();
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a MMM d yyyy");
	private int state;
	private String menu0 = "==Menu\n1. List all accounts\n2. Filter by balance\n0. Quit\n Enter a command (0, 1 or 2): ";
	private String menu1 = "==Filter by Balance\nWhat is the balance? ";
	private String reply = "";

	/**
	 * Constructor
	 * 
	 * @param sock
	 * @param inputBank
	 */
	public serverThread(Socket sock, Bank inputBank) {
		state = 1; /* set state to initial */
		try {
			conn = sock;
			bank = inputBank;
			send = new DataOutputStream(conn.getOutputStream());
			recv = new DataInputStream(conn.getInputStream());
			sendObj = new ObjectOutputStream(conn.getOutputStream());
		} catch (IOException e) {
			System.out.println("ERROR: Thread constructor failed!");
			conn = null;
			bank = null;
			send = null;
			recv = null;
			sendObj = null;
			e.printStackTrace();
		}
	}

	/**
	 * Is this thread running or not running?
	 * 
	 * The idea is to allow a separate thread to check if this thread is active and if not, remove it from the ArrayList.  Due to synchronization issues with ArrayList, this has not been implemented yet.
	 * @return boolean
	 */
	public boolean running() {
		return state != 0;
	}

	/**
	 * Sends the result and reinitializes the state
	 * 
	 * @param result
	 * @throws IOException
	 */
	private void sendResult(ArrayList<Account> result) throws IOException {
		state = 1; /* reset state to initial */
		sendObj.writeObject(result);
		sendObj.flush();
		send.writeChars(menu0);
	}

	/**
	 * Runs thread
	 */
	public void run() {
		try {
			try { /* this allows finally to close sockets */
				/* send and log welcome message */
				System.out.println("A BankServer instance is running! " + now.format(format));
				send.writeChars("Welcome to the Bank of R&S! " + now.format(format));
				send.flush();
				/* receive user name information */
				user =(String) recv.readUTF();
				System.out.println("+ name received: " + user);

				/* send initial command choice request */
				send.writeChars(menu0);

				/* process reply in a loop */
				while (reply != "0") {
					reply = Integer.toString( recv.readInt());
					if (reply != "0") {
						if (state == 1) { /* initial state */
							state = Integer.parseInt(reply);
							if (state == 1) { /* choice 1 */
								sendResult(bank.getAcc());
							} else if (state == 2) { /* choice 2 */
								send.writeChars(menu1);
							}
						} else if (state == 2) { /* continued from last loop for menu 2 */
							sendResult(bank.searchByBalance(new BigDecimal(reply)));
						}
					}
				}
			} finally {
				/* close sockets */
				send.close();
				recv.close();
				sendObj.close();
				conn.close();
			}
		} catch (EOFException eof) {
			System.out.println("ERROR: Line disconnected");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			state = 0; /* inactive state */
		}
	}
}
