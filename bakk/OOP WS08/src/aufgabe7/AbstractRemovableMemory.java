package aufgabe7;

public abstract class AbstractRemovableMemory implements StandAloneMemory{
	
	protected float used_memory;
	protected float max_memory;
	
	//sets the memory value
	public AbstractRemovableMemory(float memory) {
		this.max_memory = memory;
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
	
	//return the kind of memory as String
	abstract String getID();
}
