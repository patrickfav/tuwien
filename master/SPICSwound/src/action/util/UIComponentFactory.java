package util;

import java.io.Serializable;

import javax.ejb.Local;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import entities.formelement.FormElement;

@Local
public interface UIComponentFactory extends Serializable {

	public UIComponent getComponentFromFormElement(FormElement elem, boolean disabled);
	

	public UIComponent getComponentWithExpressions(FormElement formElement,
			boolean disabled, String beanName, String keyString);
	

	public MethodExpression createMethodExpression(String expression);

	public MethodExpression createMethodExpression(String string, Class<?>[] classes);
	
	public ValueExpression createValueExpression(String expression);

}