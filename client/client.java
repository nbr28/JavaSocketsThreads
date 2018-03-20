package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import accountType.Account;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.Socket;

public class client {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int userSelction = -1;

		Socket clientSocket; // TCP/IP socket

		try {
			clientSocket = new Socket(InetAddress.getByName("localhost"), Integer.parseInt(args[0]));
			DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream sendData = new DataOutputStream(clientSocket.getOutputStream());
			ObjectInputStream recvObj = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Connected to " + clientSocket.getInetAddress().getHostName());

			String bankMessage = dataIn.readUTF();
			System.out.println(bankMessage);

			String username = scanner.nextLine();
			sendData.writeUTF(username);
			sendData.flush();

			do {
				bankMessage = dataIn.readUTF();
				System.out.println(bankMessage);
				userSelction = scanner.nextInt();

				sendData.writeUTF(String.valueOf(userSelction));
				sendData.flush();

				switch (userSelction) {
				case 1:
					sendData.writeInt(userSelction);
					ArrayList<Account> accList = (ArrayList<Account>) recvObj.readObject();
					System.out
							.println("information about ALL accounts received, displayed LINE BY LINE as seen below:");
					printAccounts(accList);
					break;
				case 2:
					sendData.writeInt(userSelction);
					System.out.println("# question from the server: " + dataIn.readUTF());
					//sed.writeObject(new BigDecimal(scanner.nextLine()));// send big decimal
					//ArrayList<Account> accList1 = (ArrayList<Account>) recvObj.readObject();
					//printAccounts(accList1);

					break;
				case 0:
					sendData.writeInt(userSelction);
					System.out.println("Thank You!");
					break;

				default:
					System.out.println("Invalid Input!");
					break;
				}

			} while (userSelction != 0);

			sendData.close();
			dataIn.close();
			recvObj.close();
			clientSocket.close();
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}

		System.out.println("the client is going to stop runing...");
		scanner.close();
	} // end main

	private static void printAccounts(ArrayList<Account> accList) {
		int count = 0;
		for (Account account : accList) {
			System.out.println((++count) + ". " + account);

		}

	}
}
