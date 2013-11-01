package dst1.helper;

import dst1.model.hardware.Computer;

public class ComputerExecutionWrapper {
	private double duration;
	private Computer c;
	

	public ComputerExecutionWrapper(double duration, Computer c) {
		super();
		this.duration = duration;
		this.c = c;
	}
	
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public Computer getC() {
		return c;
	}
	public void setC(Computer c) {
		this.c = c;
	}
	
	@Override
	public String toString() {
		return c.getName()+" from "+c.getLocation()+" has usage "+duration;
	}
	
}
