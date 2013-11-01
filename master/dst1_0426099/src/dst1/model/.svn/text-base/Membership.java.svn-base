package dst1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import dst1.helper.MathHelper;
import dst1.model.hardware.Grid;
import dst1.model.user.User;

@Entity
public class Membership implements Serializable{
	private static final long serialVersionUID = 5419011663607826958L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date registration;
	private Double discount;
	
	@ManyToOne
	@JoinColumn(name="membership_grid_id", updatable = false, insertable = false)
	private Grid grid;
	@ManyToOne
	@JoinColumn(name="membership_user_id", updatable = false, insertable = false)
	private User user;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getRegistration() {
		return registration;
	}
	public void setRegistration(Date registration) {
		this.registration = registration;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Grid getGrid() {
		return grid;
	}
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		String id_out = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
		
		return id_out+"discount:"+MathHelper.round(discount,2)+", date:"+registration+", {u["+user.getId()+"]: "+user.getLastName()+"}, {g["+grid.getId()+"]: "+grid.getName()+"}";
	}
	
}
