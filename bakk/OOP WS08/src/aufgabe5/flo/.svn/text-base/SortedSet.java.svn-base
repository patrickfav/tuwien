package aufgabe5.flo;

/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Creates a SortedSet
 * 
 */
public class SortedSet<A extends Comparator<A>> {
	
	SortedList<A> list;
	
	public SortedSet(){
		// creates a new SortedList
		this.list = new SortedList<A>();
	}
	
	public void insert(A elem) {
		// expects Element elem
		if (!isAlreadyElement(elem)){			
			list.add(elem);
			// inserts Element if Element is already in list
		}
	}
	
	public Iterator<A> iterator() {
		// returns iterator of the list
		return list.iterator();
	}

	protected boolean isAlreadyElement(A elem) {
		// expects element elem
		Iterator<A> iter = list.iterator();
		 
		while(iter.hasNext()){
			// while there are more elements
			A dumpElem = iter.next();
			if (elem.equals(dumpElem)) return true;
		}
		return false;
		// true if Element is already in the list
	}

}