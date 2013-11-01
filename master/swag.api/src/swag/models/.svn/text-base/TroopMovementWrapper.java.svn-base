package swag.models;

import javax.persistence.*;

import java.util.*;

import swag.helper.TroopCountWrapper;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class TroopMovementWrapper implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Troop, Long> troops = new HashMap<Troop, Long>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date start_time;

	@Temporal(TemporalType.TIMESTAMP)
	private Date end_time;

	private User user;

	private MapSquare destination;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne()
	private GameMap map;

	public TroopMovementWrapper() {
	}
	
	public List<TroopCountWrapper> getAsWrapperList() {
		List<TroopCountWrapper> list = new ArrayList<TroopCountWrapper>();
		for(Troop t:troops.keySet()) {
			list.add(new TroopCountWrapper(t,troops.get(t)));
		}
		Collections.sort(list);
		return list;
	}
	
	public void setTroops(Map<Troop, Long> troops) {
		this.troops = troops;

	}

	public Map<Troop, Long> getTroops() {
		return troops;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;

	}

	public Date getStart_time() {
		return start_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;

	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setUser(User user) {
		this.user = user;

	}

	public User getUser() {
		return user;
	}

	public void setDestination(MapSquare destination) {
		this.destination = destination;

	}

	public MapSquare getDestination() {
		return destination;
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setMap(GameMap map) {
		this.map = map;

	}

	public GameMap getMap() {
		return map;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof TroopMovementWrapper) {
			return this.getId().equals(((TroopMovementWrapper) cmp).getId());
		}
		return false;
	}
}
