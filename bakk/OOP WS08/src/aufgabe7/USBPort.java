package aufgabe7;

public class USBPort extends AbstractUSB {
	
	
	//a usb device needs to load a memory up in front and cannot be changed
	public USBPort(StandAloneMemory mem) {
		pluggedin = false;
		mem.UsedByUSBPort(this);
	}
	
	public USBPort() {
		pluggedin = false;
	}
	
	public void take(StandAloneMemory mem) {
		memory = mem;
	}
}
