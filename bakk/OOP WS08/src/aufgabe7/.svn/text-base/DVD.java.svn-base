package aufgabe7;

public class DVD extends AbstractRemovableMemory{
	
	//initializes a DVD with 4.7 GB
	public DVD() {
		super(5046586572.8f);
	}
	
	public String getID() {
		return"DVD-R 4.7GB";
	}
	
	//Multimethods for Handling several Memories with several Drives
	public void UsedByBRDrive(BRDrive drive) {
		drive.dataLoad(this);
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
