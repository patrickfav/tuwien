package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class BaseSquare {

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@OneToOne()
	private Building building;

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Building getBuilding() {
		return building;
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
