package entities.constraint;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;

import util.MessageUtils;
import util.MessageUtilsBean;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="STRINGCONSTRAINT")
public class StringConstraint extends Constraint implements Serializable {

	private transient MessageUtils messages;
	
	@Column(name="REGEX_P")
	private String regexp;

	public StringConstraint() {
		super();
		messages = MessageUtilsBean.getInstance();
	}
	
	public String getRegexp() {
		return regexp;
	}

	public void setRegexp(String regexp) {
		this.regexp = regexp;
	}

	@Override
	public String validate(Object o) {
		
		if(isEmpty(regexp) || isEmpty(o)) {
			return null;
		}
		
		if (o instanceof String) {
			String str = (String) o;
			
			if(Pattern.matches(regexp, str)) {
				return null;
			} else {
				return MessageUtilsBean.formatMessage("error.regexpmsg", regexp);
			}
			
		}
		return messages.get("error.regexp");
	}

	private boolean isEmpty(Object o) {
		if(o == null) {
			return true;
		}
		
		if (o instanceof String) {
			String str = (String) o;
			
			return str.equals("");
			
		}
		return false;
	}

	@Override
	public StringConstraint duplicate() {
		StringConstraint dup = new StringConstraint();
		dup.setRegexp(this.getRegexp());
		dup.setConstraintType(this.getConstraintType());
		return dup;
	}

	@Override
	public boolean isEmpty() {
		return StringUtils.isBlank(regexp);
	}

}
