package swag.helper;

import swag.models.Troop;

public class TroopCountWrapper implements Comparable{
	
	private Troop troop;
	private long count;
	
	
	public TroopCountWrapper() {
		super();
	}
	
	public TroopCountWrapper(Troop troop, long count) {
		super();
		this.troop = troop;
		this.count = count;
	}
	
	public Troop getTroop() {
		return troop;
	}
	public void setTroop(Troop troop) {
		this.troop = troop;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof TroopCountWrapper) {
			
			if(((TroopCountWrapper)o).getCount() > count)
				return 1;
			
			if(((TroopCountWrapper)o).getCount() < count)
				return -1;
		}
		
		return 0;
	}
	
	
}
