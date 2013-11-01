package entities.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import entities.Attribute;
import entities.TrialData;
import entities.formelement.DATATYPE;

@Entity
@Table(name="VALUE")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class Value implements Serializable {

	@Embeddable
	public static final class ValueId implements Serializable{
		private static final long serialVersionUID = 1L;

		@Column(name="ATTRIBUTE_ID")
		private Long attributeId;
		
		@Column(name="TRIALDATA_ID")
		private Long trialDataId;
		
		public ValueId() {}
		
		public ValueId(Long attributeId, Long trialDataId){
			this.attributeId = attributeId;
			this.trialDataId = trialDataId;
		}
		
		public Long getAttributeId() {
			return attributeId;
		}

		public Long getTrialDataId() {
			return trialDataId;
		}

		public int hashCode() {
            return this.attributeId.hashCode() + this.trialDataId.hashCode();
        }
		
		public boolean equals(Object obj) {
            if (obj != null && obj instanceof ValueId) {
                ValueId that = (ValueId) obj;
                return this.attributeId.equals(that.attributeId) &&
                        this.trialDataId.equals(that.trialDataId);
            }
            return false;
        }
	}
	
	@EmbeddedId
	private ValueId id = new ValueId();
	

	@ManyToOne
	@JoinColumn(name="ATTRIBUTE_ID", insertable=false, updatable=false)
	private Attribute attribute;
	
	@ManyToOne
	@JoinColumn(name="TRIALDATA_ID", insertable=false, updatable=false)
	private TrialData trialData;

    @Enumerated(EnumType.STRING)
    @Column(name="TYPE", insertable=false, updatable=false, nullable=false)
	private DATATYPE type;

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
		this.id.attributeId = attribute.getId();
	}

	public ValueId getId() {
		return id;
	}

	public void setId(ValueId id) {
		this.id = id;
	}

	public TrialData getTrialData() {
		return trialData;
	}

	public void setTrialData(TrialData trialData) {
		this.trialData = trialData;
		this.id.trialDataId = trialData.getId();
	}

	public DATATYPE getType() {
		return type;
	}

	public void setType(DATATYPE type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Value other = (Value) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/**
	 * a hook method to override comparison for specific values 
	 * (e.g. BigDecimal needs compareTo and not equals)
	 * 
	 * @param s - Serializable - the new value to be compared to the one stored in the Value object
	 * @return true if the input is equal to the currently stored value, false otherwise
	 */
	public boolean valueEquals(Serializable s) {
		return this.getValueAsObject().equals(s);
	}

	public abstract Serializable getValueAsObject();
	
	public abstract void setValueObject(Serializable o);

}
