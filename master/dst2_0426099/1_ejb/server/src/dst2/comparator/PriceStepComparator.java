package dst2.comparator;

import java.util.Comparator;

import dst2.model.PriceStep;

public class PriceStepComparator implements Comparator<PriceStep>{

	@Override
	public int compare(PriceStep o1, PriceStep o2) {
		if(o1.getNumberOfHistoricalJobs() < o2.getNumberOfHistoricalJobs()) {
			return -1;
		} else if(o1.getNumberOfHistoricalJobs() > o2.getNumberOfHistoricalJobs()) {
			return 1;
		}
		return 0;
	}


}
