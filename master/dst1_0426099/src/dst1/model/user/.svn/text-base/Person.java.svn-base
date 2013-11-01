package dst1.model.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;



@Entity
/*
 * 	http://java.sys-con.com/node/286901
 * 3 inheritance types: Single Table : all column collapsed in a single table
 * 						Joined Table : splittet in 2 tables
 * 						TABLE_PER_CLASS : just not recommended
 */
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable{
	private static final long serialVersionUID = -1003548051227335221L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String firstName;
	private String lastName;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Address address;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Address getAddress() {
		return address;
	}
	
	@Override
	public String toString() {
		String id_out = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
		
		return id_out+firstName+" "+lastName+", {Address:"+address.toString()+"}";
	}
}
