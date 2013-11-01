/**
 * 
 */
package aufgabe5.georg;


/**
 * @author GEMEH
 *
 */
public class SortedList<A extends Comperator<A>> extends abstractSortedList<A>{


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
}