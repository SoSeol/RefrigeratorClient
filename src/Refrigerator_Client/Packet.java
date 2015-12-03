package Refrigerator_Client;

import java.io.IOException;
import OCSF.Client.ChatIF;
import OCSF.Common.ChatClient;

public class Packet implements ChatIF {

	private ChatClient client;
	private UI ui;

	public Packet(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
			ui = new UI(client);
		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}
	}

	// Process from login to Exit
	public void accept() {
		try {
			int result = 0;
			int Trying = 0;
			System.out.println("Welcome to HW_Refrigerator_System!");

			while (true) {
				ui.Login();
				if (ui.GetStatus() != UserStatus.MENU) {
					Trying++;
					System.out.println("Trying " + Trying + "/3");
				}
				if (ui.GetStatus() == UserStatus.MENU) {
					Trying = 0;
					// main menu
					System.out.println("=============H W R M============");
					// At login, print Messages
					ui.GetMessage();
					while (true) {
						result = ui.Menu();
						if (result == 0)
							break;
					}
					System.exit(0);
				}

				if (Trying == 3) {
					System.out
							.println("Unable to Login to Server after 3 attempts. exit.");
					System.exit(0);
				}
			}
		} catch (Exception ex) {
			System.out.println("Unexpected error while reading from console!");
		}
	}

	// Receive String data from Server and check Server's work result
	public void display(String message) {

		String[] Packet = message.split("_");
		// check login
		String cmd = Packet[0];
		if (cmd.equals("ALREADY")) {
			System.out.println("Already Logged in");
			System.out.println(">Login Fail!!!");
			ui.SetStatus(UserStatus.LOGIN_FAIL);
		} else if (Packet[0].equals("LOGIN")) {
			if (Packet[1].equals("FALSE")) {
				System.out.println(">Login Fail!!!");
				ui.SetStatus(UserStatus.LOGIN_FAIL);
			} else if (Packet[1].equals("TRUE")) {
				System.out.println(">Login Sucess!!!");
				ui.SetStatus(UserStatus.MENU);
				if (Packet[2].equals("ADMINISTRATOR")) {
					ui.setAuthority(true);
					ui.setName(Packet[3]);
				} else if (Packet[2].equals("NORMALUSER")) {
					ui.setAuthority(false);
					ui.setName(Packet[3]);
				}
			}
		}
		// check food handle
		else if (cmd.equals("FOOD")) {
			String cmd2 = Packet[1];
			if (cmd2.equals("MODIFY")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Food Modify Success!");
					ui.SetStatus(UserStatus.DONE);
				} else if (result.equals("FALSE")) {
					System.out.println("Food Modify Fail");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd2.equals("DELETE")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Food Delete Success!");
					ui.SetStatus(UserStatus.DONE);
				} else if (result.equals("FALSE")) {
					System.out.println("Food Delete Fail");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd2.equals("REGISTER")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Food Register Success!");
					ui.SetStatus(UserStatus.DONE);
				} else if (result.equals("FALSE")) {
					System.out.println("Food Register Fail!");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd2.equals("SEARCH")) {
				if (Packet[2] != null)
					System.out.println(Packet[2]);
				else
					System.out.println("Not found!");
				ui.SetStatus(UserStatus.DONE);
			} else {
				System.out.println("----------Food List----------");
				if (cmd2.equals("Refrigerator is EMPTY!")) {
					System.out.println("Go to menu");
					ui.SetStatus(UserStatus.FOOD_EMPTY);
				} else {
					System.out.println(cmd2);
					ui.SetStatus(UserStatus.FOOD_LOAD);
				}
			}
		}
		// check user handle
		else if (cmd.equals("USER")) {
			String cmd1 = Packet[1];
			String cmd2 = Packet[2];

			if (cmd1.equals("MODIFY")) {
				if (cmd2.equals("TRUE")) {
					System.out.println("User Modify Sucess!");
					ui.SetStatus(UserStatus.DONE);
				}
				if (cmd2.equals("FALSE")) {
					System.out.println("User Modify Fail!");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd1.equals("DELETE")) {
				if (cmd2.equals("TRUE")) {
					System.out.println("User Delete Success!");
					ui.SetStatus(UserStatus.DONE);
				}
				if (cmd2.equals("FALSE")) {
					System.out.println("User Delete Fail");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd1.equals("REGISTER")) {
				if (cmd2.equals("TRUE")) {
					System.out.println("User Register Success!");
					ui.SetStatus(UserStatus.DONE);
				}
				if (cmd2.equals("FALSE")) {
					System.out.println("User Register Fail");
					ui.SetStatus(UserStatus.DONE);
				}
			} else if (cmd1.equals("INFO")) {
				if (Packet[4].equals("TRUE")) {
					System.out.println("User Info Success!");
					ui.SetStatus(UserStatus.DONE);
					ui.setId(Packet[2]);
					ui.setName(Packet[3]);
				} else if (Packet[4].equals("FALSE")) {
					System.out.println("User Info Fail!");
					ui.SetStatus(UserStatus.DONE);
				}
				ui.SetStatus(UserStatus.DONE);
			} else {
				System.out.println(Packet[2]);
				ui.SetStatus(UserStatus.USER_LOAD);
			}
		}
		// check message handle
		else if (cmd.equals("MSG")) {
			String cmd2 = Packet[1];
			if (cmd2.equals("SHOW")) {
				String Mlist = Packet[2];
				System.out.println("----------Message List----------");
				System.out.println(Mlist);
				ui.SetStatus(UserStatus.MSG_LOAD);
			} else if (cmd2.equals("MEMO")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Memo Success!");
					ui.SetStatus(UserStatus.DONE);
				}

				else if (result.equals("FALSE")) {
					System.out.println("Memo Fail!");
					ui.SetStatus(UserStatus.DONE);
				}
				ui.SetStatus(UserStatus.DONE);
			} else if (cmd2.equals("DELETE")) {
				String result = Packet[2];
				if (result.equals("TRUE")) {
					System.out.println("Message Delete Success!");
					ui.SetStatus(UserStatus.DONE);
				} else if (result.equals("FALSE")) {
					System.out.println("Message Delete Fail!");
					ui.SetStatus(UserStatus.DONE);
				}
				ui.SetStatus(UserStatus.DONE);
			}
		}
	}
}
