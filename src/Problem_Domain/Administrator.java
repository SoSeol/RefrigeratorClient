package Problem_Domain;

public class Administrator extends User {
	
	private enum UserEditAction
	{
		Modify, Delete;
	}
	
	private enum UserEditType
	{
		ID, PW, Name;
	}
	
	public Administrator(String newName, String newID, String newPW)
	{
		super(newName, newID, newPW);
	}
/*		
	public String searchUser()
	{		
		return RefrigeratorSystem.getUserList().showList();
	}
*/	
	public String searchUser(UserList list)
	{		
		return list.showList();
	}
	
	
	/**
	 *  유저 엑션에 따라 유저 리스트에서 modify 할지, delete 할지 결정함.
	 * @param action 선택 종류
	 * @param name 수정하고 싶은 유저 이름 설정
	 */
	private boolean editUser(UserEditAction action, UserEditType type, String id, String editData)
	{
		switch(action)
		{
		case Modify:
			return ModifyUser(type, id, editData);
	    case Delete:
	    	return DeleteUser(id);
	    default: return false;
	    }
	}
	
	/**
	 * id에 해당하는 유저를 삭제함. 실패시 false 
	 * @param id 삭제할 유저 id
	 */
	private boolean DeleteUser(String id)
	{
		UserList ulist = RefrigeratorSystem.getUserList();
		int idx = ulist.checkIdx(id);
		// 삭제할 유저 존재 하지 않을 시.
		if(idx == -1)
		{
			return false;
		}
		ulist.delete(idx, this.getName());
		return true;
	}
	
	/**
	 * 사용자의 입력을 받아 유저 수정.
	 * @param type PW / Name 변경
	 * @param id 수정하고자 하는 유저 id
	 * @param editData 수정할 데이터 내용
	 */
	private boolean ModifyUser(UserEditType type, String id, String editData)
	{
		User target = RefrigeratorSystem.getUserList().checkID(id);
		int idx = RefrigeratorSystem.getUserList().checkIdx(id);
		switch(type)
		{
		case ID:
			if(RefrigeratorSystem.getUserList().checkID(editData) != null)
				return false;
			target.setID(editData);
		case PW:
			target.changePassword(editData);
			break;
		case Name:
			target.setName(editData);
			break;
		}
		RefrigeratorSystem.getUserList().replace(idx, target);
		return true;
	}
	
	
	public User registerUser(int authority, String name, String ID, String PW) {
		
		User newUser = null;
		
		switch(authority) {
		case 0:
			newUser = new NormalUser(name, ID, PW);
			break;
		case 1:
			newUser = new Administrator(name, ID, PW);
			break;
		}
		return newUser;
	}
	
	/**
	 * 유저 등록휴 메세지 출력
	 * @param prev 등록하는 유저 타입
	 * @param name
	 * @param id
	 * @param pw
	 */
	private void registerUser(UserPrevilege prev, String name, String id, String pw)
	{
		User newUser = null;
		switch(prev)
		{
		case Administrator:
			newUser = new Administrator(name, id, pw);
			break;
		case Normal:
			newUser = new NormalUser(name, id, pw);
			break;
		}
		
		RefrigeratorSystem.getUserList().add(newUser, this.getName());
	}
	
	/**
	 * 업데이트 유저( 사용자 등록 )
	 * 만약 이미 ID존재시, Alarm.
	 * @param prev 권한
	 * @param name 이름
	 * @param id
	 * @param pw
	 */
	public boolean UpdateUser(UserPrevilege prev, String name, String id, String pw)
	{
		// 유저가 존재하는지 checking
		UserList list = RefrigeratorSystem.getUserList();
		if(list.checkID(id) == null)
		{
			registerUser(prev, name, id, pw);
			return true;
		}
		// 등록하려는 유저가 이미 존재한다면 false 반환
		UserList.createWarningMessage(WarningMessageType.ExistUser, "System");
		return false;
	}
	
	public boolean UpdateUser(UserEditAction action, UserEditType type, String id, String editData)
	{
		return editUser(action, type, id, editData);
	}
	
	/**
	 * 기한이 다 된 메세지를 삭제함.
	 */
	public void deleteOldMessages()
	{
		MessageList mlist = RefrigeratorSystem.getMessageList();
		mlist.checkOutofDate();
	}
	
	public String toString()
	{
		return "Administrator, ID : " + getID() + ", Name : " + getName();
	}

}
