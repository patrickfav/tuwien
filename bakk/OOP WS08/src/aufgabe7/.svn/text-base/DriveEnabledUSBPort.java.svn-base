package aufgabe7;

public class DriveEnabledUSBPort extends USBPort {
	
	protected USBDrive memory;
	
	//a usb device needs to load a memory up in front and cannot be changed
	public DriveEnabledUSBPort(USBDrive drive) {
		super();
		memory = drive;
	}
	
	//for the use with usb drive
	public void load(StandAloneMemory media) {
		if(this.isPlugged()) {
			media.UsedByUSBDrive(memory);
		}
		else {
			//debug
			System.out.println("Cannot load - not plugged in.");
		}
	}

	public void unload() {
		if(this.isPlugged()) {
			this.memory.unload();
		}
		else {
			//debug
			System.out.println("Cannot unload - not plugged in.");
		}
		
	}
	
	public float available() {
		if(this.isPlugged()) {
			return this.memory.available();
		}
		else {
			return -0.0f;
		}
	}
	
	public void use(float mem) {
		if(this.isPlugged()) {
			this.memory.use(mem);
		}
		else {
			//debug
			System.out.println("Cannot use space unplugged - not plugged in.");
		}
	}
}
