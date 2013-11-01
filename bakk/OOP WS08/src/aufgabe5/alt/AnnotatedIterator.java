/**
 * 
 */
package aufgabe5.alt;

/**
 * @author GEMEH
 *
 */
public interface AnnotatedIterator<A, B> extends Iterator<A>{

	public Iterator<B> iterator();
}
