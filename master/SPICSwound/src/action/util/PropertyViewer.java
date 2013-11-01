package util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.log.Log;

import db.interfaces.IPropertyDAO;
import entities.Property;

@Name("PropertyViewer")
@Restrict("#{s:hasRole('admin')}")
@Scope(ScopeType.CONVERSATION)
public class PropertyViewer {

	@DataModel
	private List<Property> propertyList;

	@In
	private IPropertyDAO propertyDAO;

	@In(required = false)
	private Property currentProperty;

	@Logger
	private Log log;

	@Factory("propertyList")
	public void initPropertyList() {
		propertyList = propertyDAO.findAll();
	}

	public List<Property> getPropertyList() {
		return propertyList;
	}

	public void validate(FacesContext arg0, UIComponent arg1, Object inputObj) {

		if (inputObj instanceof String == false)
			throw new ValidatorException(
					new FacesMessage("Unexpected datatype"));

		String s = (String) inputObj;

		// no regex defined. we're done
		if (s == null || currentProperty == null
				|| currentProperty.getRegex() == null)
			return;

		if (s.matches(currentProperty.getRegex()) == false) {
			log.debug("property #0 doesn't match regex #1", currentProperty
					.getKey(), currentProperty.getRegex());
			
			throw new ValidatorException(new FacesMessage(MessageUtilsBean
					.formatMessage("error.invalidvalue", currentProperty
							.getKey())));
		}

	}

}
