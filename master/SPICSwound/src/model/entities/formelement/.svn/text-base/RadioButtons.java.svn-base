package entities.formelement;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="RADIOBUTTONS")
public class RadioButtons extends FormElement implements Serializable,
														 IFormElementList {

	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.STR, DATATYPE.INTEGER, DATATYPE.DECIMAL};
	
	public RadioButtons(){
		super();
		this.setType(FORMELEMENTTYPE.RADIOBUTTONS);
		values = new ArrayList<String>();
	}
	
	
	@Enumerated(EnumType.STRING)
    @Column(name="RADIOBUTTONS_ORIENTATION")
	private ORIENTATION orientation;
	
	@CollectionOfElements(fetch=FetchType.EAGER)
	@JoinTable(
			name="CODINGS",
			joinColumns = @JoinColumn(name="FORMELEMENT_ID")
			)
	@IndexColumn(name="codings_index")
	private java.util.List<String> values;

	

	public java.util.List<String> getValues() {
		return values;
	}

	public void setValues(java.util.List<String> values) {
		this.values = values;
	}

	public ORIENTATION getOrientation() {
		return orientation;
	}

	public void setOrientation(ORIENTATION orientation) {
		this.orientation = orientation;
	}
	
	public RadioButtons duplicate() {
		RadioButtons dup = new RadioButtons();
		dup.setDataType(this.getDataType());
		dup.setConstraint(this.getConstraint() == null ? null : this.getConstraint().duplicate());
		dup.setValues(new ArrayList<String>(this.getValues()));
		dup.setOrientation(this.getOrientation());
		return dup;
	}
	
	@Override
	public DATATYPE[] getValidDatatypes() {
		return VALID_DATATYPES;
	}
	
}
