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


public class client {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int userSelction = -1;



		Socket clientSocket; // TCP/IP socket

		try {
			clientSocket = new Socket(InetAddress.getByName("localhost"), Integer.parseInt(args[0]));
			ObjectInputStream oisFromServer = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream oosToServer = new ObjectOutputStream(clientSocket.getOutputStream());
			System.out.println("Connected to " + clientSocket.getInetAddress().getHostName());
            
			String bankMessage=oisFromServer.readUTF();
			System.out.println(bankMessage);
			
			String username = scanner.nextLine();
			oosToServer.writeObject(username);
			oosToServer.flush();
			
			do {
				bankMessage=(String)oisFromServer.readUTF();
				System.out.println(bankMessage);
				userSelction = scanner.nextInt();

				oosToServer.writeObject(String.valueOf(userSelction));
				oosToServer.flush();

				switch (userSelction) {
				case 1:
					oosToServer.writeInt(userSelction);
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
					oosToServer.writeInt(userSelction);
					System.out.println("# question from the server: " + (String) oisFromServer.readObject());
					oosToServer.writeObject(new BigDecimal(scanner.nextLine()));//send big decimal
					ArrayList<Account> accList = (ArrayList<Account>)oisFromServer.readObject();
					
					int count=0;
					for (Account account : accList) {
						System.out.println((++count)+". "+account);
						
					}
					break;
				case 0:
					oosToServer.writeInt(userSelction);
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
		} catch (IOException | ClassNotFoundException   ioe) {
			ioe.printStackTrace();
		}

		System.out.println("the client is going to stop runing...");
		scanner.close();
	} // end main
}
