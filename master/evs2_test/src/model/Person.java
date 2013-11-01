package model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


import annotations.RestSearchable;
import annotations.RestService;
import constants.ContentType;


@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@RestService(contentType = ContentType.XML,externalEncryption=true,internalEncryption=true)
@XmlRootElement
public class Person implements Serializable{
	
	private static final long serialVersionUID = 1112288172137081822L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@RestSearchable
	private String prename;
	@RestSearchable
	private String surname;
	@RestSearchable
	private String descrption;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<Skill> skills; 
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="person_fk")
	private Set<Occupation> occupations;
	
	@XmlAttribute
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@XmlElement
	public String getPrename() {
		return prename;
	}
	public void setPrename(String prename) {
		this.prename = prename;
	}
	
	@XmlElement
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@XmlElement
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	
	@XmlElementWrapper(name="skillsElements")
	public Set<Skill> getSkills() {
		return skills;
	}
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	
	@XmlElementWrapper(name="occupationElements")
	public Set<Occupation> getOccupations() {
		
		return occupations;
	}
	public void setOccupations(Set<Occupation> occupations) {
		this.occupations = occupations;
	}
	
	
	
}
