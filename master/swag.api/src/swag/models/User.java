package swag.models;

import javax.persistence.*;
import java.util.*;
import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity(name = "swag_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String username;

	private String password;

	private String address;

	private Boolean login;

	@Temporal(TemporalType.TIMESTAMP)
	private Date loginDate;

	private String firstName;

	private String lastName;

	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER,mappedBy="user")
	private List<UserGameMap> userMaps = new ArrayList<UserGameMap>();
	
	private String mail;

	private int points;
	
	public User() {
	}

	public void setId(Long id) {
		this.id = id;

	}

	public Long getId() {
		return id;
	}

	public void setUsername(String username) {
		this.username = username;

	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) throws Exception {
		if (username == null)
			throw new Exception("username has to be set!");
		this.password = HashUtil.sha256(password, this.username);

	}

	public String getPassword() {
		return password;
	}

	public void setAddress(String address) {
		this.address = address;

	}

	public String getAddress() {
		return address;
	}

	public void setLogin(Boolean login) {
		this.login = login;

	}

	public Boolean getLogin() {
		return login;
	}


	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;

	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;

	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;

	}

	public String getLastName() {
		return lastName;
	}

	public void setMail(String mail) {
		this.mail = mail;

	}

	public String getMail() {
		return mail;
	}

	

	@Override
	public boolean equals(Object cmp) {
		if (cmp instanceof User) {
			return this.getId().equals(((User) cmp).getId());
		}
		return false;
	}

	public void setUserMaps(List<UserGameMap> userMaps) {
		this.userMaps = userMaps;
	}

	public List<UserGameMap> getUserMaps() {
		return userMaps;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}
