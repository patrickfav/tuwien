package entities.constraint;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import util.MessageUtils;
import util.MessageUtilsBean;

@Entity
@DiscriminatorValue(value = "DECIMALCONSTRAINT")
public class DecimalConstraint extends Constraint implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient MessageUtils messages;
	
	@Column(name = "MIN_VALUE_DECIMAL")
	private BigDecimal min;

	@Column(name = "MAX_VALUE_DECIMAL")
	private BigDecimal max;
	
	public DecimalConstraint() {
		super();
		messages = MessageUtilsBean.getInstance();
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	@Override
	public String validate(Object o) {
		if (o instanceof BigDecimal) {
			BigDecimal f = (BigDecimal) o;
			if (f == null)
				return null;
			if (min != null && f.compareTo(min) < 0) {
				return MessageUtilsBean.formatMessage("error.toosmall", f,min,max);
			} else if (max != null && f.compareTo(max) > 0) {
				return MessageUtilsBean.formatMessage("error.toobig", f,min,max);
			}
			return null;
		} else {
			return messages.get("error.numbers");
		}
	}

	@Override
	public DecimalConstraint duplicate() {
		DecimalConstraint dup = new DecimalConstraint();
		dup.setMax(this.getMax());
		dup.setMin(this.getMin());
		dup.setConstraintType(this.getConstraintType());
		return dup;
	}

	@Override
	public boolean isEmpty() {
		return (min == null && max == null);
	}

}
