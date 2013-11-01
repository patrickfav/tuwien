package entities.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import entities.Trial;

@Entity
@DiscriminatorValue("TRIAL")
public abstract class TrialEvent extends Event {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Trial trial;
	
	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}

	@Override
	public String getEventString() {
		return EVENTSTRINGBASE + "trial";
	}

}
