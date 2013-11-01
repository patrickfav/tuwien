package products.parts;

import products.parts.abstracts.Product;
import workers.id.WorkerID;


public class Head extends Product {
	private static final long serialVersionUID = -6063372382215024815L;
	
	public Head() {
		super();
	}
	
	public Head(long worktime, WorkerID creator, boolean defect) {
		super(worktime, creator, defect);
	}
	
	@Override
	public String getProductName() {
		return "Head";
	}
}
