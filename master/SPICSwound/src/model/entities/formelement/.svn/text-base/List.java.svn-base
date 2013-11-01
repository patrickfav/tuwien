package entities.formelement;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="LIST")
public class List extends FormElement implements Serializable,
												 IFormElementList{

	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.STR, DATATYPE.INTEGER, DATATYPE.DECIMAL};
	
	public List(){
		super();
		this.setType(FORMELEMENTTYPE.LIST);
		values = new ArrayList<String>();
	}
	
	
	@Column(name="SIZE")
	private Integer size;
	
	@CollectionOfElements(fetch=FetchType.EAGER)
	@JoinTable(
			name="CODINGS",
			joinColumns = @JoinColumn(name="FORMELEMENT_ID")
			)
	@IndexColumn(name="codings_index")
	private java.util.List<String> values;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public java.util.List<String> getValues() {
		return values;
	}

	public void setValues(java.util.List<String> values) {
		this.values = values;
	}
	
	public List duplicate() {
		List dup = new List();
		dup.setDataType(this.getDataType());
		dup.setConstraint(this.getConstraint() == null ? null : this.getConstraint().duplicate());
		dup.size = this.getSize();
		dup.setValues(new ArrayList<String>(this.getValues()));
		return dup;
	}
	
	@Override
	public DATATYPE[] getValidDatatypes() {
		return VALID_DATATYPES;
	}
}
