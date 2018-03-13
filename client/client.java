package client;

import java.io.IOException;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class client {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int userSelction = 0;

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a MMM d yyyy");
		String s2 = new StringBuilder("Welcome to the Bank of Natan Ross and Tony Sim! ").append(now.format(format))
				.toString();
		System.out.println(s2);// welcome message
		System.out.print("Enter your name: ");

		Socket clientSocket; // TCP/IP socket
		ObjectInputStream oisFromServer;
		ObjectOutputStream oosToServer;
		try {
			clientSocket = new Socket(InetAddress.getByName("localhost"), Integer.parseInt(args[0]));

			System.out.println("Connected to " + clientSocket.getInetAddress().getHostName());

			String username = scanner.nextLine();
			
			do {
				System.out.print("Enter a command (0, 1 or 2): ");
				userSelction = scanner.nextInt();

			} while (userSelction != 0);

			oisFromServer = new ObjectInputStream(clientSocket.getInputStream());

			oosToServer = new ObjectOutputStream(clientSocket.getOutputStream());

			oosToServer.close();
			oisFromServer.close();
			clientSocket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		System.out.println("the client is going to stop runing...");
		scanner.close();
	} // end main
}
