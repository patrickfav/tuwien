package entities.formelement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="FPRSSCALE")
public class FPRSScale extends FormElement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.INTEGER};
	
	@Column(name="SIZE")
	private Integer size;
	
	public FPRSScale() {
		this.size = 6;
		this.setType(FORMELEMENTTYPE.FPRSSCALE);
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		if(new Integer(6).equals(size)) {
			this.size = size;
			this.setType(FORMELEMENTTYPE.FPRSSCALE);
		} else {
			throw new IllegalArgumentException("Illegal size of FPRSScale. Only values 5 and 11 allowed atm");
		}

	}
	
	public FPRSScale duplicate() {
		FPRSScale dup = new FPRSScale();
		dup.setDataType(this.getDataType());
		dup.setConstraint(this.getConstraint() == null ? null : this.getConstraint().duplicate());
		dup.size = this.getSize();
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
