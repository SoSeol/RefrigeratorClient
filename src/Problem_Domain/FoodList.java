package Problem_Domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class FoodList
{
	private static ArrayList<String> prohibitedList;
	private Vector<Food> list;
	
	public FoodList()
	{
		prohibitedList = new ArrayList<String>();
		prohibitedList.add("����");
		prohibitedList.add("����ȭ��");
		prohibitedList.add("����");
		prohibitedList.add("�ʷ���");
		prohibitedList.add("�븶��");
		prohibitedList.add("û�갡��");
		list = new Vector<Food>();
	}
	
	public static ArrayList<String> getProhibitedList() { return prohibitedList; }
	
	/**
	 * ����Ʈ�� ���ڿ��� ��ȯ����
	 * @return ���ڿ�ȭ �� ����Ʈ
	 */
	public String showList()
	{
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < list.size(); ++i)
			buf.append((i + 1) + " : " + list.elementAt(i).toString() + '\n');
		return buf.toString();
	}
	
	/**
	 * ������ ����Ʈ�� �߰�.  �� �� �޼��� ����
	 * @param newUser ���� �߰��� �����
	 * @param operatorName �߰� �۾��� �ϰ� �ִ� �����ڸ�
	 */
	public void add(Food newFood, String operatorName)
	{
		list.add(newFood);
		createUpdateMessage(UpdateMessageType.Addition, newFood.getName(), operatorName);
	}
	
	/**
	 * ����� ����Ʈ ��ġ�� �̿��� ����� ����
	 * @param idx ������ ������� ����Ʈ �� ��ġ
	 * @param operatorName ���� �۾��� �ϰ� �ִ� �����ڸ�
	 */
	public void delete(int idx, String operatorName)
	{
		Food deleted = list.remove(idx);
		createUpdateMessage(UpdateMessageType.Removal, deleted.getName(), operatorName);
		deleted = null;
	}
	
	public void replace(int idx, Food newFood, FoodEditType act, String operatorName)
	{
		list.set(idx, newFood);
		createUpdateMessage(act, newFood.getName(), operatorName);
	}
	
	public Food elementAt(int idx) { return list.elementAt(idx); }
	
	/**
	 * ������Ʈ �޼��� ���� �� �޼��� ��Ͽ� �߰�
	 * @param t ������Ʈ �޼��� ����
	 * @param tgtFoodName ������Ʈ �ش��ϴ� ���� �̸�
	 * @param operatorName ������Ʈ�� �ϴ� ������ �̸�
	 */
	private void createUpdateMessage(UpdateMessageType t, String tgtFoodName, String operatorName)
	{
		UpdateMessage newMessage = null;
		switch(t)
		{
		case Addition:
			newMessage = new UpdateMessage("New Food " + tgtFoodName + "stored by " + operatorName, operatorName);
		case Removal:
			newMessage = new UpdateMessage("Food " + tgtFoodName + "taken by " + operatorName, operatorName);
			break;
		default: break;
		}
		if(null != newMessage) RefrigeratorSystem.getMessageList().add(newMessage);
	}
		
	/**
	 * ���� ���� ������Ʈ �޼��� ���� �� �޼��� ��Ͽ� �߰�
	 * @param act ������ �׸�
	 * @param tgtFoodName ������ ���ĸ�
	 * @param operatorName ������ ��� �̸�
	 */
	private void createUpdateMessage(FoodEditType type, String tgtFoodName, String operatorName)
	{
		UpdateMessage newMessage = null;
		switch(type)
		{
		case FreezerCooler:
		case Location:
			newMessage = new UpdateMessage("Food " + tgtFoodName + " was moved by " + operatorName, operatorName);
			break;
		case Weight:
			newMessage = new UpdateMessage("Food " + tgtFoodName + "'s weight was modified by " + operatorName, operatorName);
			break;
		case Quantity:
			newMessage = new UpdateMessage("Food " + tgtFoodName + "'s quantity was modified by " + operatorName, operatorName);
			break;
		}
		if(null != newMessage) RefrigeratorSystem.getMessageList().add(newMessage);
	}
	
	/**
	 * warning �޼��� ������ �޼��� ��Ͽ� �߰�
	 * @param t ���׸޼��� ����
	 * @param FoodName �˶� ��� ����
	 * @param tgtUserName ���� ���� ���
	 */
	private void createWarningMessage(WarningMessageType t, Food tgtFood, String tgtUserName)
	{
		WarningMessage newMessage = null;
		switch(t)
		{
		case FoodExpired:
			newMessage= new WarningMessage("Food expired : Name -> " + tgtFood.getName() +
			         						", Location : " + (tgtFood.isFreezerItem()? "Freezer" : "Cooler") + 
			        						", Row " + tgtFood.getLocation().first + ", Column " + tgtFood.getLocation().second, tgtUserName);
			break;
		case FoodNearExpiration:
			newMessage = new WarningMessage("Food near to expired in "+ Calendar.getInstance().compareTo(tgtFood.getExpirationDate()) + 
			           						" days : Name -> " + tgtFood.getName() + ", Location : " +
			           						(tgtFood.isFreezerItem()? "Freezer" : "Cooler") + ", Row " + tgtFood.getLocation().first +
			           						", Column " + tgtFood.getLocation().second, tgtUserName);
			break;
		case ForbiddenFood:
			newMessage = new WarningMessage("Prohibited food in refrigerator : " + tgtFood.getName(), tgtUserName); 
		default: break;
		}
		if(newMessage != null)
			RefrigeratorSystem.getMessageList().add(newMessage);
	}
	
	/**
	 * ������� �����ų� 3�� ���Ϸ� ���Ҵ��� Ȯ���� �� ��� �޼��� ����.<p>
	 */
	public void checkExpired()
	{
		for(Food tmp: list) //�ϳ��ϳ� ��
		{
			if(tmp.isExpired())
				createWarningMessage(WarningMessageType.FoodExpired, tmp, "System");
			else if(Calendar.getInstance().compareTo(tmp.getExpirationDate()) <= 3)
				createWarningMessage(WarningMessageType.FoodNearExpiration, tmp, "System");
		}
	}
	
	public void checkFoodName()
	{
		for(Food tmp : list)
		{
			if(tmp.isProhibited())
				createWarningMessage(WarningMessageType.ForbiddenFood, tmp, "System");
		}
	}
}
