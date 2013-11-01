/**
 * 
 */
package aufgabe4.georg_2;

/**
 * @author GEMEH
 *
 */
public class WhiteBox extends BoxImpl {
	
	public WhiteBox(int width, int height, char fc){
		fillBehavior = new ColoredFilling(width, height, fc, ' ');
	}
}
