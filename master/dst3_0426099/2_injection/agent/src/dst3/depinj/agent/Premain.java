package dst3.depinj.agent;

import java.lang.instrument.Instrumentation;

public class Premain {
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("Premain called!");
		inst.addTransformer(new InjectionAgent());
	}
}
