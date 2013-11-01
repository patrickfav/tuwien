package dst2.ejb;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import dst1.helper.MathHelper;
import dst1.model.Membership;
import dst1.model.hardware.Grid;
import dst1.model.tasks.Job;
import dst2.ejb.interfaces.GeneralManagement;
import dst2.ejb.interfaces.PriceManagement;
import dst2.model.AuditRecord;
import dst2.model.PriceStep;

@Stateless
public class GeneralManagementBean implements GeneralManagement{
	
	@EJB
	private PriceManagement priceManagementBean;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public PriceStep storePriceStep(int numberOfHistoricalJobs, BigDecimal price) {
		return priceManagementBean.storePriceStep(numberOfHistoricalJobs, price);
	}
	
	@SuppressWarnings("unchecked")
	@Asynchronous
	public Future<String> createBill(String usrName) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nInvoice for "+usrName);
		sb.append("\n--------------------------------");
		
		List<Job> jobList = em.createQuery("select distinct job from User u join u.jobs as job where u.username = :usr " +
				"AND job.isPaid = false AND job.execution.start is not null AND job.execution.end is not null").setParameter("usr", usrName).getResultList();
		
		Long allJobs = (Long) em.createQuery("select count(distinct job) from User u join u.jobs as job where u.username = :usr AND job.isPaid = true")
						.setParameter("usr", usrName).getSingleResult();
		
		Double amount = 0.0;
		Double currentCost;
		Double currentCostWithDiscount;
		Double scheduleCost = new Double(jobList.size())*priceManagementBean.getPriceForJobs(allJobs.intValue()).doubleValue();
		
		for(Job j: jobList) {
			sb.append("\n\n Job["+j.getId()+"]:");
			sb.append("\n\tExecution Time: "+j.getExecutionTime()+" ms");
			sb.append("\n\tNumCPUs: "+j.getNumCPUs());
			//sb.append("\n\tEnvironment: "+j.getEnvironment());
			
			Grid grid = (Grid) em.createQuery("select distinct g from Grid g join g.cluster as cl join cl.computer as comp join comp.executions as exec " +
					"where exec.job = :job").setParameter("job", j).getSingleResult();
			Membership mem = (Membership) em.createQuery("select distinct m from Grid g join g.memberships as m where m.user.username =:user AND g.id = :gridId")
				.setParameter("user", usrName).setParameter("gridId", grid.getId()).getSingleResult();
			
			sb.append("\n\tCostsPerCPUMinute: "+MathHelper.round(grid.getCostsPerCPUMinute(),4));
			
			
			currentCost = new Double(j.getExecutionTime()/1000)*(grid.getCostsPerCPUMinute().doubleValue());
			currentCostWithDiscount = currentCost-(currentCost*mem.getDiscount());
			amount += currentCostWithDiscount;
			sb.append("\n\n\tExecution Cost: "+MathHelper.round(currentCost,2)+" - ("+MathHelper.round(mem.getDiscount()*100,2)+"%) = "+MathHelper.round(currentCostWithDiscount,2));
			
			j.setPaid(true);
		}
		
		sb.append("\n\n--------------------------------");
		sb.append("\n\tScheduling Cost: "+MathHelper.round(scheduleCost,2));
		sb.append("\n\tSum Execution Cost: "+MathHelper.round(amount,2));
		sb.append("\n--------------------------------");
		
		
		return new AsyncResult<String>(sb.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public StringBuffer getAllAuditRecords() {
		StringBuffer sb = new StringBuffer();
		for(AuditRecord ar : (List<AuditRecord>) em.createQuery("select a from AuditRecord a").getResultList()) {
			sb.append(ar+"\n");
		}
		
		return sb;
	}
}
