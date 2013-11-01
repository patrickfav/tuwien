package aufgabe4.patrick;

/**
 * class GreyBox
 * extends AbstractSimpleBox class
 * 
 * @author pfb
 *
 */

public class GreyBox extends AbstractSimpleBox{
	
	private static char fill;
	
	public GreyBox(int width, int height, char fc, char fill) {
		super(width, height, fc);
		GreyBox.fill = fill;
	}
	
	/**
	 * Immediate prints out the box.
	 * Attributes fc,fill, width and height must be defined 
	 * (by constructor or methods, except fill)
	 */
	public void print() {

		for(int i = 0;i<height;i++)
			for(int j=0;j<width;j++) {
				
				if(j == width-1){
					System.out.println(fc);
				}
				else if (j == 0 || i == 0 || i == height-1) {
					System.out.print(fc);
				}
				else {
					System.out.print(fill);
				}
			}
	}
}
