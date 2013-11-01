package aufgabe7;

public class BRDrive extends AbstractDrive{
	
	public BRDrive(StandAloneMemory media) {
		media.UsedByBRDrive(this);
	}
	
	public BRDrive() {
		super();
	}
	
	//outer load method - accesses the multimethods - accepts various memories
	public void load(StandAloneMemory media) {
		if(!this.checkLoaded())
			media.UsedByBRDrive(this);
		else 
			System.out.println("Media already loaded - unload first.");
	}
	
	//standard error message output
	public void detectWrongMedia() {
		System.out.println("Error: Media cannot be read in BluRay-Drive.");
		this.unload();
	}
	
}
