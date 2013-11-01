package aufgabe9;

public abstract class Box implements Gift{
	
	protected Gift g;
	protected float thickness;
	protected float height;
	protected Form outerform;
	protected Form innerform;
	
	public Box (Gift g, float height, float thickness) {
		this.thickness = thickness;
		this.height = height;
		this.g = g;
	}
	/*
	 * GETTER AND SETTER
	 */
	
	public Form getForm() {
		return outerform;
	}
	
	public Form getInnerForm() {
		return innerform;
	}
	
	public float getHeight() {
		return height;
	}
	
	public String getName() {
		return g.getName();
	}
	
	public void setGift(Gift g) {
		this.g = g;
	}
	
	public float volume() {
		return (outerform.getArea()*height);
	}
	
	//returns the gift that is in the instance
	public Gift unpack() {
		return g;
	}
	
	//requires a box and will return if the box fits to this gift
	public boolean checkIfFitsInBox(Box b) {
		if(!b.getForm().fitsInForm(outerform)) {
			System.out.println("Form does not fit.");
			return false;
		}
		if(!(b.getInnerForm().getArea() > innerform.getArea())) {
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
	
}
