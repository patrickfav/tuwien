package aufgabe7;

public class DVDrive extends AbstractDrive{
	
	//dvd drive loaded with media - accepts various memories
	public DVDrive(StandAloneMemory media) {
		media.UsedByDVDrive(this);
	}
	
	//empty dvd drive
	public DVDrive() {
		super();
	}
	
	//outer load method - accesses the multimethods - accepts various memories
	public void load(StandAloneMemory media) {
		if(!this.checkLoaded())
			media.UsedByDVDrive(this);
		else 
			System.out.println("Media already loaded - unload first.");
	}
	
	//standard error message output
	public void detectWrongMedia() {
		System.out.println("Error: Media cannot be read in DVD-Drive.");
		this.unload();
	}
	
	
}
