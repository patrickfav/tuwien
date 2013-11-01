package entities.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="LONGSTR")
public class LongstringValue extends Value implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="LONGTSTR_VALUE", length=2500)
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
