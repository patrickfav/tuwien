/**
 * 
 */
package aufgabe4.georg_2;

/**
 * @author GEMEH
 *
 */
public class SimpleFilling implements FillBehavior {
	int width;
	int height;
	char fc;
	
	public SimpleFilling(int width, int height, char fc){
		this.width=width;
		this.height=height;
		this.fc=fc;
		
	}
	/* (non-Javadoc)
	 * @see aufgabe4.georg_2.FillBehavior#print()
	 */
	
	public void print() {
		System.out.println("eine komplett schwarze Box.");
		for(int i =0; i < height; i++){
			for(int j = 0; j< width; j++){
				System.out.print(fc);
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
