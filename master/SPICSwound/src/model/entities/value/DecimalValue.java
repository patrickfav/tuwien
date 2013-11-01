package entities.value;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="DECIMAL")
public class DecimalValue extends Value implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="DECIMAL_VALUE")
	private BigDecimal value;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
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
			this.value = new BigDecimal(str);
			return;
		}
		this.value = (BigDecimal)o;
	}

	@Override
	public boolean valueEquals(Serializable s) {
		if (s instanceof BigDecimal) {
			BigDecimal to = (BigDecimal) s;
			return this.value.compareTo(to) == 0;
		}
		return false;
	}

	
	
}
