package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="ATTRIBUTEGROUP")
public class AttributeGroup implements Serializable, Comparable<AttributeGroup> {

	private static final long serialVersionUID = 1L;
	
	@XmlTransient
	@Id @GeneratedValue
	@Column(name="ATTRIBUTEGROUP_ID")
	private Long id;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SECURE")
	private Boolean secure = false;

	@Column(name="SORT")
	private Integer sort;
	
	@XmlElement(name="attribute")
	@OneToMany(mappedBy="attributeGroup", cascade={CascadeType.ALL})
	@OrderBy("sort")
	private List<Attribute> attributes;
	
	@XmlTransient
	@ManyToOne
	@JoinColumn(name="TRIALFORM_ID")
	private TrialForm trialForm;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
// TODO cleanup
//	public Boolean getTrialSpecific() {
//		return trialSpecific;
//	}
//
//	public void setTrialSpecific(Boolean trialSpecific) {
//		this.trialSpecific = trialSpecific;
//	}

	public List<Attribute> getAttributes() {
		if(attributes == null)
			attributes = new LinkedList<Attribute>();
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public TrialForm getTrialForm() {
		return trialForm;
	}

	public void setTrialForm(TrialForm trialForm) {
		this.trialForm = trialForm;
	}

	public Boolean getSecure() {
		return secure;
	}

	public void setSecure(Boolean secure) {
		this.secure = secure;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public int compareTo(AttributeGroup other) {
		
		return this.sort.compareTo(other.getSort());
	}

	public AttributeGroup duplicate() {
		AttributeGroup dup = new AttributeGroup();
		dup.setName(this.name);
		dup.setDescription(this.description);
		dup.setSecure(this.secure);
		dup.setSort(this.sort);
		
		for(Attribute a : this.getAttributes()) {
			Attribute dupA = a.duplicate();
			dupA.setAttributeGroup(dup);
			dup.getAttributes().add(dupA);
		}
		
		return dup;
	}
}
