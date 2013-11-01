package aufgabe4.patrick;

/**
 * AbstractSimpleBox defines/implements all of the box methods except 
 * for the print() method, which is defined in the respective subtyp.
 * 
 * @author pfb
 *
 */

public abstract class AbstractSimpleBox implements Box {
	
	protected int width;
	protected int height;
	protected char fc;
	
	public AbstractSimpleBox(int width, int height, char fc) {
		this.setSize(width,height);
		this.setFrameChar(fc);
	}
	/**
	 * will need the print implementations from the subtypes
	 * will writes several lines representing
	 * the box "this" to standard output
	 */
	public abstract void print();
	
	/**
	 * Sets the char of which the frame of the box consists of
	 * 
	 * @param fc
	 */
	public void setFrameChar(char fc) {
		this.fc = fc;
	}
	
	/**
	 * Will set the size of the box.
	 * 3 <= width <= 40; 3 <= height <= 10 (preconditions)
	 * If the dimensions are not within the above rules,
	 * the sizes will be cut down to the next allowed value.
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		
		//preconditions
		if(width < 3) {
			width =3;
		}
		else if (width>40) {
			width = 40;
		}
		
		if(height < 1) {
			height = 1;
		}
		else if(height > 10) {
			height=10;
		}
		
		this.width = width;
		this.height = height;
	}
}
