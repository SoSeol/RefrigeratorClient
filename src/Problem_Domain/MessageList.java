package Problem_Domain;

import java.util.Vector;

public class MessageList
{
	private Vector<Message> list;
	
	/**
	 * 메세지를 추가할 때 사용.  메세지를 목록에 추가한 뒤 추가작업은 하지 않음.
	 * @param m 추가할 메세지
	 */
	public void add(Message m)
	{
		list.add(m);
	}
	
	/**
	 * 기한이 지난 메세지를 삭제함.
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
	
}
