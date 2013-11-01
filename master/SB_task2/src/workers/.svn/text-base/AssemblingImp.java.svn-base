package workers;

import org.apache.log4j.Logger;

import dao.RMIStorageDAOImpl;
import dao.SpaceStorageDAOImpl;
import dao.interfaces.IStorageDAO;
import exceptions.IDaoSaveException;

import products.PartsContainer;
import products.Teddybear;
import products.factory.ProductsFactory;
import workers.abstracts.AbstractWorker;

public class AssemblingImp extends AbstractWorker {
	
	private static Logger log = Logger.getLogger(AssemblingImp.class);
	private IStorageDAO dao;
	
	public AssemblingImp(String id, IStorageDAO dao) {
		super(id);
		this.dao = dao;
		log.info("Imp created: "+getWorkerId().getId()+".");
	}
	
	
	@Override
	public void run() {
		long sleepTime=0;
		
		while(isAlive()) {
			Teddybear t = ProductsFactory.createTeddybear();
			
			//dao.getPartDao().getAllParts();
			log.info("Imp "+getWorkerId().getId()+" #[" +getId() + "]" + ": retreiving parts... ");
			PartsContainer partsContainer = dao.getPartDao().getAllPartsAtomic();
			if(partsContainer != null) {
				t.assemble(this.getWorkerId(), partsContainer.getCap(), partsContainer.getHead(), partsContainer.getBody(), partsContainer.getLeftArm(), partsContainer.getRightArm(), partsContainer.getLeftLeg(), partsContainer.getRightLeg());
				log.info("Imp "+getWorkerId().getId()+" #[" +getId() + "]" + ": assembled " + t);
				try {
					dao.getTeddybearDao().saveToAssembled(t);
				} catch (IDaoSaveException e) {
					e.printStackTrace();
				}
			} else {
				log.info("Imp "+getWorkerId().getId()+" #[" +getId() + "]" + " Waiting...");
			}
			try {
				sleepTime=generateRandomSleepTime(IMP_WORK_TIME_MIN,IMP_WORK_TIME_MAX);
				sleep(sleepTime);
				log.info("Imp "+getWorkerId().getId()+" #[" +getId() + "]" + ": sleeping " + sleepTime +" ms.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String id = "";
		IStorageDAO storage = null;
		
		if(args.length == 2) {
			id = args[0];
			if(args[1].equals("rmi")) {
				storage = new RMIStorageDAOImpl();
			} else if(args[1].equals("space")) {
				storage = new SpaceStorageDAOImpl();
			}
			
			AssemblingImp ai = new AssemblingImp(id, storage);
			ai.start();
			
		} else {
			usage();
		}
	}
	
	private static void usage() {
		System.out.println("Usage: java AssemblingImp id (rmi|space)");
	}
}
