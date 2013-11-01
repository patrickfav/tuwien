package aufgabe5;

public class SortedSet<Element> {
	
	List<Element> sortedList;

	public SortedSet() {
		this.sortedList = new List<Element>();
	}
	public void insert(Element elem) {
		this.sortedList.add(elem);
		sortList();
	}
	public Iterator<Element> getIterator() {
		return this.sortedList.iterator();
	}
	public List<Element> getList() {
		return this.sortedList;
	}
	private void sortList() {
		// Sorting	
	}
	
}
