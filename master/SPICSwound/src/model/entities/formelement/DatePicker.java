package entities.formelement;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="DATEPICKER")
public class DatePicker extends FormElement implements Serializable {
	
	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.DATE};

	private static final long serialVersionUID = 1L;

	public DatePicker(){
		super();
		this.setType(FORMELEMENTTYPE.DATEPICKER);
		this.setDataType(DATATYPE.DATE);
	}
	
	public DatePicker duplicate() {
		DatePicker dup = new DatePicker();
		dup.setConstraint(this.getConstraint() == null ? null : this.getConstraint().duplicate());
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
