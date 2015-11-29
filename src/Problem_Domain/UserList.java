package Problem_Domain;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class UserList
{
	private Vector<User> list;
	public UserList() { list = new Vector<User>(); }	

	/**
	 * 리스트를 문자열로 변환해줌
	 * @return 문자열화 된 리스트
	 */
	public String showList()
	{
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < list.size(); ++i)
			buf.append((i + 1) + " | " + list.elementAt(i).toString() + "\n");
		return buf.toString();
	}
	
	/**
	 * 리스트에 해당하는 아이디가 있으면 User 객체를 넘겨주고 없으면 null을 넘겨줌
	 * @param ID 찾고자 하는 ID
	 * @return 찾은 경우 해당 사용자, 찾지 못한 경우 null
	 */
	public User checkID(String ID)
	{
		for(int i = 0; i < list.size(); ++i)
		{
			if(list.elementAt(i).getID() == ID)
				return list.elementAt(i);
		}
		return null;
	}
	
	/**
	 * 리스트에서 해당하는 아이디를 찾아 유저 존재시 가진 유저의 인덱스 ,그렇지 않다면 -1 을 넘겨준다. 
	 * @param ID
	 * @return idx값
	 */
	public int checkIdx(String ID)
	{
		User tmp= checkID(ID);
		//만약 이 ID를 가진 유저가 없다면 -1 return
		if(tmp == null)
		{
			return -1;
		}
		return list.indexOf(tmp);
	}
	
	/**
	 * 중복 체크부터 함. 그 후 사용자를 리스트에 추가.  마지막으로 메세지 생성
	 * @param newUser 새로 추가할 사용자
	 * @param operatorName 추가 작업을 하고 있는 관리자명
	 */
	public void add(User newUser, String operatorName)
	{
		//해당 ID가 존재할 경우 예외를 던지는데 예외 새로 만들어 줘야 함
		if(checkID(newUser.getID()) != null) { 
			list.add(newUser);
			createUpdateMessage(UpdateMessageType.Addition, newUser.getName(), operatorName);
		}
		else
			System.out.println("ID exists!");
		
	}
	
	/**
	 * 사용자 리스트 위치를 이용해 사용자 삭제
	 * @param idx 제거할 사용자의 리스트 내 위치
	 * @param operatorName 삭제 작업을 하고 있는 관리자명
	 */
	public void delete(int idx, String operatorName)
	{
		User deleted = list.remove(idx);
		createUpdateMessage(UpdateMessageType.Removal, deleted.getName(), operatorName);
		deleted = null;
	}
	
	/**
	 * 수정 및 삭제를 위해 특정 위치에 있는 사용자 반환
	 * @param idx 위치
	 * @return 잘못된 위치 입력하면 null 반환, 정확한 위치 넣으면 해당 음식 반환
	 */
	public User elementAt(int idx)
	{
		if(idx < 0 || idx >= list.size()) return null;
		return list.elementAt(idx);
	}
	
	/**
	 * 해당 위치에 있는 사용자 객체를 수정한 객체로 변경할 때 사용
	 * @param usr
	 */
	public void replace(int idx, User usr)
	{
		list.set(idx, usr);
	}
	
	
	private void createUpdateMessage(UpdateMessageType t, String tgtUserName, String operatorName)
	{
		UpdateMessage newMessage = null;
		switch(t)
		{
		case Addition:
			newMessage = new UpdateMessage("New User " + tgtUserName + "added by " + operatorName, operatorName);
			break;
		case Modification:
			newMessage = new UpdateMessage("User " + tgtUserName + "modified by " + operatorName, operatorName);
			break;
		case Removal:
			newMessage = new UpdateMessage("User " + tgtUserName + "deleted by " + operatorName, operatorName);
			break;
		default: break;
		}
		RefrigeratorSystem.getMessageList().add(newMessage);
	}
	
	
	/**
	 * 업데이트 메세지 생성 후 메세지 목록에 추가
	 * @param t 업데이트 메세지 종류
	 * @param tgtUserName 업데이트 해당하는 사용자 이름
	 * @param operatorName 업데이트를 하는 관리자 이름
	 *
	static void createUpdateMessage(UpdateMessageType t, String tgtUserName, String operatorName)
	{
		UpdateMessage newMessage = null;
		switch(t)
		{
		case Addition:
			newMessage = new UpdateMessage("New User " + tgtUserName + "added by " + operatorName, operatorName);
		case Removal:
			newMessage = new UpdateMessage("User " + tgtUserName + "deleted by " + operatorName, operatorName);
			break;
		default: break;
		}
		RefrigeratorSystem.getMessageList().add(newMessage);
	}
	*/
	
	/**
	 * 워닝 메세지 생성 후 메세지 목록에 추가
	 * @param t 워닝 메세지 타입
	 * @param tgtUserName 메시지를 생성하게 한 장본인
	 */
	static void createWarningMessage(WarningMessageType t, String tgtUserName)
	{
		WarningMessage newMessage = null;
		switch(t)
		{
		case ExistUser:
			newMessage = new WarningMessage("User already Exist."+ "Try: "+tgtUserName, tgtUserName);
			break;
		default:
			break;
		}
		RefrigeratorSystem.getMessageList().add(newMessage);
	}
}