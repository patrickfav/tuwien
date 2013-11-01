package aufgabe6;

public class List implements Collection {
	
	protected class Node {
		Object elem; Node next = null;
		Node (Object elem) { 
			this.elem = elem; 
		}
	}
	protected Node head = null, tail = null;
	
	/**
	 * 
	 * protrected inner class ListIter
	 * provides Iterationfunctionality to objects of List
	 *
	 */
	protected class ListIter implements Iterator {
		protected Node p = head;
		
		/**
		 * returns true if the List has another following element
		 */
		public boolean hasNext() { 
			return p != null; 
		}
		/**
		 * returns the next element of the list. 
		 * If there is no element in the ListNULL is returned
		 */
		public Object next() {
			if (p == null)
				return null;
			Object elem = p.elem;
			p = p.next;
			return elem;
		}
	}
	/**
	 * adds the given Object into the List
	 */
	public void add (Object x) {
		if (head == null)
			tail = head = new Node(x);
		else
			tail = tail.next = new Node(x);
	}
	/**
	 * removes the given Object from the List
	 */
	public void remove (Object x) {
		Node k = head;
		if(k.elem.equals(x))
			head = head.next;
		while(k.next != null) {
			if(k.next.elem.equals(x)) {
				k.next = k.next.next;
			} else {
				k = k.next;
			}
		}
	}
	
	/**
	 * returns an iterator of the List instance
	 */
	public Iterator iterator() {
		return new ListIter();
	}
}
