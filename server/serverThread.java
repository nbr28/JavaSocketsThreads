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
	private String menu0 = "==Menu\n1. List all accounts\n2. Filter by balance\n0. Quit\n Enter a command (0, 1 or 2): ";
	private String menu1 = "==Filter by Balance\nWhat is the balance? ";
	private int reply = -1;

	/**
	 * Constructor
	 * 
	 * @param sock
	 * @param inputBank
	 */
	public serverThread(Socket sock, Bank inputBank) {
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
	 * Sends the result 
	 * 
	 * @param result
	 * @throws IOException
	 */
	private void sendResult(ArrayList<Account> result) throws IOException {
		sendObj.writeObject(result.toArray(new Account[0]));
		sendObj.flush();
	}

	/**
	 * Runs thread
	 */
	public void run() {
		try {
			try { /* this allows finally to close sockets */
				/* send and log welcome message */
				System.out.println("A BankServer instance is running! " + now.format(format));
				send.writeUTF("Welcome to the Bank of R&S! " + now.format(format) + "\nPlease enter your name: ");
				send.flush();
				/* receive user name information */
				user = recv.readUTF();
				System.out.println("+ name received: " + user);

				/* process reply in a loop */
				while(reply != 0){
					/* send initial command choice request */
					
					send.writeUTF(menu0);
				
					send.flush();
					
					reply = recv.readInt();
					System.out.println("+ command received: " + reply);
					switch(reply){
						case 1: /* send all account */
							sendResult(bank.getAcc());
							break;
						case 2: /* send search result based on balance */
							ArrayList<Account> result = new ArrayList<Account>();
							send.writeUTF(menu1);
							send.flush();
							/* receive query for balance */
							String balance = recv.readUTF();
							/* log balance received */
							System.out.println("+ balance received: " + balance);
							/* sendResult() the return of the searchByBalance(BigDecimal) using the query */
							try{
								result = bank.searchByBalance(new BigDecimal(balance) );
								System.out.println("+ balance received: In Big Decimal " + new BigDecimal(balance));
							}catch(NumberFormatException e) { 
								System.out.println("ERROR: received balance is an invalid entry");
							}finally {
								sendResult(result);
							}

							break;
						case 0:
							System.out.println("+ User " + user + " signing out at " + LocalDateTime.now().format(format) );
							break;
						default:
							throw new Exception("Invalid reply received");
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
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage() );
		}
	}
}
