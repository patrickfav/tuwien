package aufgabe9.flo;

public class SquareBox extends Box {

	protected Form innerform;
	protected Form outerform;
	protected float thickness;
	protected float height;
	protected Gift gift;
	
	// instances new SquareBox
	public SquareBox(float thickness, float length) {
		this.thickness = thickness;
		this.height = length;
		this.innerform = new Square(length);
		this.outerform = new Square(length+thickness*2);
	}
	
	// returns inner ground form
	public Form getForm() {
		return innerform;
	}
	
	// returns height
	public float getHeight() {
		return height;
	}
	
	// sets gift
	public void setGift(Gift gift) {
		this.gift = gift;
	}
	
	// returns gift
	public Gift getGift() {
		return gift;
	}
	
	// packs box into another box
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
			// create new SquareBox
			Box newbox = new SquareBox(0.5f, height+thickness*2);
			newbox.setGift(this);
			return newbox;
		}
	}
	
	// returns volume
	public float volume() {	
		return outerform.getArea()*(height+thickness*2);
	}

}
