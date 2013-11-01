package aufgabe9;

public class Circle implements Form{
	
	float r;
	
	public Circle(float r) {
		this.r = r;
	}
	
	public float getArea() {
		Double pi = new Double(Math.PI);
		float area =  pi.floatValue()*r*r;
		return area;
	}
	
	//if the form has a length it will return it otherwise 0
	public float getLength() {
		return 0f;
	}
	//if the form has a width it will return it otherwise 0
	public float getWidth() {
		return 0f;
	}
	//if the form has a Radius it will return it otherwise 0
	public float getRadius() {
		return r;
	}
	
	//needs a Form and will return if the form fits to this form
	public boolean fitsInForm(Form f) {
		
		if(f instanceof Circle) {
			return true;
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
	
	public void setR(float r) {
		this.r = r;
	}
	
	
	
}
