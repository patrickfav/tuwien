package aufgabe9.flo;

public class Swatch extends abstractGift {

	public String name;
	public float height;
	public float length;
	public float width;
	public Form form;
	
	// instances new Swatch
	public Swatch(String name, float height, float length, float width) {
		this.name = name;
		this.height = height;
		this.length = length;
		this.width = width;
		this.form = new Rectangle(length, width);	
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
		return height;
	}
	
	// packs this object
	public Gift pack(Store store) {
		// search for box
		Box box = store.searchBox(form, height);
		// if there is a box
		if(box != null) {
			// put the gift into it
			box.setGift(this);
			return box;
		// if there is no box in store
		} else {
			// create new RectangleBox
			Box newbox = new RectangleBox(0.5f, height, length, width);
			newbox.setGift(this);
			return newbox;
		}	
	}
	
	// returns volume
	public float volume() {
		return form.getArea()*height;
	}

}
