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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import dst1.model.hardware.Computer;

@Entity
public class Execution implements Serializable{
	private static final long serialVersionUID = -3389178244171482708L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="execSequencer")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;
	
	@Enumerated(EnumType.STRING)
	private JobStatus status;
	
	@OneToOne(cascade={CascadeType.MERGE},mappedBy="execution")
	private Job job;
	
	@ManyToMany(cascade = {CascadeType.MERGE},mappedBy = "executions", targetEntity = Computer.class)
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
		String jobId="";
		Date startT = null;
		Date endT = null;
		
		if(id != null)
			id_out= "[id:"+id+"] ";
		
		if(start != null)
			startT = new Date(start.getTime());
		
		if(end != null)
			endT = new Date(end.getTime());
		
		if(job != null && job.getId() !=null)
			jobId=String.valueOf(job.getId());
		
		return id_out+"start:"+startT+", end:"+endT+", status:"+ status+", {job: ["+jobId+"]}";
	}
}
