import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import client.ChatClient;
import common.ChatIF;

public class Packet implements ChatIF {

	private ChatClient client;
	private GUI gui;

	public Packet(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
			gui = new GUI(client);

		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
	}

	public void accept() {
		try {
			BufferedReader fromConsole = new BufferedReader(
					new InputStreamReader(System.in));
			String message;
			int result = 0;
			while(true)
			{
				// [stage1] Login
				System.out.println("Welcome to HW_Refrigerator_System!!");
				gui.Login();
				while (true) {
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e){
						System.out.println(e.getMessage()); //sleep 메소드가 발생하는 InterruptedException 
					}
					System.out.print(".");
					if (gui.GetStatus() == UserStatus.MENU)
						break;
				}
				// [stage2] show MsgList
				gui.GetMessage();
	
				// [stage3] Menu
				while(true)
				{
					result = gui.Menu();
					if(result == 1)
					{
						gui.SetStatus(UserStatus.LOGIN);
						break;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	public void display(String message) {
		System.out.println("> " + message);
		
		String[] Packet = message.split("_");
		String cmd = Packet[0];
		
		// Login 부분
		if (cmd.equals("LOGIN")) {
			// Login fail
			if (Packet[1].equals("FALSE")) {
				System.out.println("Login Fail!!!");
			}
			// Login success( Status change)
			else if (Packet[1].equals("TRUE")) {
				gui.SetStatus(UserStatus.MENU);
				System.out.println("Login Sucess!!!");
			}
		}
		// FOOD 부분
		else if (cmd.equals("FOOD")) {
			String cmd2 = Packet[1];
			if(cmd2.equals("MODIFY"))
			{
				String result = Packet[2];
				if(result.equals("TRUE"))
				{
					System.out.println("Food Modify Success");
				}
				else if(result.equals("FALSE"))
				{
					System.out.println("Food Modify Fail");
				}
			}
			else if(cmd2.equals("DELETE"))
			{
				String result = Packet[2];
				if(result.equals("TRUE"))
				{
					System.out.println("Food delete Success");
				}
				else if(result.equals("FALSE"))
				{
					System.out.println("Food delete Fail");
				}
			}
			else if(cmd2.equals("REGISTER"))
			{
				String result = Packet[2];
				if(result.equals("TRUE"))
				{
					System.out.println("Food Register Success");
				}
				else if(result.equals("FALSE"))
				{
					System.out.println("Food Register Fail");
				}
			}
			// Print Food List
			else
			{
				System.out.println("-----Food List-----");
				System.out.println(cmd2);
			}
		}
		//USER 부분
		else if(cmd.equals("USER"))
		{
			String cmd1 = Packet[1];
			String cmd2 = Packet[2];
					
			if(cmd1.equals("MODIFY"))
			{
				if(cmd2.equals("TRUE"))
					System.out.println("USER MODIFY Sucess!!!");
				if(cmd2.equals("FALSE"))
					System.out.println("USER MODIFY FAIL !!!");
			}
			else if(cmd1.equals("DELETE"))
			{
				if(cmd2.equals("TRUE"))
					System.out.println("USER DELETE Sucess!!!");
				if(cmd2.equals("FALSE"))
					System.out.println("USER DELETE FAIL !!!");			
			}
			else if(cmd1.equals("REGISTER"))
			{
				if(cmd2.equals("TRUE"))
					System.out.println("USER REGISTER Sucess!!!");
				if(cmd2.equals("FALSE"))
					System.out.println("USER REGISTER FAIL !!!");
			}
		}

		// 메세지 부분
		else if (cmd.equals("MSG")) {
			String cmd2 = Packet[1];
			if (cmd2.equals("SHOW")) {
				String Mlist = Packet[2];
				System.out.println("-----Message List-----");
				System.out.println(Mlist);
			} 
			else if (cmd2.equals("DELETE")) 
			{
				String result = Packet[2];

				// Old message delete success
				if (result.equals("TRUE")) {
					System.out.println("Message Delete Success");
				}
				// delete fail
				else if (result.equals("FALSE")) {
					System.out.println("Message Delete fail :(");
				}
			}
		}
	}

}
