package dst1.model.hardware;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityListeners;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import dst1.listener.ComputerEntityListener;
import dst1.model.tasks.Execution;


@NamedQueries({
	  @NamedQuery(name="getTotalUsageOfAllComputers",
	              query="select new dst1.helper.ComputerExecutionWrapper(sum(exec.end - exec.start),c) " +
	              		"from dst1.model.hardware.Computer c join c.executions as exec " +
	              		"where c.location like :loc group by c.id" )
	})
@EntityListeners(ComputerEntityListener.class)
public class Computer implements Serializable{
	private static final long serialVersionUID = -674476170468307312L;
	
	private Long id;
	@Size(min = 5, max = 25)
	private String name;
	
	private Integer cpus;
	@Pattern(regexp = "[A-Z]{3}\\-[A-Z]{3}\\@[0-9]{4,}$")
	private String location;
	@Past
	private Date creation;
	@Past
	private Date lastUpdate;
	private Set<Execution> executions;
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
