package entities.constraint;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import util.MessageUtils;
import util.MessageUtilsBean;

@Entity
@DiscriminatorValue(value = "DATECONSTRAINT")
public class DateConstraint extends Constraint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private transient MessageUtils messages;

	@Column(name = "DATE_FROM")
	private Date from;

	@Column(name = "DATE_TO")
	private Date to;

	public DateConstraint() {
		super();
		messages = MessageUtilsBean.getInstance();
	}
	
	@Override
	public String validate(Object o) {
		if (o instanceof Date) {
			Date d = (Date) o;
			if (d.compareTo(from) < 0) {
				return messages.get("error.yourdate") + d.toString()
						+ messages.get("error.toobig");
			} else if (d.compareTo(to) > 0) {
				return messages.get("error.yourdate") + d.toString()
						+ messages.get("error.toosmall");
			}
			return null;
		} else {
			return messages.get("error.numbers");
		}
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	@Override
	public DateConstraint duplicate() {
		DateConstraint dup = new DateConstraint();
		dup.setConstraintType(this.getConstraintType());
		dup.setFrom(this.from);
		dup.setTo(this.to);
		return dup;
	}

	@Override
	public boolean isEmpty() {
		return (from == null && to == null);
	}

}
