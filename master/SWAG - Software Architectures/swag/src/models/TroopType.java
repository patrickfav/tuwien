package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class TroopType {

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	private Integer strength;

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	public Integer getStrength() {
		return strength;
	}

	private Integer speed;

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getSpeed() {
		return speed;
	}

}
