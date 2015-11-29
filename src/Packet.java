import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import client.ChatClient;

import common.ChatIF;

public class Packet implements ChatIF {
	
	private ChatClient client;
	
	/**
	 * Construct
	 * @param host
	 * @param port
	 */
	public Packet(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
	}

	public void Login() {
		Scanner scan = new Scanner(System.in);
		String id = "";
		String pw = "";
		
		System.out.println("ID : ");
		id = scan.nextLine();
		System.out.println("PW : ");
		pw = scan.nextLine();
		
		client.handleMessageFromClientUI("LOGIN_"+id+"_"+pw);
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

			//stage1 Login
			System.out.println("Welcome to HW_Refrigerator_System!!");
			Login();
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
		
		//Login
		if(cmd.equals("LOGIN"))
		{
			//Login fail
			if(Packet[1].equals("0"))
			{
				Login();
			}
			//Login success
			else
			{
				System.out.println("Login Sucess!!!");
			}
		}
		//Get Food List
		else if(cmd.equals("FLIST"))
		{
			String Flist = Packet[1];
			DisplayFood(Flist);
		}
		//Get Message List
		else if(cmd.equals("MLIST"))
		{
			String Mlist = Packet[1];
			DisplayMessage(Mlist);
		}
		//
		else if(cmd.equals(""))
		{
			
		}
		System.out.println("> " + message);
	}
	
	public void DisplayUser(String message)
	{
		System.out.println("User List");
		System.out.println(message);
	}
	
	public void DisplayMessage(String message)
	{
		System.out.println("Message List");
		System.out.println(message);
	}
	
	public void DisplayFood(String message)
	{
		System.out.println("Food List");
		System.out.println(message);
	}
}
