package dst1.model.hardware;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import dst1.helper.MathHelper;
import dst1.model.Membership;
import dst1.validator.CPUs;

@Entity
public class Grid implements Serializable{
	private static final long serialVersionUID = -3428894900597889051L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true)
	private String name;
	private String location;
	private BigDecimal costsPerCPUMinute;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="membership_grid_id")
	private Set<Membership> memberships;
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="cluster_grid_id")
	@CPUs(min=1,max=64,message="CPU Anzahl liegt au�erhalb des gueltigen Bereiches von 1-64")
	private Set<Cluster> cluster;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public BigDecimal getCostsPerCPUMinute() {
		return costsPerCPUMinute;
	}
	public void setCostsPerCPUMinute(BigDecimal costsPerCPUMinute) {
		this.costsPerCPUMinute = costsPerCPUMinute;
	}
	public void setMemberships(Set<Membership> memberships) {
		this.memberships = memberships;
	}
	public Set<Membership> getMemberships() {
		if(memberships == null)
			memberships = new HashSet<Membership>();
		
		return memberships;
	}
	public void setCluster(Set<Cluster> cluster) {
		this.cluster = cluster;
	}
	public Set<Cluster> getCluster() { 
		if(cluster == null)
			cluster = new HashSet<Cluster>();
		return cluster;
	}
	
	@Override
	public String toString() {
		String id_out = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
		
		return id_out+"name:"+name+", loc:"+location+", cost/min:"+MathHelper.round(costsPerCPUMinute,2)+", { memb.: "+getMemberships().size()+"}";
	}
}
