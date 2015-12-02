import java.io.Console;
import java.util.Scanner;

import client.ChatClient;

public class UI {
	private ChatClient client;
	private String id;
	private String pw;
	private String name;
	private boolean authority;
	private UserStatus status;
	
	public UI(ChatClient c) {
		client = c;
		status = UserStatus.LOGIN;
	}

	public UserStatus GetStatus() {
		return status;
	}

	public void SetStatus(UserStatus stat) {
		status = stat;
	}

	public boolean isAuthority() {
		return authority;
	}

	public void setAuthority(boolean authority) {
		this.authority = authority;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void Login() {
		if (GetStatus() == UserStatus.LOGIN) {

			// Scanner scan = new Scanner(System.in);
			// System.out.print("ID : ");
			// id = scan.nextLine();
			// System.out.print("PW : ");
			// pw = scan.nextLine();

			/*
			 * p@ Ŭ���̾�Ʈ�� cmd���� ������ �۵�(eclipse������ �ȵʤ�); ��Ŭ�������� �����Ϸ��� ���� 5�� �ּ� ����,
			 * �ؿ� 6�� �ּ�ó��
			 */
			Console secret = System.console();
			if (secret == null)
				System.err.println("Console fail");
			id = secret.readLine("%s", "ID : ");
			pw = new String(secret.readPassword("%s", "PW : "));			

			client.handleMessageFromClientUI("LOGIN_" + id + "_" + pw);
			WaitLogin();
		}
	}

	public void WaitLogin() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage()); // sleep �޼ҵ尡 �߻��ϴ�
													// InterruptedException
			}
			// p@ System.out.print(".");

			if (GetStatus().equals(UserStatus.LOGIN_FAIL)) {
				SetStatus(UserStatus.LOGIN);
				break;
			} else if (GetStatus().equals(UserStatus.MENU)) {
				break;
			}
		}
	}

	public int Menu() {
		Scanner scan = new Scanner(System.in);		
		int choice;
		
		// System.out.println("=========Menu=========");

		if (isAuthority())
			System.out.println("1.User Manage");
		else
			System.out.println("1.User Info");

		System.out.println("2.Register Food");
		System.out.println("3.Edit Food");
		System.out.println("4.Memo");
		System.out.println("5.Logout");
		System.out.println("0.Exit");
		System.out.print("What do you want?(choose number)> ");

		do {
			choice = scan.nextInt();
			scan.nextLine();
			switch (choice) {
			case 0:
				choice = 0;
				break;
			case 1:
				if (isAuthority())
					UserManage();
				else
					UserInfo();
				choice = 5;
				break;
			case 2:
				FoodRegister();
				break;
			case 3:
				FoodEdit();
				break;
			case 4:
				Memo();
				break;
			case 5:
				choice = 1;
				break;
			default:
				System.out.print("Choose number between 0 and 5 > ");
				break;
			}
		} while (choice < 0 || choice > 5);

		scan.close();
		return choice;
	}

	/**
	 * Menu
	 * 
	 * @return ���� �޴� ��ȣ
	 */

	/*
	 * p@ ���� ������ ��ü�մϴ� // �α׾ƿ� if (choice == 1) { if (id.equals("admin")) {
	 * GetUser(); System.out .print(
	 * "How to manage User?(register user : 1, modify user : 2, delete user : 3, back : 0) : "
	 * ); do { choice = scan.nextInt(); if (choice == 1) UserRegister(); else if
	 * (choice == 2) UserModify(); else if (choice == 3) UserDelete(); } while
	 * (choice > 3); } else System.out.println("1.User Info"); return choice; }
	 * // ���� ���� else if (choice == 2) { FoodModify(); } // ���� ���� else if (choice
	 * == 3) { FoodDelete(); } // ���� ��� else if (choice == 4) { FoodRegister();
	 * } // ���� �˻� else if (choice == 5) { FoodSearch(); } if
	 * (id.equals("admin")) { // User ���� if (choice == 6) { UserModify(); } //
	 * User ���� else if (choice == 7) { UserDelete(); } // User ��� else if
	 * (choice == 8) { UserRegister(); } } return choice; }
	 */

	private void UserManage() {

		Scanner scan = new Scanner(System.in);
		int select;

		System.out.println("->User Manage");
		System.out.println("+User++++++++++++++");
		//GetUser();
		System.out.println("+++++++++++++++++++");
		System.out
				.println("1.register user\t2.modify user\t3.delete user\t0.back");
		System.out.print("How to manage?(choose number)>");

		do {
			select = scan.nextInt();
			scan.nextLine();
			switch (select) {
			case 0:
				break;
			case 1:
				System.out.print("-->register user");
				UserRegister();
				break;
			case 2:
				System.out.print("-->modify user");
				UserModify();
				break;
			case 3:
				System.out.print("-->delete user");
				UserDelete();
				break;
			default:
				System.out.print("Choose number between 0 and 3 > ");
				select = -1;
				break;
			}
		} while (select < 0);
		scan.close();
	}

	public void UserRegister() {
		Scanner scan = new Scanner(System.in);
		String id;
		String pw;
		String name="";
		String prev="";
		int select;		

		System.out.print("Insert New ID : ");
		id = scan.nextLine();
		System.out.print("Insert New PW : ");
		pw = scan.nextLine();
		System.out.print("Insert New Name : ");
		pw = scan.nextLine();
		System.out.print("Select User Type(1.Normal 2.Administrator) : ");
		select = scan.nextInt();
		scan.nextLine();
		while (select < 1 || select > 2) {
			if (select == 1) 
				prev = "NORMAL";
			else if (select == 2)
				prev = "ADMIN";
			else {
				System.out.print("pw:1! name:2! try again! : ");
				select = scan.nextInt();
				scan.nextLine();
			}
		}
		scan.close();
		client.handleMessageFromClientUI("USER_REGISTER_" + id + "_" + pw + "_" + name + "_" + prev);
		WaitResponse();
	}

	// UserModify
	public void UserModify() {

		Scanner scan = new Scanner(System.in);

		// change selection name, id, pw
		String idx;
		int select;
		String change = "";
		String change_data = "";

		System.out.print("Select index : ");
		idx = scan.nextLine();
		System.out.print("How to modify(1.pw, 2.name) : ");
		select = scan.nextInt();

		while (select < 1 || select > 2) {
			if (select == 1) {
				change = "pw";
				System.out.print("Change pw : ");
				change_data = scan.nextLine();
			} else if (select == 2) {
				change = "name";
				System.out.print("Change name : ");
				change_data = scan.nextLine();
			} else {
				System.out.print("pw:1! name:2! try again! : ");
				select = scan.nextInt();
			}
		}
		scan.close();
		client.handleMessageFromClientUI("USER_MODIFY_" + idx + "_" + change
				+ "_" + change_data);
		WaitResponse();
	}

	public void UserDelete() {
		Scanner scan = new Scanner(System.in);
		String id = "";

		System.out.print("Select index : ");
		// id = scan.nextInt();
		id = scan.nextLine();

		// p@ id��� ������ index��� ������ �ٲپ�� ��

		scan.close();
		client.handleMessageFromClientUI("USER_DELETE_" + id);
		WaitResponse();
	}

	private void UserInfo() {
		/*
		 * p@ ���� ������� ���̵� ��й�ȣ �̸� ��� ��й�ȣ, �̸� ���� ���� ������ �ȴٸ� �߰� ���� ǥ��
		 */
		Scanner scan = new Scanner(System.in);
		int select;
		String change = "";
		String change_data = "";
		System.out.println("->User Info\nID\tPW\tName");
		System.out.println(id + "\t********\t" + name);
		// p@ ���� getUser(id);
		System.out
				.print("1.change pw 2.change name\nChange what?(choose number)>");
		select = scan.nextInt();

		while (select < 1 || select > 2) {
			if (select == 1) {
				change = "pw";
				System.out.print("Change pw : ");
				change_data = scan.nextLine();
			} else if (select == 2) {
				change = "name";
				System.out.print("Change name : ");
				change_data = scan.nextLine();
			} else {
				System.out.print("pw:1! name:2! try again! : ");
				select = scan.nextInt();
			}
		}
		scan.close();
		client.handleMessageFromClientUI("USER_INFO_" + id + "_" + change + "_"
				+ change_data);
		WaitResponse();
	}

	/* p@ �� ������ �޼��� �ڵ��� �������? */

	public void FoodRegister() {
		Scanner scan = new Scanner(System.in);

		// Ǫ�忡 ���� ���� insertedDate, isExpired, isProhibited �� 0���� �ʱ�ȭ
		String foodname = "";
		String quantity = "";
		String Weight = "";
		String calories = "";
		String freezeType = "";
		String floor = "";
		String expirationDate = "";
		String memo = "";
		// String insertedDate = "0";
		// String method = "";
		// String isExpired = "0";
		// String isProhibited = "0";

		System.out.print("Food Name : ");
		foodname = scan.nextLine();

		System.out.print("Quantity : ");
		quantity = scan.nextLine();
		quantity = boundary_test(quantity);

		System.out.print("Weight : ");
		Weight = scan.nextLine();
		Weight = boundary_test(Weight);

		System.out.print("Calries : ");
		calories = scan.nextLine();
		calories = boundary_test(calories);

		System.out.print("FreezeType(1.Refregirator 2.freezer) : ");
		freezeType = scan.nextLine();

		System.out.print("Location(floor) : ");
		floor = scan.nextLine();

		System.out.print("expirationDate(YYYY/MM/DD) : ");
		expirationDate = scan.nextLine();

		System.out.print("memo : ");
		memo = scan.nextLine();

		scan.close();

		/* p@ �̷� ��� ���� �ٸ� ������� ������ �Ķ���͸� ������ ���������� ���ڽ��ϴ�. */

		client.handleMessageFromClientUI("FOOD_REGISTER_" + foodname + "_"
				+ quantity + "_" + Weight + "_" + calories + "_" + freezeType
				+ "_" + floor + "_" + expirationDate + "_" + memo);
		WaitResponse();
	}

	private void FoodEdit() {
		Scanner input = new Scanner(System.in);
		int select;

		System.out.println("->Edit Food");
		System.out.println("-Food--------------");
		GetUser();
		System.out.println("-------------------");
		System.out
				.println("1.search food\t2.modify food\t3.delete food\t0.back");
		System.out.print("For what?(choose number)>");

		do {
			select = input.nextInt();

			switch (select) {
			case 0:
				break;
			case 1:
				System.out.print("-->search food");
				FoodSearch();
				break;
			case 2:
				System.out.print("-->modify food");
				FoodModify();
				break;
			case 3:
				System.out.print("-->delete food");
				FoodDelete();
				break;
			default:
				System.out.print("Choose number between 0 and 3 > ");
				select = -1;
				break;
			}
		} while (select < 0);
		input.close();

	}

	public void FoodModify() {

		Scanner scan = new Scanner(System.in);
		String foodname;
		int select = 0;
		String change = "";
		String change_data;

		System.out.print("Insert Foodname for Modify : ");
		foodname = scan.nextLine();
		System.out.println("How to modify?");
		System.out
				.println("1.Quantity\t2.Weight\t3.Calories\t4.Location(Freezer/Cooler)\t5.Location(Floor)\t6.Memo\t0.Back");
		select = scan.nextInt();
		System.out.print("Change data : ");
		change_data = scan.nextLine();

		switch (select) {
		case 0:
			// back
		case 1:
			change = "quantity";
			change_data = boundary_test(change_data);
			break;
		case 2:
			change = "weight";
			change_data = boundary_test(change_data);
			break;
		case 3:
			change = "calaries";
			change_data = boundary_test(change_data);
			break;
		case 4:
			change = "freezeType";
			break;
		case 5:
			change = "floor";
			break;
		case 6:
			change = "memo";
			break;
		}
		// �ٿ���� �۾�

		/* p@ ���ڷ� ���� ���ڿ��� ���� ���� �Ķ���� ���� ������ ���� ȿ�������� �ٵ������ ���ھ�� �ڵ� �м��� ��ƽ��ϴ�. */

		// ��ȣ�� ����� ���
		// client.handleMessageFromClientUI("FOOD_MODIFY_"+foodname+"_"+number+"_"+change_data);
		// ���ڿ��� ����� ���
		scan.close();
		client.handleMessageFromClientUI("FOOD_MODIFY_" + foodname + "_"
				+ change + "_" + change_data);
		WaitResponse();
	}

	/* p@ ���� �ٵ�����ϴ� */
	public String boundary_test(String st1) {
		Scanner scaner = new Scanner(System.in);
		int num = Integer.parseInt(st1);

		while (num < 0) {
			System.out.print("Input a correct num >=0 : ");
			num = scaner.nextInt();
		}
		scaner.close();

		return String.valueOf(num);
	}

	public void FoodDelete() {
		Scanner scan = new Scanner(System.in);
		String name;

		// System.out.println("[*]Food Delete Menu selected");
		System.out.print("Delete Food Name :");
		name = scan.nextLine();
		scan.close();
		client.handleMessageFromClientUI("FOOD_DELETE_" + name);
		WaitResponse();
	}

	public void FoodSearch() {
		Scanner scan = new Scanner(System.in);
		String name;

		System.out.print("Insert Food Name for Search:");
		name = scan.nextLine();
		scan.close();
		client.handleMessageFromClientUI("FOOD_SEARCH_" + name);

		WaitResponse();
	}

	private void Memo() {
		Scanner scan = new Scanner(System.in);
		String message;

		System.out.print("Write Memo:");
		message = scan.nextLine();
		scan.close();
		client.handleMessageFromClientUI("MSG_MEMO_" + message);

		WaitResponse();
	}

	public void WaitMessage() {
		// System.out.println("-----Message List-----");
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage()); // sleep �޼ҵ尡 �߻��ϴ�
													// InterruptedException
			}
			// System.out.print(".");

			if (GetStatus() == UserStatus.MSG_LOAD) {
				SetStatus(UserStatus.MENU);
				break;
			}
		}
	}

	public void WaitFood() {
		// System.out.println("-----Food List-----");
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage()); // sleep �޼ҵ尡 �߻��ϴ�
													// InterruptedException
			}
			// System.out.print(".");

			if (GetStatus() == UserStatus.FOOD_LOAD) {
				SetStatus(UserStatus.MENU);
				break;
			}
		}
	}

	public void WaitUser() {
		// System.out.println("-----User List-----");
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage()); // sleep �޼ҵ尡 �߻��ϴ�
													// InterruptedException
			}
			// System.out.print(".");

			if (GetStatus() == UserStatus.USER_LOAD) {
				SetStatus(UserStatus.MENU);
				break;
			}
		}
	}

	public void WaitResponse() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage()); // sleep �޼ҵ尡 �߻��ϴ�
													// InterruptedException
			}
			// System.out.print(".");
			if (GetStatus() == UserStatus.DONE) {
				SetStatus(UserStatus.MENU);
				break;
			}
		}
	}

	public void GetMessage() {
		client.handleMessageFromClientUI("MSG_SHOW");
		WaitMessage();
	}

	public void GetFood() {
		client.handleMessageFromClientUI("FOOD_SHOW");
		WaitFood();
	}

	/*
	 * p@ �Ϲݻ���������� ��� ���� �����ε� public void GetUser(String uid) {
	 * client.handleMessageFromClientUI("USER_INFO_" + uid); WaitUser(); }
	 */

	public void GetUser() {
		client.handleMessageFromClientUI("USER_SHOW");
		WaitUser();
	}
}
