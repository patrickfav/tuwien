package aufgabe5.flo;

/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Creates a SortedList
 * 
 */
public class SortedList<A extends Comparator<A>> extends List<A> {
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

	public Iterator<A> iterator() {
		// returns the iterator of the list
		return new ListIter();
	}
	
	public void add(A x) {
		Node current = head; 
		// actual node & head at beginning
		Node previous = null;
		// predecessor node
		Node newNode = new Node(x);
		// node to be inserted
		
		// go to the end of the list OR till the current node is faster than the newNode
		while( (current != null) && (newNode.elem.faster(current.elem)) ){
			previous = current; 
			// predecessor is current
			current = current.next; 
			// current is successor of current
		}
		// if there are no elements
		if(previous == null) {
			head = newNode;
			// newNode is the head of the list
			System.out.println("Element wurde am Anfang eingefuegt.");
		}
		else {
			previous.next = newNode;
			// newNode is inserted in the list
			System.out.println("Element wurde in die Liste eingefuegt.");
		}
		newNode.next = current;
		
	}
}