package entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import entities.value.Value;

@Entity
@Table(name="TRIALDATA")
public class TrialData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
    @Column(name="TRIALDATA_ID")
    private Long id;
	
	@Column(name="SAVED_ON", nullable=false)
	private Date savedOn;
	
	@Column(name="LAST_MODIFIED")
	private Date lastModified;
	
	@OneToMany(mappedBy="trialData")
	private Set<Value> values;
	
	@ManyToOne
	@JoinColumn(name="TRIALFORM_ID")
	private TrialForm trialform;
	
	@ManyToOne
	@JoinColumn(name="PATIENT_ID")
	private Patient patient;
	
	@ManyToOne
	@JoinColumn(name="SAVEDBY")
	private User savedBy;
	
	@ManyToOne
	@JoinColumn(name="LASTMODIFIEDBY")
	private User lastModifiedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getSavedOn() {
		return savedOn;
	}

	public void setSavedOn(Date savedOn) {
		this.savedOn = savedOn;
	}

	public Set<Value> getValues() {
		if(values == null) {
			values = new HashSet<Value>();
		}
		return values;
	}

	public void setValues(Set<Value> values) {
		this.values = values;
	}

	public TrialForm getTrialform() {
		return trialform;
	}

	public void setTrialform(TrialForm trialform) {
		this.trialform = trialform;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public Value getValueByAttributeId(Long id) {
		for(Value v : getValues()) {
			if(v.getAttribute().getId().equals(id))
				return v;
		}
		
		return null;
	}

	public User getSavedBy() {
		return savedBy;
	}

	public void setSavedBy(User savedBy) {
		this.savedBy = savedBy;
	}

	public User getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(User lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TrialData other = (TrialData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	static class TrialDataLastModifiedComperator implements Comparator<TrialData> {
		
		private boolean asc = true;
		
		public TrialDataLastModifiedComperator() {
			this.asc = true;
		}
		
		public TrialDataLastModifiedComperator(boolean desc) {
			this.asc = !desc;
		}

		public int compare(TrialData td1, TrialData td2) {
			return (asc ? 1 : -1) * td1.getLastModified().compareTo(td2.getLastModified());
		}
		
	}
}
