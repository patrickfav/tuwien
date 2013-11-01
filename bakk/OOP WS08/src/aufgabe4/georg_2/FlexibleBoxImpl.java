/**
 * 
 */
package aufgabe4.georg_2;

/**
 * @author GEMEH
 *
 */
public abstract class FlexibleBoxImpl extends BoxImpl {
	FillBehavior fillBehavior;
	
	public void setFillChar(char ifc){
		fillBehavior.setFillChar(ifc);
	}
}
