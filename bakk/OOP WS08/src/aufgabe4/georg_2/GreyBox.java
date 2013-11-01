/**
 * 
 */
package aufgabe4.georg_2;

/**
 * @author GEMEH
 *
 */
public class GreyBox extends BoxImpl {
	
	public GreyBox(int width, int height, char fc, char ifc){
		fillBehavior = new ColoredFilling(width, height, fc, ifc);
	}
}
