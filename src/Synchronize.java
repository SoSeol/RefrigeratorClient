import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import common.ChatIF;
import Essential.*;
import Problem_Domain.*;
import client.*;

public class Synchronize implements ChatIF {
	private RefrigeratorSystem refriger;
	private ChatClient client;
	private User MyUser;

	/**
	 * Construct
	 * @param host
	 * @param port
	 */
	public Synchronize(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
	}

	public void GetUserList() {
		client.handleMessageFromClientUI("GET_USER");
	}

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message
	 *            The string to be displayed.
	 */
	// Instance methods ************************************************

	/**
	 * This method waits for input from the console. Once it is received, it
	 * sends it to the client's message handler.
	 */
	public void accept() {
		try {
			BufferedReader fromConsole = new BufferedReader(
					new InputStreamReader(System.in));
			String message;

			//stage1 Get User list
			GetUserList();
			while (true) {
				message = fromConsole.readLine();
				client.handleMessageFromClientUI(message);
			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}
	
	public void display(String message) {
		String[] Packet = message.split("_");
		String cmd = Packet[0];
		
		if(cmd.equals("ULIST"))
		{
			String Ulist = Packet[1];
			DisplayUser(Ulist);
		}
		System.out.println("> " + message);
	}
	
	public void DisplayUser(String message)
	{
		System.out.println("User List");
		System.out.println(message);
	}
}
