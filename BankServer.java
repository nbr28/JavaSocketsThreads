

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;

import BankClasses.Bank;
import BankClasses.BankApp;

public class BankServer {
	private Bank bank;
	private ServerSocket serverSocket;

	public BankServer(int port) throws IOException {
		bank = new Bank("R&S Bank");
		serverSocket = new ServerSocket(port);
	}

	public void startServer() {
		BankApp.loadBank(bank);// load some accounts

		try {
			try { /* allows finally to close socket */
				/* log initialization */
				System.out.println("*** " + bank.getName() + " Server System ***");

				/* listen for new connection forever, pushing each thread into ArrayList */
				for (;;) {
					System.out.println("listening for a connection...");
					Thread t = new ServerThread(serverSocket.accept(), bank);
					t.start();
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

	}

	public static void main(String[] args) {
		/* create new Server */
		try {
			BankServer serv = new BankServer(Integer.parseInt(args[0]));
			serv.startServer();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	} // end main
}
