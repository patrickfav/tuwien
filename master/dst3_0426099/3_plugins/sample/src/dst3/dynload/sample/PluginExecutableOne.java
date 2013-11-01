package dst3.dynload.sample;

import dst3.dynload.IPluginExecutable;

public class PluginExecutableOne implements IPluginExecutable{

	@Override
	public void execute() {
		System.out.println("\n=== "+this.getClass()+" ===");
		System.out.println("Executable 1 was called.\n");
	}

}
