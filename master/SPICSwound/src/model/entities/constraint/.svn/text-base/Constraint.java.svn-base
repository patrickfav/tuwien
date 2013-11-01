package entities.constraint;

import java.io.Serializable;

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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="SPICSCONSTRAINT")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="CONSTRAINTTYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class Constraint implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	@Id @GeneratedValue
    @Column(name="CONSTRAINT_ID")
    private Long id;
	
	//discriminator value
	@Enumerated(EnumType.STRING)
    @Column(name="CONSTRAINTTYPE", insertable=false, updatable=false)
	private CONSTRAINTTYPE constraintType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CONSTRAINTTYPE getConstraintType() {
		return constraintType;
	}

	public void setConstraintType(CONSTRAINTTYPE constraintType) {
		this.constraintType = constraintType;
	}
	
	public abstract String validate(Object o);

	public abstract Constraint duplicate();

	public abstract boolean isEmpty();	
	
}
