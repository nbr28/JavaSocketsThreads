package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import BankClasses.Bank;
import BankClasses.BankApp;

public class server {
	private static Bank bank;
	private static ServerSocket serverSocket;
	private static ArrayList<Thread> clients;

	public static void main(String[] args) {
		bank = new Bank();
		BankApp.loadBank(bank);// load some accounts

		try {
			try { /* allows finally to close socket */
				/* create ServerSocket object */
				serverSocket = new ServerSocket(8000);

				/* log initialization */
				System.out.println("*** R&S Bank Server System ***");

				/* listen for new connection forever, pushing each thread into ArrayList */
				for (;;) {
					System.out.println("listening for a connection...");
					Thread t = new serverThread(serverSocket.accept(), bank);
					t.start();
					clients.add(t);
				}
			} finally {
				System.out.println("*** the server is going to stop running ***");
				serverSocket.close();
			}
		} catch (EOFException eof) {
			System.out.println("*** THE client has terminated connection ***");
			eof.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // end main
}
