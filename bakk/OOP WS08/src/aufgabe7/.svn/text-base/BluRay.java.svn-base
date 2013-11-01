package aufgabe7;

public class BluRay extends AbstractRemovableMemory{
	
	//initializes a BluRay with 50GB
	public BluRay() {
		super(53687091200.0f);
	}
	
	public String getID() {
		return"BluRay 50GB";
	}
	
	//Multimethods for Handling several Memories with several Drives
	public void UsedByBRDrive(BRDrive drive) {
		drive.dataLoad(this);
	}
	public void UsedByDVDrive(DVDrive drive) {
		drive.detectWrongMedia();
	}
	public void UsedByUSBDrive(USBDrive drive) {
		drive.detectWrongMedia();
	}
	public void UsedByUSBPort(USBPort drive) {
		drive.detectMemoryError();
	}
}
