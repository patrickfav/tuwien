package swag.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import swag.models.enums.ResourceType;

@Entity
public class UserGameMap  implements Serializable {

	private static final long serialVersionUID = -7646208098804633253L;

	@Id
	@GeneratedValue
	private Long id;
	
	private GameMap map;
	
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="userGameMap_fk")
	private User user;
	
	@ElementCollection(fetch = FetchType.EAGER)
	//@MapKey
	private Map<ResourceType,Long> resourceStock = new HashMap<ResourceType,Long>();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastResourceUpdate;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Base> bases = new ArrayList<Base>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public Map<ResourceType, Long> getResourceStock() {
		return resourceStock;
	}

	public void setResourceStock(Map<ResourceType, Long> resourceStock) {
		this.resourceStock = resourceStock;
	}

	public void setLastResourceUpdate(Date lastResourceUpdate) {
		this.lastResourceUpdate = lastResourceUpdate;
	}

	public Date getLastResourceUpdate() {
		return lastResourceUpdate;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setBases(List<Base> bases) {
		this.bases = bases;
	}

	public List<Base> getBases() {
		return bases;
	}
	
	
}
