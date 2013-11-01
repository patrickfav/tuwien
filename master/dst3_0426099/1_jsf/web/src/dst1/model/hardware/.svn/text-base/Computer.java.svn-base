package dst1.model.hardware;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import dst1.model.tasks.Execution;

@Entity
public class Computer implements Serializable{
	private static final long serialVersionUID = -674476170468307312L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="computerSequencer")
	private Long id;
	
	@Size(min = 5, max = 25)
	private String name;
	
	private Integer cpus;
	@Pattern(regexp = "[A-Z]{3}\\-[A-Z]{3}\\@[0-9]{4,}$")
	private String location;
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = Execution.class)
	@JoinTable(
        name="execution_computer",
        joinColumns=@JoinColumn(name="computer_id"),
        inverseJoinColumns=@JoinColumn(name="execution_id")
    )
	private Set<Execution> executions;
	
	@ManyToOne
	@JoinColumn(name="cluster_id", updatable = false, insertable = false)
	private Cluster cluster;
	
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
	public Integer getCpus() {
		return cpus;
	}
	public void setCpus(Integer cpus) {
		this.cpus = cpus;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getCreation() {
		return creation;
	}
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setExecutions(Set<Execution> executions) {
		this.executions = executions;
	}
	public Set<Execution> getExecutions() {
		if(executions == null)
			executions = new HashSet<Execution>();
		return executions;
	}
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	public Cluster getCluster() {
		return cluster;
	}
	
	@Override
	public String toString() {
		String id_out = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
	
		return id_out+"name:"+name+", cpus:"+cpus+", loc:"+location+", creat:"+creation+", lastUpd:"+lastUpdate+",{cluster["+cluster.getId()+"]: "+cluster.getName()+"}, {exec:"+getExecutions().size()+"}";
	}
}
