package Problem_Domain;

import java.util.Calendar;

public class UpdateMessage extends Message
{
	
	/**
	 * Ư�� �������ڸ� ������ ��� ����� ������
	 * @param detail �޼��� ����
	 * @param created �޼��� ������
	 * @param messageUntil �Խø�������
	 */
	public UpdateMessage(String detail, String created, Calendar messageUntil)
	{
		super(detail, created, messageUntil);
	}
	
	/**
	 * Ư�� �������ڸ� �������� ���� ��� ����� ������
	 * @param detail �޼��� ����
	 * @param created �޼��� ������
	 */
	public UpdateMessage(String detail, String created)
	{
		super(detail, created);
	}
}
