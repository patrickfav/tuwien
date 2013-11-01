package aufgabe7;

public interface USB extends MemoryInterfaces{
	
	//a usb needs to be plugged in before it can use "available" and "use"
	public void plugIn();
	//makes a memory unavailable
	public void plugOut();
	//returns if the usb is plugged
	public boolean isPlugged();
	//Standard error message output
	public void detectMemoryError();
}
