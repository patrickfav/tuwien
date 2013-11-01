package aufgabe9.flo;

public class CircleBox extends Box{

	protected Form innerform;
	protected Form outerform;
	protected float thickness;
	protected float height;
	protected float diameter;
	protected Gift gift;
	
	// instances new CircleBox
	public CircleBox(float thickness, float height, float diameter) {
		this.thickness = thickness;
		this.height = height;
		this.diameter = diameter;
		this.innerform = new Circle(diameter/2);
		this.outerform = new Circle((diameter+thickness*2)/2);
	}
	
	// returns inner ground form
	public Form getForm() {
		return innerform;
	}
	
	// returns height
	public float getHeight() {
		return height;
	}
	
	// sets the gift in the box
	public void setGift(Gift gift) {
		this.gift = gift;
	}
	
	// returns the gift
	public Gift getGift() {
		return gift;
	}
	
	// packs this box into another box
	public Gift pack(Store store) {
		// search for box
		Box box = store.searchBox(outerform, height+thickness*2);
		// if there is a box
		if(box != null) {
			// put the box into it
			box.setGift(this);
			return box;
		// if there is no box in store
		} else {
			// create new CircleBox
			Box newbox = new CircleBox(0.5f, height+thickness*2, diameter+thickness*2);
			newbox.setGift(this);
			return newbox;
		}
	}
	
	// returns volume
	public float volume() {
		return (4/3)*outerform.getArea()*((diameter+thickness*2)/2);
	}

}
