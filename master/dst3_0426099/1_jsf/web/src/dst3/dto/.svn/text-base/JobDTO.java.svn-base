package dst3.dto;

import java.io.Serializable;
import java.util.Date;

import dst1.model.tasks.Job;

public class JobDTO implements Serializable {
	private static final long serialVersionUID = -1116359489466347188L;
	
	private long id;
	private Date start;
	private Date finish;
	private String username;
	
	public JobDTO() {
		
	}
	
	public JobDTO(Job j) {
		id=j.getId();
		start=j.getExecution().getStart();
		finish=j.getExecution().getEnd();
		username=j.getCreator().getUsername();
	}
	
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
	public Date getFinish() {
		return finish;
	}
	public void setFinish(Date finish) {
		this.finish = finish;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
