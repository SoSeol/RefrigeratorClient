package Problem_Domain;

import java.util.Calendar;

public class Message
{
	private final static byte DEFAULT_EXPIRE_DATE = 3;
	private String messageDetail;
	private Calendar createdDate;
	private Calendar messageUntil;
	private String createdUserName;
	
	/**
	 * �޼��� ��ü ������.
	 * @param detail ����
	 * @param until �Խ� ��������
	 * @param created �޼��� ������ �����
	 */
	protected Message(String detail, String created, Calendar until)
	{
		//messageType = type;
		messageDetail = detail;
		createdDate = Calendar.getInstance();
		messageUntil = until;
		createdUserName = created;
	}
	
	protected Message(String detail, String created)
	{
		this(detail, created, getAfterDay());
	}
	
	public boolean isExpired() { return messageUntil.before(Calendar.getInstance()); }
	protected String getCreatedUserName() { return createdUserName; }
	protected Calendar getCreatedDate() { return createdDate; }
	protected Calendar getEndDate() { return messageUntil; }
	protected String getMessageDetail() { return messageDetail; }

	
	/**
	 * ���� ��¥���� cnt�� ���� ��¥�� ����ؼ� ��ȯ��
	 * @param cnt ���� �� ����
	 * @return ����� ��¥
	 */
	private static Calendar getAfterDay(int cnt)
	{
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, cnt);
		return date;
	}
	
	/**
	 * �⺻ ������ ���� ��¥�� ����ؼ� ��ȯ��
	 * @return �⺻ ������ ���� ��¥
	 */
	private static Calendar getAfterDay() { return getAfterDay(DEFAULT_EXPIRE_DATE); }
	
	/**
	 * �޼��� ���ڿ��� ��ȯ<p>
	 * ��ȯ ����� "[��������/������] '�޼��� ����'"
	 */
	public String toString()
	{
		return '[' + getCreatedDate().toString() + '/' + getCreatedUserName() + "] " + getMessageDetail();
	}
}
