/**
 * 
 */
package aufgabe5.alt;

/**
 * @author GEMEH
 *
 */
public class AnnotatedSet<A extends Comperator<? super A>,B> /*extends Set<A> */{
	AnnotatedList<A, B> list;
	
	public AnnotatedSet(AnnotatedList<A, B> listObj){
		/*super(listObj);*/
		list = new AnnotatedList<A, B>();
	}
	
	public void annotate(A elem, B note){
		if (isAlreadyElement(elem)){
			list.annotate(elem, note);
		}
	}
	
	
	public void insert(A elem) {
		if (!isAlreadyElement(elem)){
			list.add(elem);
			System.out.println("Element " +elem.toString() +" wurde in die Liste hinzugefügt");
		}
	}
	
	public AnnotatedIterator<A,B> iterator() {
		return list.iterator();
	}
	
	// ueberprueft, ob Element in der Menge ist
	// wird von Sorted und Annoted Set benuetzt
	protected boolean isAlreadyElement(A elem) {
		Iterator<A> iter = list.iterator();
		  
		while(iter.hasNext()){
			A dumpElem = iter.next();
			if (elem.equals(dumpElem)) return true;
		}
		return false;
	}
}