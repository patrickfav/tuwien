package entities.formelement;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VANCOUVERSCARSCALE")
public class VancouverScarScale extends FormElement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.INTEGERSET};
	
	public VancouverScarScale() {
		setDataType(DATATYPE.INTEGERSET);
		setType(FORMELEMENTTYPE.VANCOUVERSCARSCALE);
	}
	
	public VancouverScarScale duplicate() {
		VancouverScarScale vss = new VancouverScarScale();
		return vss;
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
