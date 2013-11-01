package aufgabe9.flo;

public class Rectangle implements Form{
	
	float length;
	float width;
	
	// instances new Rectangle
	public Rectangle(float length, float width) {
		this.length = length;
		this.width = width;
	}
	
	// returns area
	public float getArea() {
		float area = length*width;
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

	// returns width
	public float getWidth() {
		return width;
	}

	// sets width
	public void setWidth(float width) {
		this.width = width;
	}
	
	
}
