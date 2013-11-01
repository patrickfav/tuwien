package dst2.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst1.model.hardware.Computer;
import dst1.model.hardware.Grid;
import dst2.ejb.interfaces.GeneralManagement;
@Stateless
public class GeneralManagementBean implements GeneralManagement{
	
	@PersistenceContext
	private EntityManager em;

	
	public int getFreeCPUs(Grid g) {
		int cpus =0;
		
		@SuppressWarnings("unchecked")
		List<Computer> compList = em.createQuery("select comp from Grid g join g.cluster as cl join cl.computer as comp where g.id = :id " +
		"AND comp.id not in (select cmp.id from Computer cmp join cmp.executions as exec where exec.start is not null AND exec.end is null)")
		.setParameter("id", g.getId()).getResultList();
		
		for(Computer c:compList)
			cpus+=c.getCpus();
		
		return cpus;
	}
	
	public boolean isUserNameFree(String usr) {
		if(0 < em.createQuery("select u from User u where u.username = :usr").setParameter("usr", usr).getResultList().size())
			return false;
			
		return true;
	}
	
	public void persist(Object o) {
		em.persist(o);
	}
	
	@SuppressWarnings("unchecked")
	public List<Grid> getAllGrids() {
		return em.createQuery("select g from Grid g").getResultList();
	}
}
