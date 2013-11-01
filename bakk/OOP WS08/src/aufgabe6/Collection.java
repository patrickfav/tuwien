package aufgabe6;

/**
 * 
 * interface collection
 * represents the standard methods for a collection 
 *
 */
public interface Collection {
	/**
	 * adds the element in the collection
	 * @param elem
	 */
	void add (Object elem);
	/**
	 * removes the element from the collection
	 * @param elem
	 */
	void remove (Object elem);
	/**
	 * returns an iterator for the collection
	 * @return
	 */
	Iterator iterator();
}
