package workers;

import org.apache.log4j.Logger;

import products.factory.ProductsFactory;
import products.parts.Arm;
import products.parts.Body;
import products.parts.Head;
import products.parts.Leg;
import products.parts.abstracts.Cap;
import products.parts.abstracts.Product;
import workers.abstracts.AbstractWorker;
import dao.interfaces.IPartDao;
import exceptions.IDaoSaveException;

public class ProductionElve extends AbstractWorker {

	private static Logger log = Logger.getLogger(ProductionElve.class);
	
	private int productType;
	private int count;
	private double defectRate;
	private boolean running = false;
	private IPartDao dao;
	private String productName;

	public ProductionElve(String id, IPartDao dao, int productType, int count, double defectRate, String productName) {
		super(id);
		this.productType = productType;
		this.count = count;
		this.defectRate = defectRate;
		this.dao = dao;
		this.productName = productName;
		log.info("Elve created: "+getWorkerId().getId()+". "+count+" x Type["+productType+"]");
	}
	
	@Override
	public void run() {
		try {
			running = true;
			while(count > 0) {
				Product p = ProductsFactory.producePart(productType, getWorkerId(), defectRate);
				log.info("Elve "+getWorkerId().getId()+" #[" +getId() + "]" + ": produced " + p);
				sleep(p.getWorktime());
				
				// Save Product
				try {
					savePart(p);
					count--;
				} catch(IDaoSaveException e) {
					log.error("==============================================================");
					log.error("==============================================================");
					log.error("Elve "+getWorkerId().getId()+" #[" +getId() + "]" +": Could not save Dao Exception: "+e.getMessage());
					log.error("==============================================================");
					log.error("==============================================================");
				}
			}
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void savePart(Product p) throws IDaoSaveException {
		if(p instanceof Head) {
			dao.saveHead((Head) p); 
		} else if(p instanceof Arm) {
			dao.saveArm((Arm) p);
		} else if(p instanceof Leg) {
			dao.saveLeg((Leg) p);
		} else if(p instanceof Body) {
			dao.saveBody((Body) p);
		} else if(p instanceof Cap) {
			dao.saveCap((Cap) p);
		}
	}
	
	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public int getAnzahl() {
		return count;
	}

	public void setAnzahl(int anzahl) {
		this.count = anzahl;
	}

	public double getFehlerquote() {
		return defectRate;
	}

	public void setFehlerquote(double fehlerquote) {
		this.defectRate = fehlerquote;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
