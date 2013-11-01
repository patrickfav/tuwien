package aufgabe7;

public class CD extends AbstractRemovableMemory{
	
	//initializes a CD with 700 MB
	public CD() {
		super(734003200.0f);
	}
	
	public String getID() {
		return"CD-R 700MB";
	}
	
	//Multimethods for Handling several Memories with several Drives
	public void UsedByBRDrive(BRDrive drive) {
		drive.detectWrongMedia();
	}
	public void UsedByDVDrive(DVDrive drive) {
		drive.dataLoad(this);
	}
	public void UsedByUSBDrive(USBDrive drive) {
		drive.dataLoad(this);
	}
	public void UsedByUSBPort(USBPort drive) {
		drive.detectMemoryError();
	}
}
