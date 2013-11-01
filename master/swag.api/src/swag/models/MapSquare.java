package swag.models;

import javax.persistence.*;

import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class MapSquare implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne()
	private Base base;

	@OneToMany(mappedBy = "beach")
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Troop, Long> stationedTroops = new HashMap<Troop, Long>();

	@Id
	@GeneratedValue
	private Long id;

	private Integer x;

	private Integer y;

	@Enumerated(EnumType.STRING)
	private Landscape landscape;

	public MapSquare() {
	}

	public void setBase(Base base) {
		this.base = base;

	}

	public Base getBase() {
		return base;
	}

	public void setStationedTroops(Map<Troop, Long> stationedTroops) {
		this.stationedTroops = stationedTroops;

	}

	public Map<Troop, Long> getStationedTroops() {
		return stationedTroops;
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setX(Integer x) {
		this.x = x;

	}

	public Integer getX() {
		return x;
	}

	public void setY(Integer y) {
		this.y = y;

	}

	public Integer getY() {
		return y;
	}

	public void setLandscape(Landscape landscape) {
		this.landscape = landscape;

	}

	public Landscape getLandscape() {
		return landscape;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof MapSquare) {
			return this.getId().equals(((MapSquare) cmp).getId());
		}
		return false;
	}
}
