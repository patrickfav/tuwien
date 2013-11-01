package aufgabe9;

public interface Form {
	//every form has an area
	public float getArea();
	//returns if the form is compatibel with the given one
	public boolean fitsInForm(Form f);
	//if there is a length it will be returned otherwise 0
	public float getLength();
	//if there is a width it will be returned otherwise 0
	public float getWidth();
	//if there is a radius it will be returned otherwise 0
	public float getRadius();
}
