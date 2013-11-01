package entities.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="BOOLEAN")
public class BooleanValue extends Value implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name="BOOLEAN_VALUE")
	private Boolean value;

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public Serializable getValueAsObject() {
		return value;
	}

	@Override
	public void setValueObject(Serializable o) {
		if (o instanceof String) {
			String str = (String) o;
			this.value = Boolean.parseBoolean(str);
			return;
		}
		this.value = (Boolean)o;
		
	}
	
	
}
