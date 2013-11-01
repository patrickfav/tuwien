package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class Resource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private Double factor;

	@Enumerated(EnumType.STRING)
	private ResourceType type;

	public Resource() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setFactor(Double factor) {
		this.factor = factor;

	}

	public Double getFactor() {
		return factor;
	}

	public void setType(ResourceType type) {
		this.type = type;

	}

	public ResourceType getType() {
		return type;
	}

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof Resource) {
			return this.getId().equals(((Resource) cmp).getId());
		}
		return false;
	}
}
