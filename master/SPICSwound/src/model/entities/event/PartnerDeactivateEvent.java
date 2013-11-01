package entities.event;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PARTNERDEACTIVATE")
public class PartnerDeactivateEvent extends TrialEvent {

	private static final long serialVersionUID = 1L;
	
	@Column(name="PARTNER_ID")
	private String partnerId;
	
	public PartnerDeactivateEvent() {
		super();
		setEventtype(EVENTTYPE.PARTNERDEACTIVATE);
	}
	
	public String getEventString() {
		return super.getEventString() + "." + EVENTTYPE.PARTNERDEACTIVATE.toString();
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	
}
