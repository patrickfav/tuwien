package aufgabe9.flo;

public class RectangleBox extends Box {

	protected Form innerform;
	protected Form outerform;
	protected float thickness;
	protected float height;
	protected float length;
	protected float width;
	protected Gift gift;
	
	// instances new RectangleBox
	public RectangleBox(float thickness, float height, float length, float width) {
		this.thickness = thickness;
		this.height = height;
		this.length = length;
		this.width = width;
		this.innerform = new Rectangle(length, width);
		this.outerform = new Rectangle(length+thickness*2, width+thickness*2);
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
			// create new RectangleBox
			Box newbox = new RectangleBox(0.5f, height+thickness*2, length+thickness*2, width+thickness*2);
			newbox.setGift(this);
			return newbox;
		}
	}

	// returns volume
	public float volume() {	
		return outerform.getArea()*(height+thickness*2);
	}

}
