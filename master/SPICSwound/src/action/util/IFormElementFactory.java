package util;

import java.io.Serializable;
import java.util.List;

import entities.formelement.FORMELEMENTTYPE;
import entities.formelement.FormElement;

public interface IFormElementFactory extends Serializable {
	
	public FormElement createFormElement(FORMELEMENTTYPE ft);
	
	public FormElement createFormElementWithListValues(FORMELEMENTTYPE ft, List<String> values);

}
