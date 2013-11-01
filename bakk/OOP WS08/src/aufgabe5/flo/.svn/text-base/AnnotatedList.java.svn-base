package aufgabe5.flo;



/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Creates an AnnotatedList
 * 
 */
public class AnnotatedList<A extends Comparator<? super A>, B> {
	protected Node head = null;
	protected Node tail = null;
	
	/**
	 * Subclass Node
	 */
	protected class Node{
		A elem;
		Node next = null;
		List<B> notes;
		Node (A elem) {
			this.elem = elem;
			this.notes = new List<B>();
		}
		
	}
	/**
	 * Subclass Iterator
	 */
	protected class ListIter implements AnnotatedIterator<A, B>{
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
		
		public Iterator<B> iterator() {
			// returns the iterator of the notes list
			return p.notes.iterator();
		}
	}
	protected void annotate(A elem, B note){
		Node current = head;
		
		while( (current != null) && (current.elem != elem) ){
			current = current.next;
			//current is successor of current
		}

		if(current == null) System.out.println("ERROR: No node found to attache a note");
		else current.notes.add(note);
		// inserts an note to the list
	}
	
	public List<B> getNotes(A elem) {
		// expects an Element
		Node current = head;
		while( (current != null) && (current.elem != elem) ){
			current = current.next; 
			//current is successor of current
		}
		//if there are no elements
		if(current == null) return null;
		else return current.notes;
		// returns the list of notes from Element
	}
	
	
	
	public void add(A x) {
		// expects Element x
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
		
		//if there are no elements
		if(previous == null) {
			head = newNode;
			// newNode is the head of the list
			System.out.println("Element wurde am Anfang eingefuegt.");
		} else {
			previous.next = newNode;
			//newNode is inserted in the list
			System.out.println("Element wurde in die Liste eingefuegt.");
		}
		newNode.next = current;
		
	}
	public AnnotatedIterator<A,B> iterator() {
		return new ListIter();
	}
	
}