package aufgabe9;

public class Rectangle implements Form{
	
	float length;
	float width;
	
	public Rectangle(float length, float width) {
		this.length = length;
		this.width = width;
	}
	
	public float getArea() {
		float area = length*width;
		return area;
	}

	
	//needs a Form and will return if the form fits with this form
	public boolean fitsInForm(Form f) {
		
		if(f instanceof Circle) {
			return false;
		}
		else if(f instanceof Square) {
			return false;
		}
		else if(f instanceof Rectangle) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * GETTER AND SETTER
	 */

	public void setLength(float length) {
		this.length = length;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	//if the form has a length it will return it otherwise 0
	public float getLength() {
		return length;
	}
	//if the form has a width it will return it otherwise 0
	public float getWidth() {
		return width;
	}
	//if the form has a Radius it will return it otherwise 0
	public float getRadius() {
		return 0f;
	}
}
