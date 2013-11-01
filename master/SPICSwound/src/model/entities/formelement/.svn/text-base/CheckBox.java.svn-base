package entities.formelement;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CHECKBOX")
public class CheckBox extends FormElement implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final DATATYPE[] VALID_DATATYPES = { DATATYPE.BOOLEAN };

	public CheckBox() {
		super();
		this.setType(FORMELEMENTTYPE.CHECKBOX);
		this.setDataType(DATATYPE.BOOLEAN);
	}

	public CheckBox duplicate() {
		CheckBox dup = new CheckBox();
		dup.setDataType(this.getDataType());
		dup.setType(this.getType());
		dup.setConstraint(this.getConstraint() == null ? null : this
				.getConstraint().duplicate());
		return dup;
	}

	@Override
	public DATATYPE[] getValidDatatypes() {
		return VALID_DATATYPES;
	}

	@Override
	public boolean hasUnit() {
		return false;
	}

}
