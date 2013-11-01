package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Building {

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	private Integer level;

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return level;
	}

	@OneToOne()
	private BuildingType type;

	public void setType(BuildingType type) {
		this.type = type;
	}

	public BuildingType getType() {
		return type;
	}

}
