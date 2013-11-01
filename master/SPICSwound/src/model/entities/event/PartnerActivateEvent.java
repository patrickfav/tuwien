package entities.event;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("PARTNERACTIVATE")
public class PartnerActivateEvent extends TrialEvent {

	private static final long serialVersionUID = 1L;
	
	@Column(name="PARTNER_ID")
	private String partnerId;
	
	public PartnerActivateEvent() {
		super();
		setEventtype(EVENTTYPE.PARTNERACTIVATE);
	}
	
	public String getEventString() {
		return super.getEventString() + "." + EVENTTYPE.PARTNERACTIVATE.toString();
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
}

