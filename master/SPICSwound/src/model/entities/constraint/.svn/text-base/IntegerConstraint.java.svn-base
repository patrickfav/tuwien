package entities.constraint;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import util.MessageUtils;
import util.MessageUtilsBean;

@Entity
@DiscriminatorValue(value = "INTEGERCONSTRAINT")
public class IntegerConstraint extends Constraint implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient MessageUtils messages;
	
	@Column(name = "MIN_VALUE")
	private Integer min;

	@Column(name = "MAX_VALUE")
	private Integer max;

	public IntegerConstraint() {
		super();
		messages = MessageUtilsBean.getInstance();
	}
	
	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	@Override
	public String validate(Object o) {
		if (o instanceof Integer) {
			Integer i = (Integer) o;
			if (min != null && i.compareTo(min) < 0) {
				return MessageUtilsBean.formatMessage("error.toosmall", i,min,max);
			} else if (max != null && i.compareTo(max) > 0) {
				return MessageUtilsBean.formatMessage("error.toobig", i,min,max);
			}
			return null;
		} else {
			return messages.get("error.numbers");
		}
	}

	@Override
	public IntegerConstraint duplicate() {
		IntegerConstraint dup = new IntegerConstraint();
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
