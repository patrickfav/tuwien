package aufgabe7;

public interface Drive extends MemoryInterfaces{
	
	//the "outer" load method - this one should be used when using various media
	public void load(StandAloneMemory media);
	//returns true if the memory can be loaded
	public boolean dataLoad(StandAloneMemory media);
	//Completely unloads the media and makes it unavailable
	public void unload();
}
