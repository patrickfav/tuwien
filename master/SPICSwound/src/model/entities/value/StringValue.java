package entities.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="STR")
public class StringValue extends Value implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="STR_VALUE")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Serializable getValueAsObject() {
		return value;
	}

	@Override
	public void setValueObject(Serializable o) {
		this.value = (String)o;
		
	}
}
