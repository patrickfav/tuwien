package entities.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;
import entities.value.Value;

@Entity
@DiscriminatorValue("VALUEDELETE")
public class ValueDeleteEvent extends ValueEvent {

	private static final long serialVersionUID = 1L;

	@Column(name="OLD_VALUE")
	private Serializable oldValue;

	public ValueDeleteEvent() {
		super();
	}
	
	public ValueDeleteEvent(User user, Value oldValue) {
		super(user, oldValue);
		this.setOldValue(oldValue.getValueAsObject());
	}

	public Serializable getOldValue() {
		return oldValue;
	}

	public void setOldValue(Serializable oldValue) {
		this.oldValue = oldValue;
	}

	@Override
	public String getEventString() {
		return super.getEventString() + EVENTTYPE.VALUEDELETE.toString();
	}

	@Override
	public Serializable getNewValue() {
		return "";
	}
	
	
}
