/**
 * 
 */
package aufgabe5.alt;

/**
 * @author GEMEH
 *
 */
public class List<A> implements Collection<A> {
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
	
	
	/* (non-Javadoc)
	 * @see aufgabe5.georg.Collection#add(java.lang.Object)
	 */
	
	public void add(A x) {
		if (head == null) tail = head = new Node(x);
		else tail = tail.next = new Node(x);

	}

	/* (non-Javadoc)
	 * @see aufgabe5.georg.Collection#iterator()
	 */
	public Iterator<A> iterator() {
		return new ListIter();
	}

}