package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * currently a patient is uniquely identified by the kennnummer for each user (savedby)
 * this means that two different users can have different patients with the same kennnummer
 * in the same documentation. on the other hand a single user can not have two different 
 * patients with the same kennnummer on two different documentations. therefore it might be 
 * smarter to have a 1:n mapping between participation and patient and therefore have patients
 * be only unique for one user in on documentation.
 * 
 * @author inso
 *
 */
@Entity
@Table(name="PATIENT", uniqueConstraints=@UniqueConstraint(columnNames={"KENNNUMMER","SAVEDBY"}))
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name="PATIENT_ID")
	private Long id;
	
	@Column(name="KENNNUMMER")
	private String kennnummer;
	
	@Column(name="REGISTRATION_DATE")
	private Date registrationDate;
	
	/*
	 * this column is for now only created to enable setting single patients
	 * to hidden directly via sql. 
	 * default: false!
	 */
	@Column(name="HIDDEN")
	private Boolean hidden = false;
	
	@Column
	private Boolean myExport = true;
	
	@Column
	private Boolean export = true;
	
	@OneToMany(mappedBy="patient", fetch=FetchType.EAGER)
	private List<TrialData> trialdatas;

    @ManyToOne
    @JoinColumn(name="PARTICIPATION")
    private Participation participation;
    
    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy="patients"
      )
    private List<OrgGroup> groups;

	@ManyToOne
	@JoinColumn(name="SAVEDBY")
	private User savedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKennnummer() {
		return kennnummer;
	}

	public void setKennnummer(String kennnummer) {
		this.kennnummer = kennnummer;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public List<TrialData> getTrialdatas() {
		if(trialdatas == null)
			trialdatas = new LinkedList<TrialData>();
		return trialdatas;
	}
	
	@Deprecated
	public Integer getTrialDataCount() {
		return Math.max(getTrialdatas().size() - 1, 0);
	}

	public void setTrialdatas(List<TrialData> trialdatas) {
		this.trialdatas = trialdatas;
	}

	public Participation getParticipation() {
		return participation;
	}

	public void setParticipation(Participation participation) {
		this.participation = participation;
	}
	
	public List<OrgGroup> getGroups() {
		if(groups == null) {
			groups = new LinkedList<OrgGroup>();
		}
		return groups;
	}

	public void setGroups(List<OrgGroup> groups) {
		this.groups = groups;
	}

	public List<TrialData> getTrialDatasByUser(User user) {
		List<TrialData> tdList = new LinkedList<TrialData>();
		
		for(TrialData td : getTrialdatas()) {
			if(td.getSavedBy().equals(user))
				tdList.add(td);
		}
		
		return tdList;
	}

	public User getSavedBy() {
		return savedBy;
	}

	public void setSavedBy(User savedBy) {
		this.savedBy = savedBy;
	}

	public Boolean getMyExport() {
		return myExport;
	}

	public void setMyExport(Boolean myExport) {
		this.myExport = myExport;
	}

	public Boolean getExport() {
		return export;
	}

	public void setExport(Boolean export) {
		this.export = export;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((kennnummer == null) ? 0 : kennnummer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Patient other = (Patient) obj;
		if (kennnummer == null) {
			if (other.kennnummer != null)
				return false;
		} else if (!kennnummer.equals(other.kennnummer))
			return false;
		return true;
	}	
	
}
