package products.parts.abstracts;

import java.io.Serializable;
import java.util.UUID;

import workers.id.WorkerID;

public abstract class Product implements Cloneable, Serializable {

	private static final long serialVersionUID = -7319698524865019723L;

	protected UUID id;
	protected long worktime;
	protected WorkerID creator;
	protected boolean defect = false;
	protected boolean reserved = false; //internal status: reserved for assembly
	protected boolean assembled = false; //set true when assembled in teddy
	
	public Product() {
		super();
		this.id = null;
		this.worktime = 0;
		this.creator = null;
		this.defect = false;
	}
	
	public Product(long worktime, WorkerID creator, boolean defect) {
		super();
		this.id = UUID.randomUUID();
		this.worktime = worktime;
		this.creator = creator;
		this.defect = defect;
	}
	
	public abstract String getProductName();
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public long getWorktime() {
		return worktime;
	}

	public void setWorktime(long worktime) {
		this.worktime = worktime;
	}

	public WorkerID getCreator() {
		return creator;
	}

	public void setCreator(WorkerID creator) {
		this.creator = creator;
	}

	public boolean isDefect() {
		return defect;
	}

	public void setDefect(boolean defect) {
		this.defect = defect;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
	
	public boolean isAssembled() {
		return assembled;
	}

	public void setAssembled(boolean assembled) {
		this.assembled = assembled;
	}

	@Override
	public Product clone() throws CloneNotSupportedException {
		Product clone = (Product) super.clone();
		return clone;
	}
	
	@Override
	public String toString() {
		return getProductName()+" (defect: "+isDefect()+",worktime: "+ getWorktime() +")";
	}
}
