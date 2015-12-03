package Refrigerator_Client;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import OCSF.Common.ChatClient;

public class UI {
	private ChatClient client;
	private String id;
	private String pw;
	private String name;
	private boolean authority;
	private UserStatus status;
	private BufferedReader scan;

	public UI(ChatClient c) {
		scan = new BufferedReader(new InputStreamReader(System.in));
		// client = c;
		status = UserStatus.LOGIN;
	}

	String GetConsole() {
		String tmp = "";
		try {
			tmp = scan.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tmp;
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

			System.out.print("ID : ");
			id = GetConsole();
			System.out.print("PW : ");
			pw = GetConsole();

			// cmd 콘솔에서 혹여나 안되거나 기타 이유로 로그인에서 되지 않을 경우에 아래 주석 내용 사용해서 로그인하시면
			// 됩니다.
			/*
			 * Console secret = System.console(); if (secret == null)
			 * System.err.println("Console fail"); id = secret.readLine("%s",
			 * "ID : "); pw = new String(secret.readPassword("%s", "PW : "));
			 */

			client.handleMessageFromClientUI("LOGIN_" + id + "_" + pw);
			WaitLogin();
		}
	}

	public void WaitLogin() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			if (GetStatus().equals(UserStatus.LOGIN_FAIL)) {
				SetStatus(UserStatus.LOGIN);
				break;
			} else if (GetStatus().equals(UserStatus.MENU)) {
				break;
			}
		}
	}

	public int Menu() {
		int choice = 0;

		System.out.println("============Menu===========");
		if (isAuthority()) {
			System.out.println("1.User Manage");
		} else {
			System.out.println("1.User Info");
		}
		System.out.println("2.Show message");
		System.out.println("3.Register Food");
		System.out.println("4.Edit Food");
		System.out.println("5.Memo");
		if (isAuthority()) {
			System.out.println("6.Old Memo Delete");
		}
		System.out.println("0.Exit");
		System.out.print("What do you want?(choose number)> ");
		do {
			choice = Integer.parseInt(GetConsole());
			switch (choice) {
			case 0:
				choice = 0;
				break;
			case 1:
				if (isAuthority())
					UserManage();
				else
					UserInfo();
				break;
			case 2:
				GetMessage();
				break;
			case 3:
				FoodRegister();
				break;
			case 4:
				FoodEdit();
				break;
			case 5:
				Memo();
				break;
			case 6:
				if (isAuthority())
					MemoDelete();
				break;
			default:
				System.out.print("Choose number> ");
				break;
			}
		} while (choice < 0 || choice > 6);
		return choice;
	}

	private void MemoDelete() {
		client.handleMessageFromClientUI("MSG_DELETEOLD");
		WaitResponse();
	}

	private void UserManage() {

		int select;

		System.out.println("->User Manage");
		System.out.println("----------User----------");
		GetUser();
		System.out.println("------------------------");
		System.out
				.println("1.register user\t2.modify user\t3.delete user\t0.back");
		System.out.print("How to manage?(choose number)>");
		do {
			select = Integer.parseInt(GetConsole());
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
	}

	public void UserRegister() {
		String id;
		String pw;
		String name;
		int select;

		System.out.print("Insert New ID : ");
		id = GetConsole();
		System.out.print("Insert New PW : ");
		pw = GetConsole();
		System.out.print("Insert New Name : ");
		name = GetConsole();
		System.out.print("Select User Type(1.Normal 2.Administrator) : ");
		select = Integer.parseInt(GetConsole());
		while (select < 1 || select > 2) {
			System.out.print("pw:1! name:2! try again! : ");
			select = Integer.parseInt(GetConsole());
		}
		String resultStr = "USER_REGISTER_" + id + "_" + pw + "_" + name + "_"
				+ (select == 1 ? "NORMAL" : "ADMIN");
		client.handleMessageFromClientUI(resultStr);
		WaitResponse();
	}

	public void UserModify() {

		String idx;
		int select;
		String change = "";
		String change_data = "";

		System.out.print("Select index : ");
		idx = GetConsole();
		System.out.print("How to modify(1.pw, 2.name 0.back) : ");
		select = Integer.parseInt(GetConsole());

		for (;;) {
			if (select == 1) {
				change = "pw";
				System.out.print("Change pw : ");
				change_data = GetConsole();
				break;
			} else if (select == 2) {
				change = "name";
				System.out.print("Change name : ");
				change_data = GetConsole();
				break;
			} else if (select == 0)
				return;
			else {
				System.out.print("pw:1! name:2! try again! : ");
				select = Integer.parseInt(GetConsole());
			}
		}
		client.handleMessageFromClientUI("USER_MODIFY_" + idx + "_" + change
				+ "_" + change_data);
		WaitResponse();
	}

	public void UserDelete() {
		String id = "";

		System.out.print("Select index : ");
		id = GetConsole();

		client.handleMessageFromClientUI("USER_DELETE_" + id);
		WaitResponse();
	}

	private void UserInfo() {

		int select;
		String change = "";
		String change_data = "";
		System.out.println("->User Info\nID\tPW\t\tName");
		System.out.println(id + "\t********\t" + name);

		for (;;) {

			try {
				System.out
						.print("1.change pw 2.change name 0.back\nChange what?(choose number)>");
				try {
					select = Integer.parseInt(GetConsole());
				} catch (NumberFormatException nfe) {
					throw new Exception("Wrong input.");
				}
				if (select == 1) {
					change = "pw";
					System.out.print("Change pw : ");
					change_data = GetConsole();
					if (change_data.length() == 0)
						throw new Exception("You enetered nothing.");
					break;
				}

				if (select == 2) {
					change = "name";
					System.out.print("Change name : ");
					change_data = GetConsole();
					if (change_data.length() == 0)
						throw new Exception("You enetered nothing.");
					break;
				}

				if (select == 0)
					return;
			} catch (Exception e) {
				System.err.println(e.getMessage() + " Try again.");
				select = Integer.parseInt(GetConsole());
			}
		}

		client.handleMessageFromClientUI("USER_INFO_" + id + "_" + change + "_"
				+ change_data);
		WaitResponse();
	}

	public void FoodRegister() {

		String foodname = "";
		String quantity = "";
		String Weight = "";
		String calories = "";
		String freezeType = "";
		String floor = "";
		String expirationDate = "";
		String memo = "";

		System.out.print("Food Name : ");
		foodname = GetConsole();

		System.out.print("Quantity : ");
		quantity = GetConsole();
		quantity = boundary_test(quantity);

		System.out.print("Weight : ");
		Weight = GetConsole();
		Weight = boundary_test(Weight);

		System.out.print("Calries : ");
		calories = GetConsole();
		calories = boundary_test(calories);

		System.out.print("FreezeType(1.Cooler 2.Freezer) : ");
		freezeType = GetConsole();

		System.out.print("Location(floor) : ");
		floor = GetConsole();

		for (;;) {
			System.out.print("expirationDate(YYYY/MM/DD) : ");
			expirationDate = GetConsole();
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				formatter.parse(expirationDate);
				break;
			} catch (ParseException pe) {
				System.err.print("Wrong date input. ");
			}
		}
		System.out.print("memo : ");
		memo = GetConsole();

		client.handleMessageFromClientUI("FOOD_REGISTER_" + foodname + "_"
				+ quantity + "_" + Weight + "_" + calories + "_" + freezeType
				+ "_" + floor + "_" + expirationDate + "_" + memo);
		WaitResponse();
	}

	private void FoodEdit() {
		int select;

		System.out.println("->Edit Food");
		GetFood();
		if (GetStatus() == UserStatus.FOOD_EMPTY) {
			this.SetStatus(UserStatus.MENU);
			return;
		}
		System.out.println("-------------------");
		System.out
				.println("1.search food\t2.modify food\t3.delete food\t0.back");
		System.out.print("For what?(choose number)>");

		do {
			select = Integer.parseInt(this.GetConsole());

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
	}

	public void FoodModify() {

		String foodname;
		int select = 0;
		String change = "";
		String change_data = "";

		if (GetStatus() == UserStatus.FOOD_EMPTY) {
			this.SetStatus(UserStatus.MENU);
			return;
		}

		System.out.print("\nInsert idx for Modify : ");
		foodname = GetConsole();
		System.out.println("How to modify?");
		System.out
				.println("1.Quantity\t2.Weight\t3.Calories\t4.Location(Freezer/Cooler)\t5.Location(Floor)\t6.Memo\t0.Back");
		select = Integer.parseInt(GetConsole());
		if (select != 0) {
			System.out.print("Change data : ");
			change_data = GetConsole();
		}
		switch (select) {
		case 0:
			return;
		case 1:
			change = "quantity";
			change_data = boundary_test(change_data);
			break;
		case 2:
			change = "weight";
			change_data = boundary_test(change_data);
			break;
		case 3:
			change = "calories";
			change_data = boundary_test(change_data);
			break;
		case 4:
			change = "freezeType";
			change_data = boundary_test(change_data);
			break;
		case 5:
			change = "floor";
			break;
		case 6:
			change = "memo";
			break;
		}

		client.handleMessageFromClientUI("FOOD_MODIFY_" + foodname + "_"
				+ change + "_" + change_data);
		WaitResponse();
	}

	// numbercheck
	public String boundary_test(String st1) {

		int num = Integer.parseInt(st1);

		while (num < 0) {
			System.out.print("Input a correct num >=0 : ");
			num = Integer.parseInt(GetConsole());
		}

		return String.valueOf(num);
	}

	public void FoodDelete() {

		String name;

		System.out.print("Delete Food Number :");
		name = GetConsole();

		client.handleMessageFromClientUI("FOOD_DELETE_" + name);
		WaitResponse();
	}

	public void FoodSearch() {

		String name;

		System.out.print("Insert Food Name for Search:");
		name = GetConsole();

		client.handleMessageFromClientUI("FOOD_SEARCH_" + name);

		WaitResponse();
	}

	private void Memo() {

		String message;

		System.out.print("Write Memo : ");
		message = GetConsole();

		if (message.length() > 0) {
			client.handleMessageFromClientUI("MSG_MEMO_" + message);
			WaitResponse();
		} else
			return;
	}

	public void WaitMessage() {

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

			if (GetStatus() == UserStatus.MSG_LOAD) {
				SetStatus(UserStatus.MENU);
				break;
			}
		}
	}

	public void WaitFood() {

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

			if (GetStatus() == UserStatus.FOOD_LOAD) {
				SetStatus(UserStatus.MENU);
				break;
			} else if (GetStatus() == UserStatus.FOOD_EMPTY) {
				break;
			}
		}
	}

	public void WaitUser() {

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

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
				System.out.println(e.getMessage());
			}

			if (GetStatus() == UserStatus.DONE) {
				SetStatus(UserStatus.MENU);
				break;
			}
		}
	}

	// messagelist receive
	public void GetMessage() {
		client.handleMessageFromClientUI("MSG_SHOW");
		WaitMessage();
	}

	// foodlist receive
	public void GetFood() {
		client.handleMessageFromClientUI("FOOD_SHOW");
		WaitFood();
	}

	// userlist receive
	public void GetUser() {
		client.handleMessageFromClientUI("USER_SHOW");
		WaitUser();
	}
}
