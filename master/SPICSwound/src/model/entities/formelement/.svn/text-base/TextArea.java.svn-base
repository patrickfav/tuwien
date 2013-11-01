package entities.formelement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="TEXTAREA")
public class TextArea extends FormElement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.LONGSTR};
	
	private static final Integer DEFAULT_SIZE = 300;
	
	public static final Integer LENGTH = 2500;
	
	@Column(name="SIZE")
	private Integer size = DEFAULT_SIZE;
	
	public TextArea(){
		super();
		this.setType(FORMELEMENTTYPE.TEXTAREA);
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	public TextArea duplicate() {
		TextArea dup = new TextArea();
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
