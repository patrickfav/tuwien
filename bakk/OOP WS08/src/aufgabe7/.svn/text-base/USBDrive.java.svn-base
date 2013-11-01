package aufgabe7;

public class USBDrive implements Drive{
	
	private StandAloneMemory media;
	private boolean isloaded;
	
	public USBDrive() {
		isloaded = false;
	}
	
	//outer load method - accesses the multimethods - accepts various memories
	public void load(StandAloneMemory media) {
			if(!this.checkLoaded())
				media.UsedByUSBDrive(this);
			else 
				System.out.println("Media already loaded - unload first.");
	}
	
	//needs a appropriate media (memory)
	public boolean dataLoad(StandAloneMemory media) {
		if(!this.checkLoaded()) {
			isloaded = true;
			this.media = media;
			
			//debug
			System.out.println(media+" successfully loaded.");
			
			return true;
		}
		return false;
	}
	
	//makes the media unavailable
	public void unload() {
		this.media=null;
		this.isloaded = false;
		
		//debug
		System.out.println("Media successfully unloaded.");
	}
	
	//see Memory Interface - will return 0 when media is not accessible
	public float available() {
		if(this.checkLoaded()) {
			return media.available();
		}
		else {
			//debug
			System.out.println("Media not accessible.");
			return 0;
		}
	}

	//will only work if the media is made accessible by load
	public void use(float memory) {
		if(this.checkLoaded()) {
			media.use(memory);
			//debug
			System.out.println(memory+ "Bytes used.");
		}
		else
			//debug
			System.out.println("Media not accessible.");
	}
	
	//standard error message output
	public void detectWrongMedia() {
		System.out.println("USBDrive cannot use this media.");
		this.unload();
	}

	
	//returns if a media is loaded
	public boolean checkLoaded() {
		return isloaded;
	}
	
	//return the kind of memory as String
	public String getID() {
		return "USBDrive";
	}

	
	/*
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
	public void UsedByUSBDevice(USBPort drive) {
		drive.take(this);
	}*/
}
