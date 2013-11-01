package aufgabe6;

public class Male implements Geschlecht {
	private boolean military_done;
	private boolean military_able;
	private boolean military_checked;
	
	//standard constructor, all attributes need to be set
	public Male(boolean military_done, boolean military_able,
			boolean military_checked) {
		super();
		this.setMilitary_done(military_done);
		this.setMilitary_able(military_able);
		this.military_checked = military_checked;
	}
	
	//switches the military-service attribute-done to true - will also set the preconditioned switches (is able to, has been checked) to true
	public void setMilitaryToDone() {
		setMilitary_done(true);
		setMilitary_able(true);
		military_checked = true;
	}
	
	//will only set the military-service-done attribute to false
	public void setMilitaryToUndone() {
		setMilitary_done(false);
	}
	
	//sets the military-service-able-to attribute to true, also sets the precondition was-checked to true to keep conistency
	public void setMilitaryToAble() {
		setMilitary_able(true);
		military_checked = true;
	}
	//will only set the military-service-able-to attribute to false
	public void setMilitaryToUnable() {
		setMilitary_able(false);
	}
	
	//returns the military-checked attribute
	public boolean isMilitaryChecked() {
		return military_checked;
	}
	
	public void setMilitaryChecked(boolean military_checked) {
		this.military_checked = military_checked;
	}
	
	public void setMilitary_done(boolean military_done) {
		this.military_done = military_done;
	}

	public boolean getMilitary_done() {
		return military_done;
	}

	public void setMilitary_able(boolean military_able) {
		this.military_able = military_able;
	}

	public boolean isMilitary_able() {
		return military_able;
	}
	
	
}
