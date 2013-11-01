package aufgabe9;

public abstract class AbstractGift implements Gift {
	
	public String name;
	public float height;
	public Form form;
	
	public AbstractGift(String name, float height) {
		this.height = height;
		this.name = name;
	}
	
	//requires a box and will return if the box fits to this gift
	public boolean checkIfFitsInBox(Box b) {
		if(!b.getForm().fitsInForm(form)) {
			System.out.println("Form does not fit.");
			return false;
		}
		if(!(b.getInnerForm().getArea() > form.getArea())) {
			System.out.println("Area is too small.");
			return false;
		}
		if(!(this.getHeight() < b.getHeight())) {
			System.out.println("Height is too small.");
			return false;
		}

		return true;
	}
	
	//the given box must fit with the gifts form - will return the packed gift or the unpacked gift if it doesnt fit
	public Gift pack(Box b) {
		if(this.checkIfFitsInBox(b)) {
			b.setGift(this);
			return b;
		}
		return this;
	}
	
	//creates new boxes that fits the gift (is not too big)
	protected Box makeNewRectangleBox() {
		Box newbox = new RectangleBox(null, 0.5f,height+2,form.getLength()+2,form.getWidth()+2);
		return newbox;
	}
	//creates new boxes that fits the gift (is not too big)
	protected Box makeNewSquareBox() {
		Box newbox = new SquareBox(null, 0.5f,height+2,form.getLength()+2);
		return newbox;
	}
	//creates new boxes that fits the gift (is not too big)
	protected Box makeNewCircleBox() {
		Box newbox = new CircleBox(null, 0.5f,height+2,form.getRadius()+2);
		return newbox;
	}
	
	//will return itself because cannot be unpacked again
	public Gift unpack(){
		return this;
	}
	
	/*
	 * GETTER AND SETTER
	 */
	public String getName() {
		return name;
	}

	public Form getForm() {
		return form;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float volume() {
		return form.getArea()*height;
	}
}
