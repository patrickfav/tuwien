package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class MapSquare {

	@OneToOne()
	private Base base;

	public void setBase(Base base) {
		this.base = base;
	}

	public Base getBase() {
		return base;
	}

	@OneToMany()
	private List<Troop> troops = new LinkedList<Troop>();

	public void setTroops(List<Troop> troops) {
		this.troops = troops;
	}

	public List<Troop> getTroops() {
		return troops;
	}

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	private Integer x;

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getX() {
		return x;
	}

	private Integer y;

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getY() {
		return y;
	}

}
