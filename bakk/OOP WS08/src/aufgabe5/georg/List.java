/**
 * 
 */
package aufgabe5.georg;

/**
 * @author GEMEH
 *
 */
public class List<A> extends abstractList<A> implements Collection<A> {
	
	
	
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