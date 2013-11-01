package products.parts;

import products.parts.abstracts.Product;
import workers.id.WorkerID;


public class Leg extends Product {
	private static final long serialVersionUID = 4566633113567321383L;
	
	public Leg() {
		super();
	}
	
	public Leg(long worktime, WorkerID creator, boolean defect) {
		super(worktime, creator, defect);
	}
	
	@Override
	public String getProductName() {
		return "Leg";
	}
}
