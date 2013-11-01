package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="ORGGROUP")
public class OrgGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	@Column(name="GROUP_ID")
	private Long id;
	
	@Column(name="GROUPNAME")
	private String name;
	
    @ManyToMany
    @JoinTable(
        name="GROUP2PATIENT",
        joinColumns={@JoinColumn(name="GROUP_ID")},
        inverseJoinColumns={@JoinColumn(name="PATIENT_ID")}
    )
    private List<Patient> patients;
    
    @ManyToMany
    @JoinTable(
        name="GROUP2USER",
        joinColumns={@JoinColumn(name="GROUP_ID")},
        inverseJoinColumns={@JoinColumn(name="USERNAME")}
    )
    private List<User> users;

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

	public List<Patient> getPatients() {
		if(patients == null) {
			patients = new LinkedList<Patient>();
		}
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public List<User> getUsers() {
		if(users == null)
			users = new LinkedList<User>();
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
