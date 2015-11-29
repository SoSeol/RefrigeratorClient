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
	 * ����Ʈ�� ���ڿ��� ��ȯ����
	 * @return ���ڿ�ȭ �� ����Ʈ
	 */
	public String showList()
	{
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < list.size(); ++i)
			buf.append((i + 1) + " | " + list.elementAt(i).toString() + "\n");
		return buf.toString();
	}
	
	/**
	 * ����Ʈ�� �ش��ϴ� ���̵� ������ User ��ü�� �Ѱ��ְ� ������ null�� �Ѱ���
	 * @param ID ã���� �ϴ� ID
	 * @return ã�� ��� �ش� �����, ã�� ���� ��� null
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
	 * ����Ʈ���� �ش��ϴ� ���̵� ã�� ���� ����� ���� ������ �ε��� ,�׷��� �ʴٸ� -1 �� �Ѱ��ش�. 
	 * @param ID
	 * @return idx��
	 */
	public int checkIdx(String ID)
	{
		User tmp= checkID(ID);
		//���� �� ID�� ���� ������ ���ٸ� -1 return
		if(tmp == null)
		{
			return -1;
		}
		return list.indexOf(tmp);
	}
	
	/**
	 * �ߺ� üũ���� ��. �� �� ����ڸ� ����Ʈ�� �߰�.  ���������� �޼��� ����
	 * @param newUser ���� �߰��� �����
	 * @param operatorName �߰� �۾��� �ϰ� �ִ� �����ڸ�
	 */
	public void add(User newUser, String operatorName)
	{
		//�ش� ID�� ������ ��� ���ܸ� �����µ� ���� ���� ����� ��� ��
		if(checkID(newUser.getID()) != null) { 
			list.add(newUser);
			createUpdateMessage(UpdateMessageType.Addition, newUser.getName(), operatorName);
		}
		else
			System.out.println("ID exists!");
		
	}
	
	/**
	 * ����� ����Ʈ ��ġ�� �̿��� ����� ����
	 * @param idx ������ ������� ����Ʈ �� ��ġ
	 * @param operatorName ���� �۾��� �ϰ� �ִ� �����ڸ�
	 */
	public void delete(int idx, String operatorName)
	{
		User deleted = list.remove(idx);
		createUpdateMessage(UpdateMessageType.Removal, deleted.getName(), operatorName);
		deleted = null;
	}
	
	/**
	 * ���� �� ������ ���� Ư�� ��ġ�� �ִ� ����� ��ȯ
	 * @param idx ��ġ
	 * @return �߸��� ��ġ �Է��ϸ� null ��ȯ, ��Ȯ�� ��ġ ������ �ش� ���� ��ȯ
	 */
	public User elementAt(int idx)
	{
		if(idx < 0 || idx >= list.size()) return null;
		return list.elementAt(idx);
	}
	
	/**
	 * �ش� ��ġ�� �ִ� ����� ��ü�� ������ ��ü�� ������ �� ���
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
	 * ������Ʈ �޼��� ���� �� �޼��� ��Ͽ� �߰�
	 * @param t ������Ʈ �޼��� ����
	 * @param tgtUserName ������Ʈ �ش��ϴ� ����� �̸�
	 * @param operatorName ������Ʈ�� �ϴ� ������ �̸�
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
	 * ���� �޼��� ���� �� �޼��� ��Ͽ� �߰�
	 * @param t ���� �޼��� Ÿ��
	 * @param tgtUserName �޽����� �����ϰ� �� �庻��
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