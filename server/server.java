package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import BankClasses.Bank;
import BankClasses.BankApp;

public class server {

	public static void main(String[] args) {
		Bank bank = new Bank();
		BankApp.loadBank(bank);// load some accounts
		ServerSocket serverSocket; // TCP socket used for listening

		try {
			/*
			 * step 1: create a server socket port number: 8000
			 */

			serverSocket = new ServerSocket(Integer.parseInt(args[0]));

			/*
			 * setp 2: listen for a connection and create a socket
			 */
			System.out.println("*** this server is going register the cars ***");
			System.out.println("listening for a connection...");

			Socket socketConnection = serverSocket.accept(); // listen and wait
																// TCP socket that is connected
																// to the client

			/*
			 * step 3: connect input and output streams to the socket
			 */
			ObjectOutputStream oosToClient = new ObjectOutputStream(socketConnection.getOutputStream());

			ObjectInputStream oisFromClient = new ObjectInputStream(socketConnection.getInputStream());

			System.out.println("I/O streams connected to the socket");

			/*
			 * step 4: exchange objects with THE client
			 */

			try {
				int m = 1000;

				oosToClient.writeObject(bank.searchByAccountNumber("A1234"));
				oosToClient.flush();

				// display the result to the screen of the server
				System.out.println("\t******* send the object to the CLIENT");

			}
			catch (EOFException eof) {
				System.out.println("*** THE client has terminated connection ***");
			} catch (IOException e) {
				e.printStackTrace();
			}

			/*
			 * step 5: close the connection to the client
			 */
			oosToClient.close();
			oisFromClient.close();
			socketConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("*** the server is going to stop running ***");

	} // end main
}
