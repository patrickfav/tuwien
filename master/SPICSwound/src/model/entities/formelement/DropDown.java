package entities.formelement;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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
@DiscriminatorValue(value="DROPDOWN")
public class DropDown extends FormElement implements Serializable,
													 IFormElementList{
	
	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.STR, DATATYPE.INTEGER, DATATYPE.DECIMAL};

	public DropDown(){
		super();
		this.setType(FORMELEMENTTYPE.DROPDOWN);
		values = new LinkedList<String>();
	}
	
	
	@Column(name="SIZE")
	private Integer size;
	
	@CollectionOfElements(fetch=FetchType.EAGER)
	@JoinTable(
			name="CODINGS",
			joinColumns = @JoinColumn(name="FORMELEMENT_ID")
			)
	@IndexColumn(name="codings_index")
	private List<String> values;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public DropDown duplicate() {
		DropDown dup = new DropDown();
		dup.setConstraint(this.getConstraint() == null ? null : this.getConstraint().duplicate());
		dup.setDataType(this.getDataType());
		dup.setSize(this.getSize());
		dup.setValues(new LinkedList<String>(this.values));
		return dup;
	}
	
	@Override
	public DATATYPE[] getValidDatatypes() {
		return VALID_DATATYPES;
	}
	
}
