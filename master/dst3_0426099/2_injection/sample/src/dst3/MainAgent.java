package dst3;

import java.util.ArrayList;

import dst3.depinj.sample.ControllerWithInjectionsPrototype;
import dst3.depinj.sample.ControllerWithInjectionsSingelton;

public class MainAgent {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("\nProtoype Components: ");
		ControllerWithInjectionsPrototype.taskB();
		ControllerWithInjectionsPrototype.taskB();
		ControllerWithInjectionsPrototype.taskB();
		
		System.out.println("\nSingelton Components: ");
		ControllerWithInjectionsSingelton.taskB();
		ControllerWithInjectionsSingelton.taskB();
		ControllerWithInjectionsSingelton.taskB();
		
	}

}
