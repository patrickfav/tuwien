package dst3.depinj.sample;


import java.util.ArrayList;
import java.util.List;

import dst3.annotations.Component;
import dst3.annotations.ComponentId;
import dst3.annotations.Inject;
import dst3.annotations.ScopeType;
import dst3.depinj.IInjectionController;
import dst3.depinj.InjectionController;
import dst3.depinj.types.ClassWithOutSimpleConstructor;
import dst3.depinj.types.SimpleInterface;
import dst3.depinj.types.SimpleInterfaceImpl;

@Component(scope = ScopeType.SINGELTON)
public class ControllerWithInjectionsSingelton {
	
	@ComponentId
	private Long id;
	
	@Inject
	private String string;
	
	@Inject(required=false)
	private ClassWithOutSimpleConstructor cwosc;
	
	@Inject(specificType = SimpleInterfaceImpl.class)
	private SimpleInterface si;
	
	@Inject(specificType = ArrayList.class)
	private List<String> list;
	
	public void callSi() {
		si.fooBar();
		System.out.println("[List] size is "+list.size()+" (should be 0 and not null) of type "+list.getClass()+". Id: "+list);
		System.out.println("[String] is "+string+" (should be empty and not null). Id: "+string+"\n");
	}
	
	public static void taskA() {
		IInjectionController ic = InjectionController.getInstance();
		ControllerWithInjectionsSingelton cwi = new ControllerWithInjectionsSingelton();
		ic.initialize(cwi);
		cwi.callSi(); // output expected
	}
	
	public static void taskB() {
		ControllerWithInjectionsSingelton cwi = new ControllerWithInjectionsSingelton();
		cwi.callSi(); // output expected
	}
}
