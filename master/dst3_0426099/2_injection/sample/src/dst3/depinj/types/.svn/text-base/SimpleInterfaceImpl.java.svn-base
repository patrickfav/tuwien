package dst3.depinj.types;

import java.util.Random;

import dst3.annotations.Component;
import dst3.annotations.ComponentId;
import dst3.annotations.Inject;
import dst3.annotations.ScopeType;

@Component(scope = ScopeType.SINGELTON)
public class SimpleInterfaceImpl implements SimpleInterface{
	

	@ComponentId
	private Long id;
	
	@Inject
	private Random r;
	
	@Override
	public void fooBar() {
		System.out.println("[SimpleInterfaceImpl] id: "+id+" fooBar called! Random class:"+r);
	}

}
