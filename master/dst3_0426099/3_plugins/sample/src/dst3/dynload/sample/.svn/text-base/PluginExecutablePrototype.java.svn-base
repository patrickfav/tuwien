package dst3.dynload.sample;

import java.util.ArrayList;
import java.util.List;

import dst3.annotations.Component;
import dst3.annotations.ComponentId;
import dst3.annotations.Inject;
import dst3.annotations.ScopeType;
import dst3.dynload.IPluginExecutable;
import dst3.dynload.sample.types.ClassWithOutSimpleConstructor;
import dst3.dynload.sample.types.SimpleInterface;
import dst3.dynload.sample.types.SimpleInterfaceImpl;

@Component(scope = ScopeType.PROTOTYPE)
public class PluginExecutablePrototype implements IPluginExecutable{
	
	@ComponentId
	private Long id;
	
	@Inject
	private String string;
	
	@SuppressWarnings("unused")
	@Inject(required=false)
	private ClassWithOutSimpleConstructor cwosc;
	
	@Inject(specificType = SimpleInterfaceImpl.class)
	private SimpleInterface si;
	
	@Inject(specificType = ArrayList.class)
	private List<String> list;
	
	public void callSi() {
		System.out.println("\n=== "+this.getClass()+" === (id:"+id+")");
		si.fooBar();
		System.out.println("[List] size is "+list.size()+" (should be 0 and not null) of type "+list.getClass());
		System.out.println("[String] is "+string+" (should be empty and not null).\n");
	}

	
	@Override
	public void execute() {
		callSi(); // output expected
	}

}
