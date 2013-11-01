package aufgabe9.flo;

public class Ball extends abstractGift {

	public String name;
	public float diameter;
	public Form form;
	
	// instances new ball
	public Ball(String name, float diameter) {		
		this.name = name;
		this.diameter = diameter;
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
	
	// returns diameter = height
	public float getHeight() {
		return diameter;
	}
	
	// packs this object
	public Gift pack(Store store) {
		// search for box
		Box box = store.searchBox(form, diameter);
		// if there is a box
		if(box != null) {
			// put the gift into it
			box.setGift(this);
			return box;
		// if there is no box in store
		} else {
			// create new SquareBox because its easier to pull the ball out
			Box newbox = new SquareBox(0.5f, diameter);
			newbox.setGift(this);
			return newbox;
		}
	}
	
	// returns the volume
	public float volume() {
		return (4/3)*form.getArea()*(diameter/2);
	}

}
