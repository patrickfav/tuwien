package aufgabe5;

public class List<Element> implements Collection<Element> {
	protected class Node {
		Element elem;
		Node next = null;
		Node (Element elem) {
			this.elem = elem;
		}
	}
	protected Node head = null, tail = null;
	protected class ListIter implements Iterator<Element> {
		protected Node p = head;
		public boolean hasNext() {
			return p != null;
		}
		public Element next() {
			if(p == null)
				return null;
			Element elem = p.elem;
			p = p.next;
			return elem;
		}
	}
	public void add(Element x) {
		if(head == null)
			tail = head = new Node(x);
		else
			tail = tail.next = new Node(x);
	}
	public Iterator<Element> iterator() {
		return new ListIter();
	}
}
