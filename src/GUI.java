import java.util.Scanner;

import client.ChatClient;

public class GUI {
	private ChatClient client;
	private String id;
	private String pw;
	private UserStatus status;

	public GUI(ChatClient c) {
		client = c;
		status = UserStatus.LOGIN;
	}

	public UserStatus GetStatus() {
		return status;
	}

	public void SetStatus(UserStatus stat) {
		status = stat;
	}

	public void Login() {
		Scanner scan = new Scanner(System.in);

		System.out.print("ID : ");
		id = scan.nextLine();
		System.out.print("PW : ");
		pw = scan.nextLine();

		client.handleMessageFromClientUI("LOGIN_" + id + "_" + pw);
		
		while (true) {
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e){
				System.out.println(e.getMessage()); //sleep 메소드가 발생하는 InterruptedException 
			}
			System.out.print(".");
			if (GetStatus() == UserStatus.MENU)
				break;
		}
	}

	public void FoodModify()
	{
		Scanner scan = new Scanner(System.in);
		String foodname = "";
		int number = 0;
		String change = "";
		String change_data ="";
		int num = 0;
		
		System.out.println("Food name for Modify : ");
		foodname = scan.nextLine();
		System.out.println("What do you want to change");
		System.out.println("1.Name       2.Quantity  3.Weight          4.Calories");
		System.out.println("5.FreezeType 6.Floor     7.ExpirationDate  8.Inserted Date");
		System.out.println("9.Memo       10.Method   11.isExpired      12.isProhibited");
		number = scan.nextInt();
		System.out.println("Input new data");
		change_data = scan.nextLine();
		
		switch(number)
		{
		case 1: change = "name";
			break;
		case 2:	change = "quantity";
			change_data = boundary_test(change_data);
			break;
		case 3: change = "weight";
		    change_data = boundary_test(change_data);
			break;
		case 4: change = "calaries";
			change_data = boundary_test(change_data);
			break;
		case 5: change = "freezeType";
			break;
		case 6: change = "floor";
			break;
		case 7: change = "expirationDate";
			break;
		case 8: change = "inserted Date";
			break;
		case 9: change = "memo";
			break;
		case 10: change = "method";
			break; 
		case 11: change = "isExpired";
			break;
		case 12: change = "isProhibited";
			break;
		}
		//바운더리 작업
		
		//번호로 사용할 경우
		//client.handleMessageFromClientUI("FOOD_MODIFY_"+foodname+"_"+number+"_"+change_data);
		//문자열로 사용할 경우
		client.handleMessageFromClientUI("FOOD_MODIFY_"+foodname+"_"+change+"_"+change_data);

	}
	
	public void FoodRegister()
	{
		Scanner scan = new Scanner(System.in);
		
		//푸드에 대한 정보 insertedDate, isExpired, isProhibited 는 0으로 초기화
		String foodname = "";
		String quantity = "";
		String Weight = "";
		String calories ="";
		String freezeType = "";
		String floor = "";
		String expirationDate = "";
		String insertedDate = "0";
		String memo = "";
		String method = "";
		String isExpired = "0";
		String isProhibited = "0";
		

		System.out.println("Food name : ");
		foodname = scan.nextLine();
			
		System.out.println("quantity : ");
		quantity = scan.nextLine();
		quantity = boundary_test(quantity);
		
		System.out.println("Weight : ");
		Weight = scan.nextLine();
		Weight = boundary_test(Weight);
		
		System.out.println("calries : ");
		calories = scan.nextLine();
		calories = boundary_test(calories);
		
		System.out.println("freezeType : 1.Refregirator 2.freezer ");
		freezeType = scan.nextLine();
		
		System.out.println("floor : ");
		floor = scan.nextLine();
		
		System.out.println("expirationDate 00/00/00(year/month/day) : ");
		expirationDate = scan.nextLine();
		
		System.out.println("memo : ");
		memo= scan.nextLine();
		
		System.out.println("method : ");
		method = scan.nextLine();
		
		client.handleMessageFromClientUI("FOOD_REGISTER_"+foodname+"_"+quantity+"_"+Weight+"_"+calories+"_"+freezeType
				+"_"+floor+"_"+expirationDate+"_"+insertedDate+"_"+memo+"_"+method+"_"+isExpired+"_"+isProhibited);

	}
	
	public String boundary_test(String st1)
	{
		Scanner scaner = new Scanner(System.in);
		int num = Integer.parseInt(st1);
		
		while(num < 0 )
		{
			
			num = Integer.parseInt(st1);
			if(num>0)
			{
				break;
			}
	    	System.out.println("Input a correct num >=0");
	    	st1 = scaner.nextLine();
	    	
		}
		return(st1);
	}

	public void FoodDelete() {
		Scanner scan = new Scanner(System.in);
		String name;

		System.out.println("[*]Food Delete Menu selected");
		System.out.print("Delete Food Name:");
		name = scan.nextLine();
		client.handleMessageFromClientUI("FOOD_DELETE_" + name);
	}

	public void FoodSearch() {
		Scanner scan = new Scanner(System.in);
		String name;

		System.out.println("[*]Food Search Menu selected");
		System.out.print("Search Food Name:");
		name = scan.nextLine();
		client.handleMessageFromClientUI("FOOD_SEARCH_" + name);
	}

	// UserModify
	public void UserModify() {

		Scanner scan = new Scanner(System.in);

		// change selection name, id, pw
		String id = "";
		String change = "";
		String change_data = "";

		System.out.println("Input ID which you want to change : ");
		id = scan.nextLine();
		System.out.println("What you want to change name/id/pw : ");
		change = scan.nextLine();

		if (change.equals("name")) {
			System.out.println("Input change name");
			change_data = scan.nextLine();
		} else if (change.equals("id")) {
			System.out.println("Input change Id");
			change_data = scan.nextLine();
		} else if (change.equals("pw")) {
			System.out.println("Input change pw");
			change_data = scan.nextLine();
		} else {
			System.out.println("name or id or pw");
		}

		client.handleMessageFromClientUI("USER_MODIFY" + id + "_" + change
				+ "_" + change_data);
	}

	public void UserDelete() {
		Scanner scan = new Scanner(System.in);
		String id = "";

		System.out.println("Delete ID : ");
		id = scan.nextLine();

		client.handleMessageFromClientUI("USER_DELETE_" + id);
	}

	public void UserRegister() {
		Scanner scan = new Scanner(System.in);
		String id = "";
		String pw = "";

		System.out.println("Input New ID : ");
		id = scan.nextLine();
		System.out.println("Input New PW : ");
		pw = scan.nextLine();

		client.handleMessageFromClientUI("USER_REGISTER" + id + "_" + pw);
	}
	
	/**
	 * Menu
	 * @return 선택 메뉴 번호
	 */
	public int Menu() {
		Scanner scan = new Scanner(System.in);
		int choice;

		System.out.println("=========Menu=========");
		GetFood();
		System.out.println("1.LogOut");
		System.out.println("2.Food 변경");
		System.out.println("3.Food 삭제");
		System.out.println("4.Food 등록");
		System.out.println("5.Food 검색");

		if (id.equals("admin")) {
			System.out.println("6.User 변경");
			System.out.println("7.User 삭제");
			System.out.println("8.User 등록");
		}

		System.out.print("choice number> ");
		choice = scan.nextInt();

		// 로그아웃
		if(choice == 1)
		{
			return choice;
		}
		// 음식 변경
		else if (choice == 2) {
			FoodModify();
		}
		// 음식 삭제
		else if (choice == 3) {
			FoodDelete();
		}
		// 음식 등록
		else if (choice == 4) {
			FoodRegister();
		}
		// 음식 검색
		else if (choice == 5) {
			FoodSearch();
		}
		if (id.equals("admin")) {
			// User 변경
			if (choice == 6) {
				UserModify();
			}
			// User 삭제
			else if (choice == 7) {
				UserDelete();
			}
			// User 등록
			else if (choice == 8) {
				UserRegister();
			}
		}
		return choice;
	}

	public void GetMessage() {
		client.handleMessageFromClientUI("MSG_SHOW");
	}

	public void GetFood() {
		client.handleMessageFromClientUI("FOOD_SHOW");
	}
}
