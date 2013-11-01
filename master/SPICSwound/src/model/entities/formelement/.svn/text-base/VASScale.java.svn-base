package entities.formelement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="VASSCALE")
public class VASScale extends FormElement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.INTEGER};
	
	@Column(name="SIZE")
	private Integer size;
	
	public VASScale() {
		this.size = new Integer(11);
		this.setType(FORMELEMENTTYPE.VASSCALE);
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		if(new Integer(11).equals(size)) {
			this.size = size;
			this.setType(FORMELEMENTTYPE.VASSCALE);
		} else {
			throw new IllegalArgumentException("Illegal size of VASScale. Only values 5 and 11 allowed atm");
		}
		
	}
	
	public VASScale duplicate() {
		VASScale dup = new VASScale();
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
