package aufgabe9.flo;

public class RubiksCube extends abstractGift {

	public String name;
	public float length;
	public Form form;

	// instances new RubiksCube
	public RubiksCube(String name, float length) {
		this.name = name;
		this.length = length;
		this.form = new Square(length);
	}

	// returns name
	public String getName() {
		return name;
	}
	
	// returns ground form
	public Form getForm() {
		return form;
	}
	
	// returns height
	public float getHeight() {
		return length;
	}
	
	// packs this object
	public Gift pack(Store store) {
		// search for box
		Box box = store.searchBox(form, length);
		// if there is a box
		if(box != null) {
			// put the gift into it
			box.setGift(this);
			return box;
		// if there is no box in store
		} else {
			// create new SquareBox
			Box newbox = new SquareBox(0.5f, length);
			newbox.setGift(this);
			return newbox;
		}
	}
	
	// returns volume
	public float volume() {
		return form.getArea()*length;
	}
}
