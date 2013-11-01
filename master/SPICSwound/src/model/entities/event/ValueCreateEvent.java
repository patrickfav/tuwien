package entities.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;
import entities.value.Value;

@Entity
@DiscriminatorValue("VALUECREATE")
public class ValueCreateEvent extends ValueEvent {

	private static final long serialVersionUID = 1L;
	
	@Column(name="NEW_VALUE")
	private Serializable newValue;
	
	public ValueCreateEvent() {
		super();
	}
	
	public ValueCreateEvent(User u, Value newValue) {
		super(u, newValue);
		this.setNewValue(newValue.getValueAsObject());
	}

	public Serializable getNewValue() {
		return newValue;
	}

	public void setNewValue(Serializable newValue) {
		this.newValue = newValue;
	}
	
	@Override
	public String getEventString() {
		return super.getEventString() + EVENTTYPE.VALUECREATE.toString();
	}

	@Override
	public Serializable getOldValue() {
		return "";
	}


}
