package aufgabe5.commented;

/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Creates a simple List
 * 
 */
public class List<A> implements Collection<A> {
	protected Node head = null;
	protected Node tail = null;
	
	/**
	 * Subclass Node
	 */
	protected class Node {
		A elem;
		Node next = null;
		Node (A elem) {this.elem = elem;}
	}
	/**
	 * Subclass Iterator
	 */
	protected class ListIter implements Iterator<A>{
		protected Node p = head;
		
		public ListIter() {
			// Iterator begins with head
			p = head;
		}
		
		public boolean hasNext() {
			// true if there is a next Element
			return p != null;
		}
		
		public A next(){
			// returns the next Element
			if (p == null) return null;
			
			A elem = p.elem;
			p = p.next;
			return elem;
		}
	}

	public void add(A x) {
		// Expects an Element x and inserts it into the list
		if (head == null) tail = head = new Node(x);
		else tail = tail.next = new Node(x);
	}	

	public Iterator<A> iterator() {
		// returns the iterator of the list
		return new ListIter();
	}

}