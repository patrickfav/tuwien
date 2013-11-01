package swag.gui.comparator;

import java.util.Comparator;

import swag.models.Message;


public class MessageComparator<T> implements Comparator<T>{

	@Override
	public int compare(T o1, T o2) {
		if(o1 instanceof Message) {
			Message m1 = (Message) o1;
			Message m2 = (Message) o2;
			
			if(m1.getSendDate().getTime() > m2.getSendDate().getTime())
				return -1;
			
			if(m1.getSendDate().getTime() < m2.getSendDate().getTime())
				return 1;
			
			return 0;
		}
		
		return 0;
	}

}
