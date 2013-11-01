package aufgabe7;

public class HardDisk implements StandAloneMemory{
	
	protected float used_memory;
	protected float max_memory;
	
	//initializes a Harddisk with variable size
	public HardDisk(float memory) {
		max_memory=memory;
	}
	
	//returns the available memory size (in Bytes)
	public float available() {
		return max_memory-used_memory;
	}

	//will use some of the free memory size. Should not exceed max available.
	public void use(float memory) {
		used_memory += memory;
		
		if(used_memory > max_memory)
			used_memory = max_memory;
	}
	
	//frees memory - cannot free more than used before
	public void free(float memory) {
		used_memory -= memory;
		
		if(used_memory <= 0)
			used_memory = 0;
	}
	
	//Multimethods for Handling several Memories with several Drives
	public void UsedByBRDrive(BRDrive drive) {
		drive.detectWrongMedia();
	}
	public void UsedByDVDrive(DVDrive drive) {
		drive.detectWrongMedia();
	}
	public void UsedByUSBDrive(USBDrive drive) {
		drive.detectWrongMedia();
	}
	public void UsedByUSBPort(USBPort drive) {
		drive.take(this);
	}
}
