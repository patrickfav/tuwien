package aufgabe9.flo;

public class Circle implements Form {
	
	float r;
	
	// instances new Circle
	public Circle(float r) {
		this.r = r;
	}
	
	// returns area
	public float getArea() {
		Double pi = new Double(Math.PI);
		Double r2 = new Double(Math.pow(2, r));
		float area =  pi.floatValue()*r2.floatValue();
		return area;
	}
	
	// returns radius
	public float getR() {
		return r;
	}

	// sets radius
	public void setR(float r) {
		this.r = r;
	}
	
}
