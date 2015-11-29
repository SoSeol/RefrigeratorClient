package Problem_Domain;

import java.util.Calendar;
import Essential.Pair;

public class Food
{	
	private String name;
	private int quantity;
	private int weight;
	private int calories;
	private boolean bFreezerItem;
	private Pair<Integer, Integer> location;
	private Calendar expirationDate;
	private Calendar insertedDate;

	/**
	 * @param newName ���� �̸�
	 * @param newQuantity ���� ����
	 * @param newWeight ���� ����
	 * @param newCalories ������ Į�θ�
	 * @param bFreezer �õ����̸� true, ������̸� false
	 * @param row ���° ��
	 * @param col ���ʿ��� ���°?
	 * @param newExpirationDate �������
	 */
	public Food
		(String newName, int newQuantity, int newWeight, int newCalories, boolean bFreezer, int row, int col, Calendar newExpirationDate)
	{
		name = newName;
		quantity = newQuantity;
		weight = newWeight;
		calories = newCalories;
		bFreezerItem = bFreezer;
		location = new Pair<Integer, Integer>(row, col);
		expirationDate = newExpirationDate;
		insertedDate = Calendar.getInstance();
	}
	
	public boolean isProhibited() { return FoodList.getProhibitedList().indexOf(name) != -1; }
	public boolean isExpired() { return Calendar.getInstance().before(expirationDate); }
	public String getName() { return name; }
	public int getQuantity() { return quantity; }
	public int getWeight() { return weight; }
	public int getCalories() { return calories; }
	public Calendar getExpirationDate() { return expirationDate; }
	public boolean isFreezerItem() { return bFreezerItem; }
	public void setQuantity(int newQ) { quantity = newQ; }
	public void setWeight(int newW) { weight = newW; }
	public void setLocation(int row, int col) { location = new Pair<Integer, Integer>(row, col); }
	public void toggleFreezerCooler() { bFreezerItem = !bFreezerItem; }
	public Pair<Integer, Integer> getLocation() { return location; }
	public String toString()
	{
		return name + " : Quantity -> " + quantity + ", weight -> " + weight + "g, calories -> " + calories + " each, Location"
				+ (bFreezerItem ? "Freezer " : "Cooler") + ", Row " + location.first + ", Column " + location.second
				+ "Inserted at " + insertedDate + ", Expiration Date -> " + expirationDate;
	}
}
