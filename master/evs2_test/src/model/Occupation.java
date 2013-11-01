package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import validator.annotations.FromDate;
import validator.annotations.SkillIntersection;
import validator.annotations.ToDate;
import annotations.RestSearchable;
import annotations.RestService;
import constants.ContentType;

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@RestService(contentType = ContentType.JSON)
@XmlRootElement
@SkillIntersection
@FromDate
@ToDate
public class Occupation implements Serializable{
	
	private static final long serialVersionUID = -8938002723670236748L;
	//public static final int HASH_SEED = 23;
	//private static final int fODD_PRIME_NUMBER = 37;
	
	/*
	@EmbeddedId
	private OccupationPk id;
	*/
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@RestSearchable
	private String position;
	
	@Column(name="fromDate")
	private Date from;
	
	@Column(name="toDate")
	private Date to;
	
	/*@ManyToOne
	@JoinColumn(name="person_fk", insertable=false, updatable=false)
	private Person person;*/
	
	@ManyToOne
	@JoinColumn(name="company_fk", insertable=false, updatable=false)
	private Company company;
	
	/*
	@XmlElement
	public OccupationPk getId() {
		return id;
	}
	public void setId(OccupationPk id) {
		this.id = id;
	}*/
	
	@XmlElement
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	@XmlElement
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	
	@XmlElement
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	
	/*@XmlElement
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}*/
	
	
	@XmlElement
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
/*
	@Embeddable
	public static class OccupationPk implements Serializable{
		private static final long serialVersionUID = -988828483729523319L;
		
		private long personId;
		private long companyId;
		
		public OccupationPk() {
			
		}
		
		public OccupationPk(long personId, long companyId) {
			super();
			this.personId = personId;
			this.companyId = companyId;
		}

		public long getPersonId() {
			return personId;
		}

		public void setPersonId(long personId) {
			this.personId = personId;
		}

		public long getCompanyId() {
			return companyId;
		}

		public void setCompanyId(long companyId) {
			this.companyId = companyId;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (!(obj instanceof OccupationPk))
				return false;
			OccupationPk pk = (OccupationPk) obj;
			if (personId != pk.personId)
				return false;
			if (companyId != pk.companyId)
				return false;
			return true;
		}
		
		@Override
		public int hashCode() {
			return (HASH_SEED*fODD_PRIME_NUMBER)+(int) personId + (int) companyId;
			
		}
		
		@Override
		public String toString() {
			return "PK: ("+personId+","+companyId+")";
		}
		
	}
	*/
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	
	
}
