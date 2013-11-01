package workers;

import org.apache.log4j.Logger;

import dao.RMIStorageDAOImpl;
import dao.SpaceStorageDAOImpl;
import dao.interfaces.IStorageDAO;
import exceptions.IDaoReadException;
import exceptions.IDaoSaveException;

import products.Teddybear;
import workers.abstracts.AbstractWorker;

public class LogisticReindeer extends AbstractWorker {

	private static Logger log = Logger.getLogger(LogisticReindeer.class);
	private IStorageDAO dao;

	public LogisticReindeer(String id, IStorageDAO dao) {
		super(id);
		this.dao = dao;
		log.info("Reindeer created: " + getWorkerId().getId() + ".");
	}

	@Override
	public void run() {
		
		
		while (isAlive()) {

			Teddybear t = null;
			
			try {
				log.info("Reindeer " + getWorkerId().getId() + " #["
						+ getId() + "]" + ": retreive teddybear... ");
				t = dao.getTeddybearDao().takeChecked();
			} catch (IDaoReadException e1) {
				e1.printStackTrace();
			}

			if (t != null) {

				if (t.isDefectCheckDone() && t.isWeightCheckDone()
						&& t.isAssembleDone()) {
					if (t.isDefect()) {
						try {
							t.markReadyButDefect(this.getWorkerId());
							dao.getTeddybearDao().saveToDefect(t);
						} catch (IDaoSaveException e) {
							e.printStackTrace();
						}
						log.info("Reindeer " + getWorkerId().getId() + " #["
								+ getId() + "]" + ": found defect teddybear "
								+ t);
					} else {
						try {
							t.markShipped(this.getWorkerId());
							
							dao.getTeddybearDao().saveToShipping(t);
						} catch (IDaoSaveException e) {
							e.printStackTrace();
						}
						log.info("Reindeer " + getWorkerId().getId() + " #["
								+ getId() + "]" + ": shipped teddybear " + t);
					}
				}
			}
			
			sleepReindeer();
			
		}
	}
	
	private void sleepReindeer() {
		long sleepTime=0;
		try {
			sleepTime = generateRandomSleepTime(REINDEER_WORK_TIME_MIN,REINDEER_WORK_TIME_MAX);
			sleep(sleepTime);
			log.info("Reindeer " + getWorkerId().getId() + " #["
					+ getId() + "]" + ": sleeping "
					+ sleepTime +" ms.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String id = "";
		IStorageDAO storage = null;

		if (args.length == 2) {
			id = args[0];
			if (args[1].equals("rmi")) {
				storage = new RMIStorageDAOImpl();
			} else if (args[1].equals("space")) {
				storage = new SpaceStorageDAOImpl();
			}

			LogisticReindeer ai = new LogisticReindeer(id, storage);
			ai.start();

		} else {
			usage();
		}
	}

	private static void usage() {
		System.out.println("Usage: java LogisticReindeer id (rmi|space)");
	}
}
