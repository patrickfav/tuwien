package entities.formelement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="TEXTFIELD")
public class TextField extends FormElement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.STR, DATATYPE.INTEGER, DATATYPE.DECIMAL};
	
	private static final Integer DEFAULT_SIZE = 20;
	
	public static final Integer LENGTH = 255;
	
	@Column(name="SIZE")
	private Integer size = DEFAULT_SIZE;
	
	public TextField(){
		super();
		this.setType(FORMELEMENTTYPE.TEXTFIELD);
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	public TextField duplicate() {
		TextField dup = new TextField();
		dup.setDataType(this.getDataType());
		dup.setConstraint(this.getConstraint() == null ? null : this.getConstraint().duplicate());
		dup.size = this.getSize();
		return dup;
	}
	
	@Override
	public DATATYPE[] getValidDatatypes() {
		return VALID_DATATYPES;
	}
}
