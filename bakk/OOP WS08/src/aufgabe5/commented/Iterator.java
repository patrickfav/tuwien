package aufgabe5.commented;

/**
 * @author GEMEH, FLRAU, PAFAV
 * 
 * Iterator interface
 * 
 */
public interface Iterator<A> {
	// returns the next Element
	A next();
	// returns true if there is a next Element 
	boolean hasNext();
}