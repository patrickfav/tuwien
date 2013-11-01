/**
 * 
 */
package aufgabe4.georg_2;

/**
 * @author GEMEH
 *
 */
public class ColoredFilling implements FillBehavior {
	int width;
	int height;
	char fc;
	char ifc;
	
	public ColoredFilling(int width, int height, char fc, char ifc){
		this.width=width;
		this.height=height;
		this.fc=fc;
		this.ifc=ifc;
		
	}
	/* (non-Javadoc)
	 * @see aufgabe4.georg_2.FillBehavior#print()
	 */
	
	public void print() {
		System.out.println("eine gefüllte Box. Das Füllzeichen ist\""+ifc+"\".");
		for(int i =0; i < height; i++){
			for(int j = 0; j< width; j++){
				if (i == 0 || i == (height-1)){
					System.out.print(fc);
				}
				else
				{
					if (j == 0 || j == (width - 1)) {
						System.out.print(fc);
					} else {
						System.out.print(ifc);
					}
				}
			}
			System.out.print("\n");
		}

	}
	
	public void setFrameChar(char fc) {
		this.fc = fc;
		
	}
	
	public void setSize(int width, int height) {
		this.width=width;
		this.height = height;
		
	}

	public void setFillChar(char ifc) {
		// TODO Auto-generated method stub
		
	}

}
