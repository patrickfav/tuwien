/**
 * 
 */
package aufgabe5.georg;

/**
 * @author GEMEH
 *
 */
public class SortedSet<A extends Comperator<A>> extends Set<A> {
	//SortedList<A> list;
	
	public SortedSet(List<A> listObj){
		super(listObj);
	}
	
	public void insert(A elem) {
		if (!isAlreadyElement(elem)){
			list.add(elem);
			System.out.println("Element " +elem.toString() +" wurde in die Liste hinzugefügt");
		}
	}
	
/*	protected void insertInSortedPosition(A elem){
		Iterator<A> iter = list.iterator();
		
		//wenn liste leer
		if (iter.hasNext()) list.add(elem);
		else{
			//wenn liste nicht leer
			A p= iter.next();
			A g =null;
			//solange x schneller als y -> p=iter.next()
			while(p!= null && elem.faster(p)){
				g = p;
				p=iter.next();
			}
			
			while(iter.hasNext()){
				A dumpElem = iter.next();
				if (elem.equals(dumpElem)) return true;
			}
		}
		
	}*/
}