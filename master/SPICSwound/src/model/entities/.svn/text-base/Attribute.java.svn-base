package entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import entities.formelement.FormElement;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="ATTRIBUTE")
public class Attribute implements Serializable, Comparable<Attribute> {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	@Id @GeneratedValue
    @Column(name="ATTRIBUTE_ID")
    private Long id;
	
	@Column(name="NAME", nullable=false)
    private String name;
	
	@Column(name="DESCRIPTION")
    private String description;
	
	@Column(name="RECOMMENDED")
	private Boolean recommended = false;
	
	@Column(name="SORT")
	private Integer sort;

	@Column(name="UNIT")
	private String unit;
	
	@OneToOne(targetEntity=FormElement.class, optional=false, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	private FormElement formElement;
	
	@OneToOne(cascade=CascadeType.ALL, optional=true)
	private Image imageComment;
	
	@XmlTransient
	@ManyToOne()
	@JoinColumn(name="ATTRIBUTEGROUP_ID")
	private AttributeGroup attributeGroup;
	
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

	public Boolean getRecommended() {
		return recommended;
	}

	public void setRecommended(Boolean recommended) {
		this.recommended = recommended;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public FormElement getFormElement() {
		return formElement;
	}

	public void setFormElement(FormElement formElement) {
		this.formElement = formElement;
	}

	public AttributeGroup getAttributeGroup() {
		return attributeGroup;
	}

	public void setAttributeGroup(AttributeGroup attributeGroup) {
		this.attributeGroup = attributeGroup;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Image getImageComment() {
		return imageComment;
	}

	public void setImageComment(Image imageComment) {
		this.imageComment = imageComment;
	}

	public int compareTo(Attribute other) {
		return this.sort.compareTo(other.getSort());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Attribute other = (Attribute) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Attribute duplicate() {
		Attribute dup = new Attribute();
		dup.setName(this.name);
		dup.setDescription(this.description);
		dup.setRecommended(this.recommended);
		dup.setSort(this.sort);
		dup.setUnit(this.unit);
		dup.setFormElement(formElement == null ? null : formElement.duplicate());
		return dup;
	}
	

	
}
