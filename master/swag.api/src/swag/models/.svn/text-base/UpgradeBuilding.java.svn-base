package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class UpgradeBuilding extends BuildingType implements Serializable {

	private static final long serialVersionUID = 1L;

	@ElementCollection(fetch=FetchType.EAGER)
	private List<MilitaryType> upgradeableMilitary = new LinkedList<MilitaryType>();

	public UpgradeBuilding() {
	}

	public void setUpgradeableMilitary(List<MilitaryType> upgradeableMilitary) {
		this.upgradeableMilitary = upgradeableMilitary;

	}

	public List<MilitaryType> getUpgradeableMilitary() {
		return upgradeableMilitary;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof UpgradeBuilding) {
			return this.getId().equals(((UpgradeBuilding) cmp).getId());
		}
		return false;
	}
}
