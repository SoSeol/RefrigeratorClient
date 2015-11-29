package Problem_Domain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

public class RefrigeratorSystem {
	private static UserList ulist=null;
	private static FoodList flist=null;
	private static MessageList mlist=null;

	public static void RefrigeratorSystem(UserList ulist,FoodList flist,MessageList mlist) {
		//리스트 read
		ulist = ulist;
		flist = flist;
		mlist = mlist;
	}
	
	/**
	 * 로그인 프로세스
	 * ID, PW 비교 통해 검색된 user 객체 return
	 */
	private static User loginMode() {
		Scanner input = new Scanner(System.in);
		User user;

		while (true) {
			System.out.println("ID : ");
			user = ulist.checkID(input.nextLine());

			if (user != null) {
				//while (true) { // while 활성화하면 pw에서 반복, 비활성화시 id,pw 반복
					System.out.println("PW : ");
					if (user.checkPassword(input.nextLine())) {
						input.close();
						System.out.println(user.toString() + " login!");
						input.close();
						return user;
					} else
						System.out.println("Wrong PW!");
				//}
			} else
				System.out.println("User not found!");
		}
	}

	/*
	 * 뉴스피드 프로세스
	 * 사용자에 맞는 메세지 출력 or 그냥 메세지 출력
	 */
	private static void messageMode() {
		mlist.showList();
		// 사용자에 맞는 메세지 출력 시 사용자 변수에 따른 메세지 검색 후 리스트 출력
	}
	
	
	/* 
	 * 메뉴 프로세스
	 * loginuser 객체에 따른 동적바인딩하고 상속 형태에 따라(관리자,일반) 메뉴구분 
	 */
	private static void menuMode(User userInUse) {

		Scanner input = new Scanner(System.in);
		int exitFlag = 0;

		System.out.println("What do you want?");
		
		if (userInUse instanceof Administrator) { // 관리자
			Administrator admin = (Administrator)userInUse;
			while(exitFlag==1)
			{
				System.out.println("manage user(1), register food(2), show food(3), setting(4), exit(0)\n>");

				switch (input.nextInt()) {
				case 1:						
					adminMode(admin); //사용자 관리
					break;
				case 2:	
					newFoodMode(admin); //음식 등록
					break;
				case 3:
					showFoodMode(admin); //음식 보기, 이 메뉴를 통해 편집, 삭제
					break;
				case 4:
					//setMode(); // 설정, 필요시 구현, 알림 유무나 알림 주기 등을 설정
					break;
				case 0:
					exitFlag = 1; // while문 탈출
					break;
				default:
					System.out.println("Wrong Command Number!"); //예외 명령 처리
					break;
				}				
			}

		} else if (userInUse instanceof NormalUser) { // 사용자
			NormalUser normal = (NormalUser) userInUse;
			while(exitFlag==1)
			{
				System.out.println("register food(1), search food(2), user info(3), setting(4), exit(0)\n>");
				
				switch (input.nextInt()) {
				case 1:						
					newFoodMode(normal);
					break;
				case 2:	
					showFoodMode(normal);
					break;
				case 3:
					userMode(normal); // 사용자 정보, 이름이나 pw 수정
					break;
				case 4:
					//setMode();
					break;
				case 0:
					exitFlag = 1;
					break;
				default:
					System.out.println("Wrong Command Number!");
					break;								
				}				
			}
		}
	}
	
	public static void adminMode(Administrator admin) {
		Scanner input = new Scanner(System.in);
		int type;
		int index;
		
		admin.searchUser(ulist);
		
		System.out.println("\nManage User");
		System.out.print("Select (register user : 1, modify user : 2, delete user : 3, back : 0) : ");
		switch(input.nextInt()) {
			case 1:
				System.out.println("\nRegister User");
				System.out.print("authority(Normal User : 0, Administrator : 1) : ");
				type = input.nextInt();
				System.out.print("name : ");
				String userName = input.nextLine();
				System.out.print("ID : ");
				String userID = input.nextLine();
				System.out.print("PW : ");
				String userPW = input.nextLine();
				ulist.add(admin.registerUser(type, userName, userID, userPW), admin.getName());
				System.out.println("User Register Complete!");
				break;
			case 2:
				System.out.println("Modify User");
				System.out.print("Index : ");
				index = input.nextInt();
				System.out.print("(Name : 2, ID : 0, PW : 1)");
				type = input.nextInt();
				String modifyString = input.nextLine();
				//ulist.modify(admin.ModifyUser(index,type,modifyString),admin.getName());
				System.out.println("User Modify Complete!");
				break;
			case 3:
				System.out.println("Delete User");
				System.out.print("Index : ");
				index = input.nextInt();
				//admin.DeleteUser(index);
				System.out.println("User Delete Complete!");
				break;
			case 0:
				break;
			default:
				System.out.println("Wrong Command Number!");
				break;
		}
		
		input.close();
	}

