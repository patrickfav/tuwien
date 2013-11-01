package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Property")
public class Property {
	
	@Id
	@Column(name="pkey")
	private String key;
	
	@Column(nullable=false)
	private String value;
	
	@Column(nullable=false)
	private Boolean required;
	
	@Column(nullable=true)
	private String regex;

	public Property() {
		super();
	}
	
	public Property(String key, String value, Boolean required) {
		super();
		this.key = key;
		this.value = value;
		this.required = required;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	@Override
	public String toString(){
		return "key: " + key + " value: " + value;
	}
}
