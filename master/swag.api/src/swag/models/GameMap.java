package swag.models;

import javax.persistence.*;

import java.util.*;

import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
@Cacheable
/*	@Cache(
		usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
		include = "non-lazy"
	)*/
public class GameMap implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(fetch = FetchType.EAGER)
	private List<MapSquare> squares = new LinkedList<MapSquare>();

	private String name;

	@ManyToOne()
	private Config config;

	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
	private List<TroopMovementWrapper> troops = new LinkedList<TroopMovementWrapper>();

	public GameMap() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setSquares(List<MapSquare> squares) {
		this.squares = squares;

	}

	public List<MapSquare> getSquares() {
		return squares;
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setConfig(Config config) {
		this.config = config;

	}

	public Config getConfig() {
		return config;
	}

	public void setTroops(List<TroopMovementWrapper> troops) {
		this.troops = troops;

	}

	public List<TroopMovementWrapper> getTroops() {
		return troops;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof GameMap) {
			return this.getId().equals(((GameMap) cmp).getId());
		}
		return false;
	}
}
