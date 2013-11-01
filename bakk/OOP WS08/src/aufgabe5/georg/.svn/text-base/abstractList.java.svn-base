/**
 * 
 */
package aufgabe5.georg;


/**
 * @author GEMEH
 *
 */
public abstract class abstractList<A> {

	protected Node head = null;
	protected Node tail = null;
	
	protected class Node{
		A elem;
		Node next = null;
		Node (A elem) {this.elem = elem;}
		
	}
	
	protected class ListIter implements Iterator<A>{
		protected Node p = head;
		
		public ListIter(){
			p = head;
			System.out.println("ListIter: Wurzelknoten ist "+p);
		}
		
		public boolean hasNext() {
			return p != null;
		}
		
		public A next(){
			if (p == null) return null;
			
			A elem = p.elem;
			p = p.next;
			return elem;
		}
	}
}
