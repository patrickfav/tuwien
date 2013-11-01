/**
 * 
 */
package aufgabe5.alt;

/**
 * @author GEMEH
 *
 */
public class Set<A> {
	List<A> list;
	
	public Set(List<A> listObj){
		list = listObj;
	}
	
/*	public Set(AnnotatedList<A,B> listObj){
		list = listObj;
	}*/
	
	public void insert(A elem) {
		list.add(elem);
	}
	
	public Iterator<A> iterator() {
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