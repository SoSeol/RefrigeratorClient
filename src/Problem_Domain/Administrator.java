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
	 *  ���� ���ǿ� ���� ���� ����Ʈ���� modify ����, delete ���� ������.
	 * @param action ���� ����
	 * @param name �����ϰ� ���� ���� �̸� ����
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
	 * id�� �ش��ϴ� ������ ������. ���н� false 
	 * @param id ������ ���� id
	 */
	private boolean DeleteUser(String id)
	{
		UserList ulist = RefrigeratorSystem.getUserList();
		int idx = ulist.checkIdx(id);
		// ������ ���� ���� ���� ���� ��.
		if(idx == -1)
		{
			return false;
		}
		ulist.delete(idx, this.getName());
		return true;
	}
	
	/**
	 * ������� �Է��� �޾� ���� ����.
	 * @param type PW / Name ����
	 * @param id �����ϰ��� �ϴ� ���� id
	 * @param editData ������ ������ ����
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
	 * ���� ����� �޼��� ���
	 * @param prev ����ϴ� ���� Ÿ��
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
	 * ������Ʈ ����( ����� ��� )
	 * ���� �̹� ID�����, Alarm.
	 * @param prev ����
	 * @param name �̸�
	 * @param id
	 * @param pw
	 */
	public boolean UpdateUser(UserPrevilege prev, String name, String id, String pw)
	{
		// ������ �����ϴ��� checking
		UserList list = RefrigeratorSystem.getUserList();
		if(list.checkID(id) == null)
		{
			registerUser(prev, name, id, pw);
			return true;
		}
		// ����Ϸ��� ������ �̹� �����Ѵٸ� false ��ȯ
		UserList.createWarningMessage(WarningMessageType.ExistUser, "System");
		return false;
	}
	
	public boolean UpdateUser(UserEditAction action, UserEditType type, String id, String editData)
	{
		return editUser(action, type, id, editData);
	}
	
	/**
	 * ������ �� �� �޼����� ������.
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
