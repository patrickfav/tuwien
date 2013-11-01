package aufgabe4.patrick;

/**
 * class WhiteBox
 * extends AbstractSimpleBox class
 * 
 * @author pfb
 *
 */

public class WhiteBox extends AbstractSimpleBox {
	
	
	public WhiteBox(int width, int height, char fc) {
		super(width, height, fc);
	}
	
	/**
	 * Immediate prints out the box.
	 * Attributes fc, width and height must be defined 
	 * (by constructor or methods)
	 */
	
	public void print() {

		for(int i = 0;i<height;i++)
			for(int j=0;j<width;j++) {
				
				if(j == 0 || (i == 0 && j != width-1) || i == height-1) {
					System.out.print(fc);
				}
				else if(j == width-1){
					System.out.println(fc);
				}
				else {
					System.out.print(' ');
				}
			}
	}

	

}
