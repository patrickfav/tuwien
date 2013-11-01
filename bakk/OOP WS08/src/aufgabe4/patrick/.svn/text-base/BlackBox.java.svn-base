package aufgabe4.patrick;

/**
 * class BlackBox
 * extends AbstractSimpleBox class
 * 
 * @author pfb
 *
 */

public class BlackBox extends AbstractSimpleBox {
	
	public BlackBox(int width, int height, char fc) {
		super(width,height,fc);
	}
	
	/**
	 * Immediate prints out the box.
	 * Attributes fc, width and height must be defined 
	 * (by constructor or methods)
	 */
	public void print() {

		for(int i = 0;i<height;i++)
			for(int j=0;j<width;j++) {
				
				if(j == width-1 && i != height-1) {
					System.out.println(fc);
				}
				else {
					System.out.print(fc);
				}
			}
	}
}