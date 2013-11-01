package factory.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import workers.ProductionElve;

public class ElveStorage {

	private static ElveStorage instance = null;
	private List<ProductionElve> elves;
	
	private ElveStorage() {
		this.elves = Collections.synchronizedList(new ArrayList<ProductionElve>());
	}
	
	public static ElveStorage getInstance() {
        if (instance == null) {
            instance = new ElveStorage();
        }
        return instance;
    }
	
	public void addProductionElve(ProductionElve elve) {
		elves.add(elve);
	}
	
	public void removeProductionElve(ProductionElve elve) {
		elves.remove(elve);
	}

	public List<ProductionElve> getElves() {
		return elves;
	}

	public void setElves(List<ProductionElve> elves) {
		this.elves = elves;
	}
	
}
