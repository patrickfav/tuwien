package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class Upgrade implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne()
	private User user;

	@OneToOne()
	private GameMap map;

	private Integer level;

	private UpgradeType type;

	@Id
	@GeneratedValue
	private Long id;

	public Upgrade() {
	}

	public void setUser(User user) {
		this.user = user;

	}

	public User getUser() {
		return user;
	}

	public void setMap(GameMap map) {
		this.map = map;

	}

	public GameMap getMap() {
		return map;
	}

	public void setLevel(Integer level) {
		this.level = level;

	}

	public Integer getLevel() {
		return level;
	}

	public void setType(UpgradeType type) {
		this.type = type;

	}

	public UpgradeType getType() {
		return type;
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof Upgrade) {
			return this.getId().equals(((Upgrade) cmp).getId());
		}
		return false;
	}
}
