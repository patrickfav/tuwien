package dst1.model.hardware;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dst1.model.user.Admin;

@Entity
public class Cluster implements Serializable{
	private static final long serialVersionUID = 3415790040562477276L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="clusterSequencer")
	private Long id;
	
	@Column(unique=true)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastService;
	@Temporal(TemporalType.TIMESTAMP)
	private Date nextService;
	
	@ManyToOne
	@JoinColumn(name="cluster_grid_id", updatable = false, insertable = false)
	private Grid grid;
	
	@ManyToOne
	@JoinColumn(name="cluster_admin_id", updatable = false, insertable = false)
	private Admin admin;
	
	
	
	@ManyToMany(targetEntity=Cluster.class,cascade={CascadeType.ALL})
    @JoinTable(name="cluster_relations",joinColumns=@JoinColumn(name="owner_id"),inverseJoinColumns=@JoinColumn(name="child_id"))
	private Set<Cluster> clusterChildren;
	
	
	@ManyToMany(cascade = {CascadeType.REFRESH},mappedBy = "clusterChildren", targetEntity = Cluster.class )
	private Set<Cluster> clusterOwner;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="cluster_id")
	private Set<Computer> computer;
	
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

	public Date getLastService() {
		return lastService;
	}

	public void setLastService(Date lastService) {
		this.lastService = lastService;
	}

	public Date getNextService() {
		return nextService;
	}

	public void setNextService(Date nextService) {
		this.nextService = nextService;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Set<Cluster> getClusterChildren() {
		if(clusterChildren == null)
			clusterChildren = new HashSet<Cluster>();
		return clusterChildren;
	}

	public void setClusterChildren(Set<Cluster> clusterChildren) {
		this.clusterChildren = clusterChildren;
	}

	public Set<Cluster> getClusterOwner() {
		if(clusterOwner == null)
			clusterOwner = new HashSet<Cluster>();
		return clusterOwner;
	}

	public void setClusterOwner(Set<Cluster> clusterOwner) {
		this.clusterOwner = clusterOwner;
	}

	public void setComputer(Set<Computer> computer) {
		this.computer = computer;
	}

	public Set<Computer> getComputer() {
		if(computer == null)
			computer = new HashSet<Computer>();
		return computer;
	}
	
	@Override
	public String toString() {
		String id_out = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
	
		return id_out+"name:"+name+", last:"+lastService+", next:"+nextService+", {admin["+admin.getId()+"]: "+admin.getLastName()+"} {grid["+grid.getId()+"]: "+grid.getName()+"}, {computer:"+getComputer().size()+"} {owner:"+getClusterOwner().size()+"}, {child:"+getClusterChildren().size()+"}";
	}
}
