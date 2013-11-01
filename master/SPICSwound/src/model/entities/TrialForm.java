package entities;

import java.io.Serializable;
import java.util.Collections;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import util.TrialFormPatientWrapper;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "TRIALFORM")
public class TrialForm implements Serializable, TrialFormPatientWrapper,
		Comparable<TrialForm> {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	@Id
	@GeneratedValue
	@Column(name = "TRIALFORM_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	// TODO: deprecate and remove in a later version
	@Column(name = "TRIALSPECIFIC")
	private Boolean trialSpecific = false;

	@Column(name = "FILLONCE")
	private Boolean fillOnce = false;

	@XmlTransient
	@Column(name = "LAST_MODIFIED")
	private Date lastModified;

	@XmlTransient
	@Column(name = "SORT")
	private Integer sort;

	@XmlTransient
	@Column(name = "ARCHIVED", nullable = false)
	private Boolean archived = false;

	@XmlTransient
	@Column(name = "ARCHIVEDATE", nullable = true)
	private Date archivedSince;

	@Column(name = "FILLABLE_AFTER_ARCHIVE")
	private Boolean fillableAfterArchive;

	@XmlTransient
	@OneToOne(optional = true)
	private TrialForm predecessor;

	@XmlElement(name = "attributeGroup")
	@OneToMany(mappedBy = "trialForm", cascade = CascadeType.ALL)
	private List<AttributeGroup> attributeGroups;

	@XmlTransient
	@OneToMany(mappedBy = "trialform")
	private List<TrialData> trialDatas;

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRIAL_ID")
	private Trial trial;

	@XmlTransient
	@Transient
	private Patient wrapperPatient;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TrialData> getTrialDatas() {
		if (trialDatas == null)
			trialDatas = new LinkedList<TrialData>();
		return trialDatas;
	}

	public void setTrialDatas(List<TrialData> trialDatas) {
		this.trialDatas = trialDatas;
	}

	public List<TrialData> getTrialDatasByPatient(Patient patient) {
		List<TrialData> tdByPatient = new LinkedList<TrialData>();
		for (TrialData td : getTrialDatas()) {
			if (td.getPatient().equals(patient)) {
				tdByPatient.add(td);
			}
		}
		return tdByPatient;
	}

	public List<AttributeGroup> getAttributeGroups() {
		if (attributeGroups == null)
			attributeGroups = new LinkedList<AttributeGroup>();
		return attributeGroups;
	}

	public void setAttributeGroups(List<AttributeGroup> attributeGroups) {
		this.attributeGroups = attributeGroups;
	}

	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}

	public Boolean getTrialSpecific() {
		return trialSpecific;
	}

	public void setTrialSpecific(Boolean trialSpecific) {
		this.trialSpecific = trialSpecific;
	}

	public Boolean getFillOnce() {
		return fillOnce;
	}

	public void setFillOnce(Boolean fillOnce) {
		this.fillOnce = fillOnce;
	}

	public AttributeGroup getAGbySortID(int id) {
		for (AttributeGroup ag : getAttributeGroups()) {
			if (ag.getSort().intValue() == id) {
				return ag;
			}
		}
		return null;
	}

	public Boolean getCanAddTrialData() {
		if (!this.getTrialSpecific())
			return true;
		else
			return this.getPatientTrialData().size() == 0;
	}

	/* TrialFormPatientWrapper methods */

	public List<TrialData> getPatientTrialData() {
		List<TrialData> patientTrialData = getTrialDatasByPatient(wrapperPatient);
		
		Collections.sort(patientTrialData, new TrialData.TrialDataLastModifiedComperator(true));
		
		return patientTrialData;
	}

	public void resetWrapperPatient() {
		this.wrapperPatient = null;
	}

	public void setWrapperPatient(Patient patient) {
		this.wrapperPatient = patient;
	}

	/* end TrialFormPatientWrapper methods */

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	public Date getArchivedSince() {
		return archivedSince;
	}

	public void setArchivedSince(Date archiveDate) {
		this.archivedSince = archiveDate;
	}

	public Boolean getFillableAfterArchive() {
		return fillableAfterArchive;
	}

	public void setFillableAfterArchive(Boolean fillableAfterArchive) {
		this.fillableAfterArchive = fillableAfterArchive;
	}

	public TrialForm getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(TrialForm predecessor) {
		this.predecessor = predecessor;
	}

	/**
	 * just comparing over the ID field... ATTENTION comparTo == 0 and equals do
	 * not necessarily return the same result
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TrialForm other = (TrialForm) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * ATTENTION compareTo == 0 and equals do not necessarily return the same
	 * result
	 */
	public int compareTo(TrialForm tf) {
		return this.getSort().compareTo(tf.getSort());
	}

	public boolean isFirst() {
		return getSort().equals(new Integer(0));
	}

	public boolean isLast() {
		return Collections.max(getTrial().getTrialForms()).equals(this);
	}

	public boolean isEditable() {
		if (getTrialSpecific())
			return false;

		return !getArchived()
				&& entities.TRIALSTATUS.CREATE.equals(getTrial().getStatus());
	}

	public boolean isFillable() {
		if (getTrialSpecific())
			return false;

		if (getArchived())
			return getFillableAfterArchive();

		return entities.TRIALSTATUS.EXECUTE.equals(getTrial().getStatus());
	}

	public TrialForm duplicate() {
		TrialForm dup = new TrialForm();
		dup.setDescription(this.description);
		dup.setName(this.name);
		dup.setLastModified(new Date(System.currentTimeMillis()));
		dup.setSort(this.sort);
		dup.setTrial(this.trial);
		dup.setTrialSpecific(this.trialSpecific);
		for (AttributeGroup ag : this.getAttributeGroups()) {
			AttributeGroup dupAg = ag.duplicate();
			dupAg.setTrialForm(dup);
			dup.getAttributeGroups().add(dupAg);
		}
		return dup;
	}
	
	public List<Attribute> fullSortedAttributeList() {
		List<Attribute> result = new LinkedList<Attribute>();
		Collections.sort(getAttributeGroups());
		
		for(AttributeGroup ag: getAttributeGroups()) {
			Collections.sort(ag.getAttributes());
			result.addAll(ag.getAttributes());
		}
		
		return result;
	}
	
}
