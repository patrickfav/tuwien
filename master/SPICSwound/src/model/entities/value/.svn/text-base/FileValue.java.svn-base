package entities.value;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import entities.TrialAttachment;

@Entity
@DiscriminatorValue(value="FILE")
public class FileValue extends Value {

	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
	private TrialAttachment file;
	
	private static final long serialVersionUID = 1L;

	@Override
	public Serializable getValueAsObject() {
		return file;
	}

	@Override
	public void setValueObject(Serializable o) {
		if (o instanceof TrialAttachment) {
			TrialAttachment ta = (TrialAttachment) o;
			this.file = ta;
		}
	}

	public TrialAttachment getFile() {
		return file;
	}

	public void setFile(TrialAttachment file) {
		this.file = file;
	}
	
	

}
