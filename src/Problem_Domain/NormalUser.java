package Problem_Domain;

public class NormalUser extends User {

	public NormalUser(String newName, String newID, String newPW)
	{
		super(newName, newID, newPW);
		
	}
	
	
	public String toString()
	{
		return "Normal_User, ID : " + getID() + ", Name : " + getName();
	}
}
