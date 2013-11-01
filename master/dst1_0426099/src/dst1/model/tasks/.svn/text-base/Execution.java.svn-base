package dst1.model.tasks;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import dst1.model.hardware.Computer;

@Entity
public class Execution implements Serializable{
	private static final long serialVersionUID = -3389178244171482708L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date start;
	private Date end;
	
	@Enumerated(EnumType.STRING)
	private JobStatus status;
	
	@OneToOne(cascade=CascadeType.ALL,mappedBy="execution")
	private Job job;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},mappedBy = "executions", targetEntity = Computer.class)
	private Set<Computer> computer;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public JobStatus getStatus() {
		return status;
	}
	public void setStatus(JobStatus status) {
		this.status = status;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}

	
	public void setComputer(Set<Computer> computer) {
		this.computer = computer;
	}
	public Set<Computer> getComputer() {
		if(computer == null)
			computer = new HashSet<Computer>();
		return computer;
	}
	public enum JobStatus {
		SCHEDULED,RUNNING,FAILED,FINISHED
	}
	
	
	@Override
	public String toString() {
		String id_out = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
		
		return id_out+"start:"+start.getTime()+", end:"+end.getTime()+", status:"+ status+", {job: ["+job.getId()+"]}";
	}
}
