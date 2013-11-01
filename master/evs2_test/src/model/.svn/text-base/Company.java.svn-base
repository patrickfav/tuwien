package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import annotations.RestSearchable;
import annotations.RestService;
import constants.ContentType;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@RestService(contentType = ContentType.JSON, name="companies", externalEncryption=true,internalEncryption=true)
@XmlRootElement
public class Company implements Serializable{
	
	private static final long serialVersionUID = 5792650561973091955L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@RestSearchable
	private String name;
	
	@Past
	private Date founded;
	
	@Past
	private Date abandoned;
	
	@RestSearchable
	private String fieldOfActivity;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private Set<Skill> skills; 
	
	/*
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="company_fk")
	private Set<Occupation> occupations; 
	*/
	
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
	
	@XmlElement
	public Date getFounded() {
		return founded;
	}
	public void setFounded(Date founded) {
		this.founded = founded;
	}
	
	@XmlElement
	public Date getAbandoned() {
		return abandoned;
	}
	public void setAbandoned(Date abandoned) {
		this.abandoned = abandoned;
	}
	
	@XmlElement
	public String getFieldOfActivity() {
		return fieldOfActivity;
	}
	public void setFieldOfActivity(String fieldOfActivity) {
		this.fieldOfActivity = fieldOfActivity;
	}
	
	@XmlElementWrapper(name="skillsElements")
	public Set<Skill> getSkills() {
		return skills;
	}
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	/*
	@XmlElementWrapper(name="occupationElements")
	public Set<Occupation> getOccupations() {
		
		return occupations;
	}
	public void setOccupations(Set<Occupation> occupations) {
		this.occupations = occupations;
	}*/
	
}
