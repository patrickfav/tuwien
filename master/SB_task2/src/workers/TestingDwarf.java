package workers;

import org.apache.log4j.Logger;

import dao.RMIStorageDAOImpl;
import dao.SpaceStorageDAOImpl;
import dao.interfaces.IStorageDAO;
import exceptions.IDaoReadException;
import exceptions.IDaoSaveException;

import products.Teddybear;
import workers.abstracts.AbstractWorker;

public class TestingDwarf extends AbstractWorker {

	private static Logger log = Logger.getLogger(TestingDwarf.class);

	public static final int DEFECT_TESTING_DWARF = 0;
	public static final int WEIGHT_TESTING_DWARF = 1;

	private int dwarf_type;
	private IStorageDAO dao;

	public TestingDwarf(String id, int dwarf_type, IStorageDAO storage) {
		super(id);
		this.dao = storage;
		this.dwarf_type = dwarf_type;

		log.info("Dwarf created: " + getWorkerId().getId() + ". Type: "
				+ dwarf_type);
	}

	@Override
	public void run() {
		long sleepTime=0;
		
		while (isAlive()) {

			Teddybear t = null;
			
			try {
				log.info("Dwarf " + getWorkerId().getId() + " #[" + getId() + "]: retreiving teddy...");
				
				if(dwarf_type == DEFECT_TESTING_DWARF) {
					t = dao.getTeddybearDao().takeAssembledForDefectCheck();
				} else if(dwarf_type == WEIGHT_TESTING_DWARF) {
					t = dao.getTeddybearDao().takeAssembledForWeightCheck();
				}
				
			} catch (IDaoReadException e1) {
				e1.printStackTrace();
			}

			if (t != null) {

				if (t.isDefectCheckDone() && t.isWeightCheckDone()) {
					try {
						dao.getTeddybearDao().saveToChecked(t);
					} catch (IDaoSaveException e) {
						e.printStackTrace();
					}
				} else {

					Boolean result = false;
					switch (dwarf_type) {
					case DEFECT_TESTING_DWARF:
						result = t.checkForDefect(this.getWorkerId());
						log.info("Dwarf " + getWorkerId().getId() + " #["
								+ getId() + "]: Teddy "+ t.getId() +" testing defect:" + result);
					break;
					case WEIGHT_TESTING_DWARF:
						result = t.checkForWeight(this.getWorkerId());
						log.info("Dwarf " + getWorkerId().getId() + " #["
								+ getId() + "]: Teddy "+ t.getId() +" testing weight:" + result);
					break;
					default:
						log.info("Dwarf " + getWorkerId().getId() + " #["
								+ getId() + "]: Error - wrong type.");
					break;
					}

					try {
						if (t.isDefectCheckDone() && t.isWeightCheckDone()) {
							dao.getTeddybearDao().saveToChecked(t);
						} else {
							dao.getTeddybearDao().saveToAssembled(t);
						}
					} catch (IDaoSaveException e) {
						e.printStackTrace();
					}
					
					try {
						sleepTime = generateRandomSleepTime(DWARF_WORK_TIME_MIN,DWARF_WORK_TIME_MAX);
						sleep(sleepTime);
						log.info("Dwarf " + getWorkerId().getId() + " #["
								+ getId() + "]: sleeping "+sleepTime+" ms.");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String id = "";
		int type_int = 0;
		int type = DEFECT_TESTING_DWARF;
		IStorageDAO storage = null;

		if (args.length == 3) {
			id = args[0];
			type_int = Integer.parseInt(args[1]);
			if(type_int == 0) {
				type = DEFECT_TESTING_DWARF;
			} else {
				type = WEIGHT_TESTING_DWARF;
			}
			if (args[2].equals("rmi")) {
				storage = new RMIStorageDAOImpl();
			} else if (args[2].equals("space")) {
				storage = new SpaceStorageDAOImpl();
			}

			TestingDwarf ai = new TestingDwarf(id, type, storage);
			ai.start();

		} else {
			usage();
		}
	}

	private static void usage() {
		System.out.println("Usage: java TestingDwarf id type (rmi|space)");
	}

}
