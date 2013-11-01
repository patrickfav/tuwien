package dst3.dynload.sample;

import dst3.dynload.IPluginExecutable;

public class PluginExecutableTwo implements IPluginExecutable{

	@Override
	public void execute() {
		System.out.println("\n=== "+this.getClass()+" ===");
		System.out.println("Executable 2 was called.");
	}

}
