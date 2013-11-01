package swag.models;

import javax.persistence.*;

import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class MilitaryBuilding extends BuildingType implements Serializable {

	private static final long serialVersionUID = 1L;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<MilitaryType> createableMilitary = new LinkedList<MilitaryType>();

	public MilitaryBuilding() {
	}

	public void setCreateableMilitary(List<MilitaryType> createableMilitary) {
		this.createableMilitary = createableMilitary;

	}

	public List<MilitaryType> getCreateableMilitary() {
		return createableMilitary;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof MilitaryBuilding) {
			return this.getId().equals(((MilitaryBuilding) cmp).getId());
		}
		return false;
	}
}
