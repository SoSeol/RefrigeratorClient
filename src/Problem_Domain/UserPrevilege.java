
package Problem_Domain;

public enum UserPrevilege
{
	Normal, Administrator;
	
	public String toString()
	{
		if(this == Normal) return "Normal User";
		return "Administrator";
	}
}
