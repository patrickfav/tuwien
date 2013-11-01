package aufgabe9;

public class Square implements Form{
	
	float length;
	
	public Square(float length) {
		this.length = length;
	}
	
	public float getArea() {
		float area = length*length;
		return area;
	}
	
	//needs a Form and will return if the form fits with this form
	public boolean fitsInForm(Form f) {
		
		if(f instanceof Circle) {
			return false;
		}
		else if(f instanceof Square) {
			return true;
		}
		else if(f instanceof Rectangle) {
			return false;
		}
		
		return false;
	}
	
	/*
	 * GETTER AND SETTER
	 */

	public void setLength(float length) {
		this.length = length;
	}
	
	//if the form has a length it will return it otherwise 0
	public float getLength() {
		return length;
	}
	//if the form has a width it will return it otherwise 0
	public float getWidth() {
		return 0f;
	}
	//if the form has a Radius it will return it otherwise 0
	public float getRadius() {
		return 0f;
	}
	
	
}
