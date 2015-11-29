package Problem_Domain;

import java.util.Vector;

public class MessageList
{
	private Vector<Message> list;
	
	/**
	 * �޼����� �߰��� �� ���.  �޼����� ��Ͽ� �߰��� �� �߰��۾��� ���� ����.
	 * @param m �߰��� �޼���
	 */
	public void add(Message m)
	{
		list.add(m);
	}
	
	/**
	 * ������ ���� �޼����� ������.
	 */
	public void checkOutofDate()
	{
		for(int i = 0; i < list.size(); ++i)
		{
			if(list.elementAt(i).isExpired())
			{
				list.remove(i);
			}
				
		}
	}
	
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
	
}
