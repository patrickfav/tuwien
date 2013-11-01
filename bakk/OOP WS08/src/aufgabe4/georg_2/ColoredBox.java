/**
 * 
 */
package aufgabe4.georg_2;

/**
 * @author GEMEH
 *
 */
public class ColoredBox extends FlexibleBoxImpl {

	public ColoredBox(int width, int height, char fc, char ifc){
		fillBehavior = new ColoredFilling(width, height, fc, ifc);
	}
}
