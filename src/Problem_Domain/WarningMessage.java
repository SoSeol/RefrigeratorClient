package Problem_Domain;

import java.util.Calendar;

public class WarningMessage extends Message
{
	/**
	 * Ư�� �������ڸ� ������ ��� ����� ������
	 * @param detail �޼��� ����
	 * @param created �޼��� ������
	 * @param messageUntil �Խø�������
	 */
	public WarningMessage(String detail, String created, Calendar until)
	{
		super(detail, created, until);
	}
	
	/**
	 * Ư�� �������ڸ� �������� ���� ��� ����� ������
	 * @param detail �޼��� ����
	 * @param created �޼��� ������
	 */
	public WarningMessage(String detail, String created)
	{
		super(detail, created);
	}
	
	/**
	 * WarningMessage �޼��� ���ڿ��� ��ȯ
	 * ��ȯ ����� "[���� ����/������] Warning : '�޼��� ����'"
	 */
	public String toString()
	{
		String[] splitted = super.toString().split(" ");
		return splitted[0] + "Warning : " + splitted[1];
		
	}
	
}
