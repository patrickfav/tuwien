package dst2.dto;

import java.io.Serializable;

public class TaskDTO implements Serializable{
	
	private static final long serialVersionUID = 459457626840280842L;
	
	public static final String ASSIGNED = "ASSIGNED";
	public static final String READY_FOR_PROCESSING = "READY_FOR_PROCESSING";
	public static final String PROCESSING_NOT_POSSIBLE = "PROCESSING_NOT_POSSIBLE";
	public static final String PROCESSED = "PROCESSED";
	
	public static final String UNRATED = "UNRATED";
	public static final String EASY = "EASY";
	public static final String CHALLENGING = "CHALLENGING";
	
	
	/*additional fields */
	private String origintator;
	private String additionalCommand;
	
	/* task fields */
	private Long id;
	private Long jobId;
	private String taskStatus;
	private String ratedBy;
	private String complexity;
	
	
	
	public TaskDTO(String origintator) {
		this.origintator = origintator;
	}
	
	public TaskDTO(Long jobId,String origintator) {
		super();
		this.origintator = origintator;
		this.jobId = jobId;
		taskStatus = ASSIGNED;
		complexity = UNRATED;
		this.ratedBy = "";
		this.additionalCommand = "";
	}
	
	public TaskDTO(String origintator, Long id, Long jobId, String taskStatus,
			String ratedBy, String complexity) {
		super();
		this.origintator = origintator;
		this.id = id;
		this.jobId = jobId;
		this.taskStatus = taskStatus;
		this.ratedBy = ratedBy;
		this.complexity = complexity;
		this.additionalCommand = "";
	}
	
	
	public String getOrigintator() {
		return origintator;
	}
	public void setOrigintator(String origintator) {
		this.origintator = origintator;
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
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getRatedBy() {
		return ratedBy;
	}
	public void setRatedBy(String ratedBy) {
		this.ratedBy = ratedBy;
	}
	public String getComplexity() {
		return complexity;
	}
	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}
	
	@Override
	public String toString() {
		String cmd = "";
		
		if(additionalCommand != null && !additionalCommand.equals("")) {
			cmd = ":"+additionalCommand;
		}
		
		return "["+origintator+cmd+"]: ("+id+"), jobId:"+jobId+", status:"+taskStatus+", ratedBy:"+ratedBy+", complexity:"+complexity;
	}

	public void setAdditionalCommand(String additionalCommand) {
		this.additionalCommand = additionalCommand;
	}

	public String getAdditionalCommand() {
		return additionalCommand;
	}
}
