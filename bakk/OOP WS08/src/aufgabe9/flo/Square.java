package aufgabe9.flo;

public class Square implements Form{
	
	float length;
	
	// instances new Square
	public Square(float length) {
		this.length = length;
	}
	
	// returns area
	public float getArea() {
		float area = length*length;
		return area;
	}
	
	// returns length
	public float getLength() {
		return length;
	}
	
	// sets length
	public void setLength(float length) {
		this.length = length;
	}
	
	
}
