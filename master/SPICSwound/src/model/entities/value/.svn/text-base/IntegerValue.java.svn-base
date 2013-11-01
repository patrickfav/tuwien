package entities.value;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="INTEGER")
public class IntegerValue extends Value implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(name="INTEGER_VALUE")
	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
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
			value = Integer.parseInt(str);
			return;
		}
		if (o instanceof BigDecimal) {
			BigDecimal b = (BigDecimal)o;
			value = b.intValue();
			return;
		}
		value = (Integer)o;
		
	}
	
	
}
