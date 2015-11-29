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
		prohibitedList.add("마약");
		prohibitedList.add("마리화나");
		prohibitedList.add("본드");
		prohibitedList.add("필로폰");
		prohibitedList.add("대마초");
		prohibitedList.add("청산가리");
		list = new Vector<Food>();
	}
	
	public static ArrayList<String> getProhibitedList() { return prohibitedList; }
	
	/**
	 * 리스트를 문자열로 변환해줌
	 * @return 문자열화 된 리스트
	 */
	public String showList()
	{
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < list.size(); ++i)
			buf.append((i + 1) + " : " + list.elementAt(i).toString() + '\n');
		return buf.toString();
	}
	
	/**
	 * 음식을 리스트에 추가.  그 후 메세지 생성
	 * @param newUser 새로 추가할 사용자
	 * @param operatorName 추가 작업을 하고 있는 관리자명
	 */
	public void add(Food newFood, String operatorName)
	{
		list.add(newFood);
		createUpdateMessage(UpdateMessageType.Addition, newFood.getName(), operatorName);
	}
	
	/**
	 * 사용자 리스트 위치를 이용해 사용자 삭제
	 * @param idx 제거할 사용자의 리스트 내 위치
	 * @param operatorName 삭제 작업을 하고 있는 관리자명
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
	 * 업데이트 메세지 생성 후 메세지 목록에 추가
	 * @param t 업데이트 메세지 종류
	 * @param tgtFoodName 업데이트 해당하는 음식 이름
	 * @param operatorName 업데이트를 하는 관리자 이름
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
	 * 수정 관련 업데이트 메세지 생성 후 메세지 목록에 추가
	 * @param act 수정한 항목
	 * @param tgtFoodName 수정한 음식명
	 * @param operatorName 수정한 사람 이름
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
	 * warning 메세지 생성후 메세지 목록에 추가
	 * @param t 워닝메세지 종류
	 * @param FoodName 알람 대상 음식
	 * @param tgtUserName 음식 넣은 사람
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
	 * 유통기한 지났거나 3일 이하로 남았는지 확인한 후 경고 메세지 제작.<p>
	 */
	public void checkExpired()
	{
		for(Food tmp: list) //하나하나 들어감
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
