package util;


import java.util.LinkedList;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Stateful
@Scope(ScopeType.SESSION)
@Name("BeanResetter")
public class BeanResetterBean implements BeanResetter {

	private static final long serialVersionUID = 1L;

	@Logger private Log log;
	
	private LinkedList<Resettable> beans;
	
	public BeanResetterBean() {
		beans = new LinkedList<Resettable>();
	}
	
	public void addGridBean(Resettable resettable) {
		if(!beans.contains(resettable)) {
			beans.add(resettable);	
			log.info("addGridBean called, new size: #0 bean: #1", beans.size(), resettable.getClass().getSimpleName());
		}
	}

	public void resetAll() {
		log.info("Resetting all #0 Beans", beans.size());
		boolean dirty = false;
		for(Resettable bean: beans) {
			if(bean != null) {
				log.info("Resetting Bean #0", bean.getClass());
				bean.reset();
				dirty = true;
			}
		}
		
		if(dirty) {
			beans.remove(null);
		}
	}

	@Remove
	@Destroy
	public void destroy() { 
	}

}
