/**
 * 
 */
package aufgabe5.georg;

import aufgabe5.flo.AnnotatedIterator;

/**
 * @author GEMEH
 *
 */
public interface AnnotatedCollection <A, B>{
	void add (A elem);
	AnnotatedIterator<A,B> iterator();

}