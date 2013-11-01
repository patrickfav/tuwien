package aufgabe9;

public class SquareBox extends Box{

	public SquareBox(Gift g, float thickness,float height, float length) {
		super(g,height,thickness);
		this.innerform = new Square(length-thickness);
		this.outerform = new Square(length);
	}
	
	public String getGiftType() {
		return "Box with Square Base";
	}
}
