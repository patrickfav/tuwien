package aufgabe7;

public abstract class AbstractDrive implements Drive{
	
	protected boolean isloaded;
	protected StandAloneMemory media;
	
	//initial load of a drive no media and not loaded
	public AbstractDrive() {
		isloaded=false;
		media = null;
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
			System.out.println("Media not accessable.");
	}
	
	//returns if a media is loaded
	public boolean checkLoaded() {
		return isloaded;
	}

	
	//kind of exception handling- outputting when accessed with wrong media
	abstract public void detectWrongMedia();
}
