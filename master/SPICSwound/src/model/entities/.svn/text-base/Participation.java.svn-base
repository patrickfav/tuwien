package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="PARTICIPATION", uniqueConstraints=@UniqueConstraint(columnNames={"USERNAME", "TRIAL_ID"}))
public class Participation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	@Column(name="PARTICIPATION_ID")
	private Long id;
	
	@Column(name="PARTICIPATING_SINCE")
	private Date participatingSince;
	
	@Column
	private Boolean active = true;
	
	@Column
	private Boolean canViewAllPatients = false;
	
	@ManyToOne
	@JoinColumn(name="USERNAME")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="TRIAL_ID")
	private Trial trial;
	
    @OneToMany(mappedBy="participation")
    private List<Patient> patients;

	public Date getParticipatingSince() {
		return participatingSince;
	}

	public void setParticipatingSince(Date participatingSince) {
		this.participatingSince = participatingSince;
	}

	public Trial getTrial() {
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Patient> getPatients() {
		if(patients == null)
			patients = new LinkedList<Patient>();
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getCanViewAllPatients() {
		return canViewAllPatients;
	}

	public void setCanViewAllPatients(Boolean canViewAllPatients) {
		this.canViewAllPatients = canViewAllPatients;
	}

}
