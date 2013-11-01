package entities.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;
import entities.value.Value;

@Entity
@DiscriminatorValue("VALUECHANGE")
public class ValueChangeEvent extends ValueEvent {

	private static final long serialVersionUID = 1L;
	
	@Column(name="NEW_VALUE")
	private Serializable newValue;

	@Column(name="OLD_VALUE")
	private Serializable oldValue;
	
	public ValueChangeEvent() {
		super();
	}

	public ValueChangeEvent(User user, Serializable oldValue, Value newValue) {
		super(user, newValue);
		this.setNewValue(newValue.getValueAsObject());
		this.setOldValue(oldValue);
		
	}

	public Serializable getNewValue() {
		return newValue;
	}

	public void setNewValue(Serializable newValue) {
		this.newValue = newValue;
	}

	public Serializable getOldValue() {
		return oldValue;
	}

	public void setOldValue(Serializable oldValue) {
		this.oldValue = oldValue;
	}
	
	@Override
	public String getEventString() {
		return super.getEventString() + EVENTTYPE.VALUECREATE.toString();
	}
}
