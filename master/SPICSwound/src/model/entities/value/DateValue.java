package entities.value;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue(value = "DATE")
public class DateValue extends Value implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The default Date pattern. eg. "12. Oktober 1980"
	 */
	public static final String datePattern = "dd. MMMM yyyy";

	@Transient
	private SimpleDateFormat sdf = new SimpleDateFormat(datePattern,
			org.jboss.seam.international.Locale.instance());

	@Column(name = "DATE_VALUE")
	private Date value;

	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	public Serializable getValueAsObject() {
		return value;
	}

	public void setValueObject(Serializable o) {

		if (o instanceof String) {

			String str = (String) o;

			try {
				this.value = sdf.parse(str);
			} catch (ParseException e) {
				throw new IllegalArgumentException("could not parse: " + str
						+ " (using pattern: " + datePattern + ")");
			}

			return;
		}
		this.value = (Date) o;
	}

	@Override
	public boolean valueEquals(Serializable s) {

		if (s instanceof Date) {
			Date comp = (Date) s;

			return this.value.getTime() == comp.getTime();
		}
		return false;

	}
}
