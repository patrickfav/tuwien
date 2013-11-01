/**
 * 
 */
package aufgabe5.alt;






/**
 * @author GEMEH
 *
 */
public class AnnotatedList<A extends Comperator<? super A>, B> /*extends abstractSortedList<A>*/ {
	protected Node head = null;
	protected Node tail = null;
	
	public void add(A x) {
		Node current = head; //actual node & head at beginning
		Node previous = null; //predecessor node
		Node newNode = new Node(x); //node to be inserted
		
		//go to the end of the list OR till the current node is faster than the newNode
		while( (current != null) && (newNode.elem.faster(current.elem)) ){
			previous = current; //predecessor is current
			current = current.next; //current is successor of current
		}
		//if there are no elements
		if(previous == null) {head = newNode;//newNode is the head of the list
			System.out.println("Element wurde am Anfang eingefügt.");
		}
		else {previous.next = newNode; //newNode is inserted in the list
			System.out.println("Element wurde in die Liste eingefügt.");
		}
		newNode.next = current;
		
	}
	
	protected class Node{
		A elem;
		Node next = null;
		List<B> notes;
		Node (A elem) {this.elem = elem;this.notes = new List<B>();}
	}
	
	protected void annotate(A elem, B note){
		Node current = head;
		
		while( /*(current != null) ||*/ (current.elem != elem) ){
			current = current.next; //current is successor of current
			if (current == null) break;
		}
		//if there are no elements
		if(current == null) {System.out.println("ERROR: No node found to attache a note");}
		else {
			if(current.notes==null){
				current.notes = new List<B>();
			}
			current.notes.add(note); System.out.println("added "+note);
		}
	}

	protected class ListIter implements AnnotatedIterator<A, B>{
		protected Node p = head;
		
		public boolean hasNext() {
			return p != null;
		}
		
		public A next(){
			if (p == null) return null;
			
			A elem = p.elem;
			p = p.next;
			return elem;
		}
		
		public Iterator<B> iterator() {
			System.out.println("1");
			System.out.println("p:"+p);
			System.out.println("notes:"+p.notes);
			Iterator<B> i = p.notes.iterator();
			System.out.println("2");
			return i;
		}
	}
	
	public AnnotatedIterator<A,B> iterator() {
		return new ListIter();
	}

}