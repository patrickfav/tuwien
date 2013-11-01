package model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import annotations.RestSearchable;
import annotations.RestService;
import constants.ContentType;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@RestService(contentType = ContentType.XML)
@XmlRootElement
public class Skill implements Serializable{
	private static final long serialVersionUID = 102687849052337890L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@RestSearchable
	private String name;
	
	@XmlAttribute
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@XmlElement
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
