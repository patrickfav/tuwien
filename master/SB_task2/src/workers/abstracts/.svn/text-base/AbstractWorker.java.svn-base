package workers.abstracts;

import java.util.Random;

import workers.id.WorkerID;

public abstract class AbstractWorker extends Thread {
	
	public static final long DWARF_WORK_TIME_MIN =1000l;
	public static final long DWARF_WORK_TIME_MAX =3000l;
	public static final long IMP_WORK_TIME_MIN=2000l;
	public static final long IMP_WORK_TIME_MAX=4000l;
	public static final long REINDEER_WORK_TIME_MIN=2000l;
	public static final long REINDEER_WORK_TIME_MAX=3000l;
	
	private WorkerID workerId;
	private Random rand;
	
	public AbstractWorker(String id) {
		workerId = new WorkerID(id);
		rand= new Random();
	}

	public WorkerID getWorkerId() {
		return workerId;
	}
	
	public long generateRandomSleepTime(long min, long max) {
		return min + (long) (rand.nextDouble() * (max - min));
	}
}