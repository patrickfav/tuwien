package entities.formelement;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import entities.constraint.Constraint;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "FORMELEMENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "FORMELEMENTTYPE", discriminatorType = DiscriminatorType.STRING)
public class FormElement implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	@Id
	@GeneratedValue
	@Column(name = "FORMELEMENT_ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "DATATYPE")
	private DATATYPE dataType;

	@Enumerated(EnumType.STRING)
	@Column(name = "FORMELEMENTTYPE", insertable = false, updatable = false, nullable = false)
	private FORMELEMENTTYPE type;

	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REMOVE })
	private Constraint constraints;

	public DATATYPE getDataType() {
		return dataType;
	}

	public void setDataType(DATATYPE dataType) {
		this.dataType = dataType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FORMELEMENTTYPE getType() {
		return type;
	}

	public void setType(FORMELEMENTTYPE type) {
		this.type = type;
	}

	public Constraint getConstraint() {
		return constraints;
	}

	public void setConstraint(Constraint c) {
		this.constraints = c;
	}

	public FormElement duplicate() {
		FormElement dup = new FormElement();
		dup.setDataType(this.dataType);
		dup.setType(this.type);
		dup.setConstraint(this.constraints == null ? null : this.constraints
				.duplicate());
		return dup;
	}

	/**
	 * override in concrete classes to specify which datatypes are possible for
	 * the Formelement
	 * 
	 * @return DATATYPE[] - an Array of valid datatypes
	 */
	public DATATYPE[] getValidDatatypes() {
		return DATATYPE.values();
	}

	/**
	 * override in concrete classes if the given FormElement does not support
	 * the specification of a unit field (or it does not make sense)
	 * 
	 * @return - true if the FormElement supports a unit specification, false otherwise
	 */
	public boolean hasUnit() {
		return true;
	}

}
