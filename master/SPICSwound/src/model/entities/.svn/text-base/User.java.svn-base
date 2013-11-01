package entities;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.validator.Email;
import org.jboss.seam.annotations.security.management.UserEnabled;
import org.jboss.seam.annotations.security.management.UserFirstName;
import org.jboss.seam.annotations.security.management.UserLastName;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;

@Entity
@Table(name = "SPICSUSER")
public class User implements Serializable, Principal {

	private static final long serialVersionUID = 1L;

	@UserPrincipal
	@Id
	@Column(name = "USERNAME")
	private String username;

	@UserEnabled
	@Column(name = "ENABLED")
	private Boolean enabled = true;

	@UserPassword(hash = "MD5")
	@Column(name = "PASSWORDHASH")
	private String passwordHash;

	@UserFirstName
	@Column(name = "FIRSTNAME")
	private String firstname;

	@UserLastName
	@Column(name = "LASTNAME")
	private String lastname;

	@Column(name = "SCREENNAME")
	private String screenname;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "ORGANISATION")
	private String organisation;

	@Email
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PREFERREDLOCALE")
	private String preferredLocale;

	@Column(name = "NOTIFYAPPOINTMENTMAIL")
	private boolean notifyAppointmentMail;
	
	@Column(name = "NOTIFYAPPOINTMENTBROWSER")
	private boolean notifyAppointmentBrowser;
	
	@Column(name = "NOTIFYONEVENTS")
	private boolean notifyOnEvents;
	
	@CollectionOfElements(targetElement = String.class)
	private List<String> notifyEvents;

	@UserRoles
	@ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
	@JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "rolename"))
	public Set<UserRole> roles;

	@OneToMany(mappedBy = "user")
	private Set<Trial> trials;

	@OneToMany(mappedBy = "user")
	private Set<Participation> participations;

	@ManyToMany(mappedBy = "users")
	private List<OrgGroup> groups;

	public User() {
	}

	public User(String username) {
		super();
		this.username = username;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String field) {
		this.organisation = field;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Set<UserRole> getRoles() {

		if (roles == null)
			roles = new HashSet<UserRole>();

		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public Set<Trial> getTrials() {
		if (trials == null)
			trials = new HashSet<Trial>();
		return trials;
	}

	public void setTrials(Set<Trial> trials) {
		this.trials = trials;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public Set<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(Set<Participation> participations) {
		this.participations = participations;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getScreenname() {
		return screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	public List<OrgGroup> getGroups() {
		if (groups == null)
			groups = new LinkedList<OrgGroup>();
		return groups;
	}

	public void setGroups(List<OrgGroup> groups) {
		this.groups = groups;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreferredLocale() {
		return preferredLocale;
	}

	public void setPreferredLocale(String preferredLocale) {
		this.preferredLocale = preferredLocale;
	}
	
	public boolean getNotifyAppointmentMail() {
		return notifyAppointmentMail;
	}
	
	public void setNotifyAppointmentMail(boolean notifyAppointmentMail) {
		this.notifyAppointmentMail = notifyAppointmentMail;
	}
	
	public boolean getNotifyAppointmentBrowser() {
		return notifyAppointmentBrowser;
	}
	
	public void setNotifyAppointmentBrowser(boolean notifyAppointmentBrowser) {
		this.notifyAppointmentBrowser = notifyAppointmentBrowser;
	}
	
	public boolean isNotifyOnEvents() {
		return notifyOnEvents;
	}
	
	public boolean getNotifyOnEvents() {
		return notifyOnEvents;
	}
	
	public void setNotifyOnEvents(boolean notifyOnEvents) {
		this.notifyOnEvents = notifyOnEvents;
	}
	
	public List<String> getNotifyEvents() {
		return notifyEvents;
	}
	
	public void setNotifyEvents(List<String> notifyEvents) {
		this.notifyEvents = notifyEvents;
	}
	
	public void addNotifyEvent(String eventType) {
		notifyEvents.add(eventType);
	}
	
	public void removeNotifyEvent(String eventType) {
		notifyEvents.remove(eventType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public String getName() {
		return username;
	}

	@Override
	public int hashCode() {
		return username != null ? username.hashCode() : super.hashCode();
	}

	@Override
	public String toString() {
		return username;
	}
}
