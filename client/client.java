package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import accountType.Account;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class client {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int userSelction = -1;

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a MMM d yyyy");
		String s2 = new StringBuilder("Welcome to the Bank of Natan Ross and Tony Sim! ").append(now.format(format))
				.toString();
		System.out.println(s2);// welcome message
		System.out.print("Enter your name: ");
		String username = scanner.nextLine();

		Socket clientSocket; // TCP/IP socket

		try {
			clientSocket = new Socket(InetAddress.getByName("localhost"), Integer.parseInt(args[0]));
			ObjectInputStream oisFromServer = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream oosToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			System.out.println("Connected to " + clientSocket.getInetAddress().getHostName());

			oosToServer.writeObject(username);
			oosToServer.flush();
			do {
				System.out.print("Enter a command (0, 1 or 2): ");
				userSelction = scanner.nextInt();

				oosToServer.writeObject(String.valueOf(userSelction));
				oosToServer.flush();

				switch (userSelction) {
				case 1:
					Account acc[] = (Account[]) oisFromServer.readObject();
					System.out
							.println("information about ALL accounts received, displayed LINE BY LINE as seen below:");
					int i = 0;
					for (Account acct : acc) {
						i++;
						System.out.println(i + ".  " + ((Account) acct).toString());
					}
					break;
				case 2:
					System.out.println("# question from the server: " + (String) oisFromServer.readObject());
					oosToServer.writeObject(new BigDecimal(scanner.nextLine()));//send big decimal
					ArrayList<Account> accList = (ArrayList<Account>)oisFromServer.readObject();
					
					int count=0;
					for (Account account : accList) {
						System.out.println((++count)+". "+account);
						
					}
					break;
				case 0:
					System.out.println("Thank You!");
					break;
					
				default:
					System.out.println("Invalid Input!");
					break;
				}

			} while (userSelction != 0);

			oosToServer.close();
			oisFromServer.close();
			clientSocket.close();
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
		}

		System.out.println("the client is going to stop runing...");
		scanner.close();
	} // end main
}
