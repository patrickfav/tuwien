package aufgabe9;

public interface Gift {
	
	//calculates the volume out of the form-area and height (in cm3)
	public float volume();
	//the height in cm
	public float getHeight();
	//returns the general kind of Gift (eg. Book, SquareBox,etc)
	public String getGiftType();
	//returns the specific name of a Gift - a box will return the name of the gift in it
	public String getName();
	//returns the base-form of the gift
	public Form getForm();
	//requires a fitting box (compatible form, bigger inner-volume), returns the box with the gift in it
	public Gift pack(Box b);
	//unpacks a box and returns the content, a unpacked gift will return itself
	public Gift unpack();
	//checks if the the instance fits in the given box
	public boolean checkIfFitsInBox(Box b);
	
	
}
