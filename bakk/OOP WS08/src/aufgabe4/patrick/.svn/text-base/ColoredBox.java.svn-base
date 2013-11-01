package aufgabe4.patrick;

/**
 * class ColoredBox
 * 
 * extends  AbstractExtendedBox abstract class
 * 
 * @author pfb
 *
 */

public class ColoredBox extends  AbstractExtendedBox{
	
	
	public ColoredBox(int width, int height, char fc,char flc) {
		super(width, height, fc,flc);
	}
	
	/**
	 * Immediate prints out the box.
	 * Attributes FrameChar,FiLlingChar, width and height must be defined 
	 * (by constructor or methods)
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
					System.out.print(flc);
				}
			}
	}
	
	
}
