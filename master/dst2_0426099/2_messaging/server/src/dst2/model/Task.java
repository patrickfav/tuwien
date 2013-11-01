package dst2.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task implements Serializable{
	private static final long serialVersionUID = -3307750946784395947L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long jobId;
	private String ratedBy;
	
	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	
	@Enumerated(EnumType.STRING)
	private TaskComplexity complexity;
	
	public Task() {
		super();
	}
	
	public Task(Long jobId, String ratedBy, TaskStatus status,
			TaskComplexity complexity) {
		super();
		this.jobId = jobId;
		this.ratedBy = ratedBy;
		this.status = status;
		this.complexity = complexity;
	}
	
	public Task(Long id, Long jobId, String ratedBy, TaskStatus status,
			TaskComplexity complexity) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.ratedBy = ratedBy;
		this.status = status;
		this.complexity = complexity;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getRatedBy() {
		return ratedBy;
	}
	public void setRatedBy(String ratedBy) {
		this.ratedBy = ratedBy;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	public TaskComplexity getComplexity() {
		return complexity;
	}
	public void setComplexity(TaskComplexity complexity) {
		this.complexity = complexity;
	}
	
	/* ******************************** ENUMS */
	
	public static enum TaskStatus {
		ASSIGNED,READY_FOR_PROCESSING,PROCESSING_NOT_POSSIBLE,PROCESSED
	}
	public static enum TaskComplexity {
		UNRATED,EASY,CHALLENGING
	}
}


