package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Resources {

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	private Integer countWood;

	public void setCountWood(Integer countWood) {
		this.countWood = countWood;
	}

	public Integer getCountWood() {
		return countWood;
	}

	private Integer countIron;

	public void setCountIron(Integer countIron) {
		this.countIron = countIron;
	}

	public Integer getCountIron() {
		return countIron;
	}

}
