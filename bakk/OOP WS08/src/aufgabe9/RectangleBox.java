package aufgabe9;

public class RectangleBox extends Box{


	public RectangleBox(Gift g, float thickness,float height, float length,float width) {
		super(g,height,thickness);
		this.innerform = new Rectangle(length-thickness,width-thickness);
		this.outerform = new Rectangle(length, width);
	}
	
	public String getGiftType() {
		return "Box with Rectangle Base";
	}
}
