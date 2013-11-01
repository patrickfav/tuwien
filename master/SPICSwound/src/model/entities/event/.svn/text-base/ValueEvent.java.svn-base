package entities.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import entities.User;
import entities.value.Value;

@Entity
@DiscriminatorValue("VALUE")
public abstract class ValueEvent extends Event {

	// do not save as reference as a Value could be deleted and we should
	// still be able to restore!
	@Column(name="TRIALDATA_ID")
	private Long trialdataId;
	
	@Column(name="ATTRIBUTE_ID")
	private Long attributeId;
	
	public ValueEvent() {
		super();
	}
	
	public ValueEvent(User u, Value v) {
		super(u);
		this.setValue(v);
	}
	
	@Override
	public String getEventString() {
		return EVENTSTRINGBASE + "value.";
	}


	public Long getTrialdataId() {
		return trialdataId;
	}


	public void setTrialdataId(Long trialdataId) {
		this.trialdataId = trialdataId;
	}


	public Long getAttributeId() {
		return attributeId;
	}


	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}
	
	public void setValue(Value v) {
		this.setAttributeId(v.getId().getAttributeId());
		this.setTrialdataId(v.getId().getTrialDataId());
	}

	@Override
	public int compareTo(Event e) {
		if (e instanceof ValueEvent) {
			ValueEvent ve = (ValueEvent) e;
			int comp = this.attributeId.compareTo(ve.getAttributeId());
			if(comp != 0) {
				return comp;
			}
		}
		
		return super.compareTo(e);
	}
	
	public abstract Serializable getOldValue();
	
	public abstract Serializable getNewValue();
	
}