	public static void userMode(NormalUser user) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("User Info");
		System.out.println(user.toString() + "\t음식갯수 : ");
		//유저의 음식리스트
		System.out.print("(change Name : 1, chage PW : 2, back : 0) : ");
		switch(input.nextInt()) {
			case 1:
				System.out.println("Change Name");
				System.out.print("name : ");
				String userName = input.nextLine();
				user.setName(userName);
				System.out.println("Chage Name Complete!");
				break;
			case 2:
				System.out.println("Change PW");
				System.out.print("name : ");
				String userPW = input.nextLine();
				user.setPW(userPW);//재확인
				System.out.println("Change PW Complete!");
				break;
			case 0:
				break;
			default:
				System.out.println("Wrong Command Number!");
				break;
		}	
		
		input.close();
	}
	
	public static void newFoodMode(User user) {
		Scanner input = new Scanner(System.in);
		String name;
		int quantity;
		int weight;
		int calories;
		boolean freeze;
		int row;
		int col;
		Calendar expiration = Calendar.getInstance();	
		
		System.out.println("Register Food");
		System.out.print("Food Name : ");
		name = input.nextLine();
		System.out.print("Quantity : ");
		quantity = input.nextInt();
		System.out.print("Weight : ");
		weight = input.nextInt();
		System.out.print("Calories : ");
		calories = input.nextInt();
		System.out.print("Expiration Date(yyyy/mm/dd) : ");
		String[] date = input.nextLine().split("/");
		expiration.set(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));	
		
		//user.registerItem(name,quantity,weight,calories,expiration);
		
		input.close();	
	}
	
	public static void showFoodMode(User user) {
		
		Scanner input = new Scanner(System.in);
		String foodName;
		
		System.out.println("Food");
		user.searchItem();			
		
		System.out.print("(Search : 1, Modify : 2, Delete : 3, Back : 0) : ");
		switch(input.nextInt()) {
		case 1:
			System.out.println("Search food");
			System.out.print("food name : ");
			foodName = input.nextLine();
			//user.searchItem(foodName);
			break;
		case 2:			
			modifyFoodMode(user);
			break;
		case 3:
			System.out.println("Delete food");
			System.out.print("food name : ");
			foodName = input.nextLine();
			//user.searchItem(foodName);
			break;
		case 0:
			break;
		default:
			System.out.println("Wrong Command Number!");
			break;
	}	
	
	input.close();
				
		
	}
	
	public static void modifyFoodMode(User user) {
		Scanner input = new Scanner(System.in);
		//
		
		System.out.println("Modify Food");
		System.out.print("(modify name : 1, modify quantity : 2, modify : 3, back : 0) : ");
		switch(input.nextInt()) {
			case 1:				
				System.out.print("name : ");
				String userName = input.nextLine();				
				//user.modifyItem()
				System.out.println("name Modify Complete!");
				break;
			case 2:
				System.out.print("quantity : ");
				int quantity = input.nextInt();				
				//user.modifyItem()
				System.out.println("name Modify Complete!");
				break;
			case 3:
				System.out.print("name : ");
				String str = input.nextLine();				
				//user.modifyItem()
				System.out.println("name Modify Complete!");
				break;
			case 0:
				break;
			default:
				System.out.println("Wrong Command Number!");
				break;
		}
		
		input.close();
	}
	
	public static UserList getUserList() { return ulist; }
	public static FoodList getFoodList() { return flist; }
	public static MessageList getMessageList() { return mlist; }	
	
}
