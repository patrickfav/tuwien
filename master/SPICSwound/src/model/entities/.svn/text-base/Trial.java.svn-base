package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

@Entity
@Table(name="TRIAL")
@Name("trial")
public class Trial implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	@Column(name="TRIAL_ID")
	private Long id;
	
	@NotNull
	@Length(min=1,max=50)
	@Column(name="NAME",nullable=false,length=50)
	private String name;
	
	@Column(name="FULL_NAME")
	private String fullName;
	
	@Column(name="BEGINDATE",nullable=true)
	private Date beginDate;
	
	@Column(name="ENDDATE",nullable=true)
	private Date endDate;
	
	@Column(name="STATUS")
	private TRIALSTATUS status = TRIALSTATUS.CREATE;
	
	@Length(min=0,max=5000)
	@Column(name="DESCRIPTION",length=5000)
	private String description;
	
	@Column(name="SUPERVISOR")
	private String supervisor;
	
	@OneToMany(mappedBy="trial")
	private List<TrialForm> trialForms;
    
    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    private User user;
    
    @OneToMany(mappedBy="trial")
    private List<Participation> participators;

    @OneToMany(mappedBy="trial",cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, fetch=FetchType.LAZY)
    private List<TrialAttachment> trialAttachments = null;

    public Trial() {
    	// per default - 
    	this.status = TRIALSTATUS.CREATE;
    }
    
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public List<TrialForm> getTrialForms() {
		if(trialForms == null)
			trialForms = new LinkedList<TrialForm>();
		return trialForms;
	}

	public void setTrialForms(List<TrialForm> trialForms) {
		this.trialForms = trialForms;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Participation> getParticipators() {
		if(participators == null)
			participators = new LinkedList<Participation>();
		return participators;
	}

	public void setParticipators(List<Participation> participators) {
		this.participators = participators;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public TRIALSTATUS getStatus() {
		return status;
	}

	public void setStatus(TRIALSTATUS status) {
		this.status = status;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public List<TrialAttachment> getTrialAttachments() {
		if(this.trialAttachments == null)
			trialAttachments = new LinkedList<TrialAttachment>();
		return trialAttachments;
	}

	public void setTrialAttachments(List<TrialAttachment> trialAttachment) {
		this.trialAttachments = trialAttachment;
	}
	
	public boolean isEditable() {
		return entities.TRIALSTATUS.CREATE.equals(getStatus());
	}
	
	public boolean isFillable() {
		return entities.TRIALSTATUS.EXECUTE.equals(getStatus());
	}
	
	public String getLocalizedStatus() {
		ResourceBundle rb = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale(),Thread.currentThread().getContextClassLoader()); 
		
		if(rb == null)
			throw new NullPointerException("Could not get ResourceBundle for messages");
		
		return rb.getString("label.status." + getStatus().toString().toLowerCase());
	}
	
	public List<TrialForm> getActiveTrialForms() {
		List<TrialForm> active = new LinkedList<TrialForm>();
		
		for(TrialForm tf : getTrialForms()) {
			if(!tf.getArchived())
				active.add(tf);
		}
		return active;
	}
	
	public List<TrialForm> getArchivedTrialForms() {
		List<TrialForm> archived = new LinkedList<TrialForm>();
		
		for(TrialForm tf : getTrialForms()) {
			if(tf.getArchived())
				archived.add(tf);
		}
		return archived;
	}
	
	public Set<Patient> getAllPatients() {
		HashSet<Patient> set = new HashSet<Patient>();
		for(Participation p : getParticipators()) {
			set.addAll(p.getPatients());
		}
		return set;
	}
  
}
