package aufgabe9.flo;

public class Vase extends abstractGift {

	public String name;
	public float diameter;
	public float height;
	public Form form;

	// instances new Vase
	public Vase(String name, float diameter, float height) {		
		this.name = name;
		this.diameter = diameter;
		this.height = height;
		this.form = new Circle(diameter/2);	
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
			// create new CircleBox
			Box newbox = new CircleBox(0.5f, height, diameter);
			newbox.setGift(this);
			return newbox;
		}
	}
	
	// returns volume
	public float volume() {
		return (4/3)*form.getArea()*(diameter/2);
	}

}
