package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.seam.annotations.security.permission.PermissionAction;
import org.jboss.seam.annotations.security.permission.PermissionDiscriminator;
import org.jboss.seam.annotations.security.permission.PermissionRole;
import org.jboss.seam.annotations.security.permission.PermissionTarget;
import org.jboss.seam.annotations.security.permission.PermissionUser;

@Entity
@Table(name = "SPICSUSERPERMISSION")
public class UserPermission implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	public Integer id;

	@PermissionUser
	@PermissionRole
	public String recipient;
	
	@PermissionTarget
	public String target;
	
	@PermissionAction
	@Column(length=700)
	public String action;
	
	@PermissionDiscriminator
	public String discriminator;

	public UserPermission() {
		super();
	}

	public UserPermission(String recipient, String target,
			String action, String discriminator) {
		super();
		this.recipient = recipient;
		this.target = target;
		this.action = action;
		this.discriminator = discriminator;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}
}
