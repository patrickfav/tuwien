package entities.value;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import entities.FileSet;

@Entity
@DiscriminatorValue(value="FILESET")
public class FileValueSet extends Value {

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade=CascadeType.ALL)
	private FileSet fileset;
	
	@Override
	public Serializable getValueAsObject() {
		return fileset;
	}

	@Override
	public void setValueObject(Serializable o) {
		this.fileset = (FileSet)o;
	}

	@Override
	public boolean valueEquals(Serializable s) {
		return fileset.equals(s);
	}

}
