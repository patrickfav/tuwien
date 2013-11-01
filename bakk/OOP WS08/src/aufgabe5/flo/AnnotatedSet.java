package aufgabe5.flo;



/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Creates an AnnotatedSet
 * 
 */
public class AnnotatedSet<A extends Comparator<? super A>,B> {
	AnnotatedList<A, B> list;
	
	public AnnotatedSet(){
		// creates an anntotatedList
		list = new AnnotatedList<A, B>();
	}
	
	public void annotate(A elem, B note){
		// expects an Element and a Note 
		if (isAlreadyElement(elem)){
			// inserts note if Element is in list
			list.annotate(elem, note);
		} 
	}
	
	public void insert(A elem) {
		if (!isAlreadyElement(elem)){
			// inserts Element to the list if its not there
			list.add(elem);
		}
	}
	
/*	public Iterator<A> iterator() {
		// returns the Iterator of the AnnotatedList
		return list.iterator();
	}*/
	
	public AnnotatedIterator<A,B> iterator() {
		return list.iterator();
	}
	
/*	public List<B> getNotes(A elem) {
		// expects an Element in the annotatedList
		return list.getNotes(elem);
		// returns the list of notes
	}*/
	
	protected boolean isAlreadyElement(A elem) {
		// expects an Element
		Iterator<A> iter = list.iterator();
		  
		while(iter.hasNext()){
			A dumpElem = iter.next();
			if (elem.equals(dumpElem)) return true;
		}
		return false;
		// returns true if Element is in the annotatedList
	}
}