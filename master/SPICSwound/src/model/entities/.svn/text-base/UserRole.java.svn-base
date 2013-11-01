package entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.jboss.seam.annotations.security.management.RoleGroups;
import org.jboss.seam.annotations.security.management.RoleName;

@Entity
@Table(name = "SPICSUSERROLE")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@RoleName
	@Id
	@Column(name = "ROLENAME")
	public String rolename;

	@RoleGroups
	@ManyToMany(targetEntity = UserRole.class)
	@JoinTable(name = "RoleGroups", 
			joinColumns = @JoinColumn(name = "rolename"), 
			inverseJoinColumns = @JoinColumn(name = "groupid"))
	private Set<UserRole> groups;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Set<UserRole> getGroups() {
		return groups;
	}

	public void setGroups(Set<UserRole> groups) {
		this.groups = groups;
	}	
}
