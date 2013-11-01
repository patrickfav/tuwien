/**
 * 
 */
package aufgabe5.georg;

import aufgabe5.flo.AnnotatedIterator;



/**
 * @author GEMEH
 *
 */
public class AnnotatedList<A extends Comperator<? super A>, B> extends abstractList<A> implements AnnotatedCollection<A, B>{
	protected Node head = null;
	protected Node tail = null;
	
	protected class Node{
		A elem;
		Node next = null;
		Node (A elem) {this.elem = elem;}
		List<B> notes;
	}
	
	protected void annotate(A elem, B note){
		Node current = head;
		
		while( (current != null) && (current.elem != elem) ){
			current = current.next; //current is successor of current
		}
		//if there are no elements
		if(current == null) System.out.println("ERROR: No node found to attache a note");
		else current.notes.add(note);
	}

	protected class ListIter implements Iterator<A>{
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
		
/*		public AnnotatedIterator<B> iterator() {
			return p.notes.iterator();
		}*/
		
	}
	
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

	@Override
	public AnnotatedIterator<A, B> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}