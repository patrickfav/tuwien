package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class Building implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private boolean underConstruction;

	private Integer level;

	@OneToOne()
	private BuildingType type;

	@OneToOne()
	private BaseSquare base_square;

	public Building() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setLevel(Integer level) {
		this.level = level;

	}

	public Integer getLevel() {
		return level;
	}

	public void setType(BuildingType type) {
		this.type = type;

	}

	public BuildingType getType() {
		return type;
	}

	public void setBase_square(BaseSquare base_square) {
		this.base_square = base_square;

	}

	public BaseSquare getBase_square() {
		return base_square;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof Building) {
			return this.getId().equals(((Building) cmp).getId());
		}
		return false;
	}

	public void setUnderConstruction(boolean underConstruction) {
		this.underConstruction = underConstruction;
	}

	public boolean isUnderConstruction() {
		return underConstruction;
	}
}