package dst2.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst2.comparator.PriceStepComparator;
import dst2.ejb.interfaces.PriceManagement;
import dst2.model.PriceStep;

@Startup
@Singleton
public class PriceManagamentBean implements PriceManagement{
	
	private List<PriceStep> priceList;
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void fetchStartupData() {
		priceList = Collections.synchronizedList(new ArrayList<PriceStep>());
		priceList.addAll(em.createQuery("select p from PriceStep p").getResultList());
		Collections.sort(priceList,new PriceStepComparator());
	}
	
	@Override
	@Lock(LockType.WRITE)
	public PriceStep storePriceStep(int numberOfHistoricalJobs, BigDecimal price) {
		PriceStep pS = new PriceStep();
		pS.setNumberOfHistoricalJobs(numberOfHistoricalJobs);
		pS.setPrice(price);
		
		em.persist(pS);
		priceList.add(pS);
		
		return pS;
	}

	@Override
	@Lock(LockType.READ)
	public BigDecimal getPriceForJobs(int numJobs) {
		
		
		if(priceList.size() == 0)
			return null;
		
		if(priceList.size() == 1)
			return priceList.get(0).getPrice();
		
		for(int i=0; i< priceList.size();i++) {
			if(numJobs < priceList.get(i).getNumberOfHistoricalJobs() )
				return priceList.get(i).getPrice();
		}
		/* index;
		List<PriceStep> searchList = priceList;
		
		do {
			index = (int) Math.floor(searchList.size()/2);
			
			if(numlJobs < searchList.get(index).getNumberOfHistoricalJobs()) {
				searchList = searchList.subList(0, index-1);
			} else {
				searchList = searchList.subList(index+1, searchList.size()-1);
			}
		} while(numlJobs <  );*/
		
		return priceList.get(priceList.size()-1).getPrice();
	}

}
