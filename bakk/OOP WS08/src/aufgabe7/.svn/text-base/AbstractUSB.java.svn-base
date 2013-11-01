package aufgabe7;

public class AbstractUSB implements USB{
	
	protected boolean pluggedin;
	protected StandAloneMemory memory;
	
	//plugIn makes a the usb device available
	public void plugIn() {
		this.pluggedin = true;
		//debug
		System.out.println(this+" Device plugged in.");
	}
	
	//plugin makes the usb device unavailable
	public void plugOut() {
		pluggedin=false;
		//debug
		System.out.println(this+" Device plugged out.");
	}
	
	//can only be accessed if plugged in
	public float available() {
		if(this.isPlugged()) {
			return memory.available();
		}
		else {
			//debug
			System.out.println("Memory not accessible.");
			return -0.0f;
		}
	}
	
	//can only be accessed if plugged in
	public void use(float memory) {
		if(this.isPlugged()) {
			this.memory.use(memory);
			//debug
			System.out.println(memory+ "Bytes used.");
		}
		else
			//debug
			System.out.println("Memory not accessible.");
	}
	
	//checks if the device is plugged
	public boolean isPlugged() {
		return pluggedin;
	}
	
	//standard error message output
	public void detectMemoryError() {
		System.out.println("Error: USB cannot cope with this kind of Memory.");
	}
}
