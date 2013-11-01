package dst2.ejb.interfaces;

import java.math.BigDecimal;

import javax.ejb.Local;

import dst2.model.PriceStep;

@Local
public interface PriceManagement {
	
	public PriceStep storePriceStep(int numberOfHistoricalJobs, BigDecimal price);
	public BigDecimal getPriceForJobs(int numberOfHistoricalJobs);
}
