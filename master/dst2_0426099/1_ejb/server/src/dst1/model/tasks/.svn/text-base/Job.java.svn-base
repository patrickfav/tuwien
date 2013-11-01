package dst1.model.tasks;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import dst1.model.hardware.Computer;
import dst1.model.user.User;


@Entity
public class Job implements Serializable{
	private static final long serialVersionUID = 378193497547886423L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="jobSequencer")
	private Long id;

	private boolean isPaid;
	
	@ManyToOne
	@JoinColumn(name="person_id", updatable = false, insertable = false)
	private User creator;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Environment environment;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="execution_id")
	private Execution execution;
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getNumCPUs() {
		int out = 0;
		if(execution != null && execution.getComputer() != null) {
			for(Computer c:execution.getComputer())
				out += c.getCpus();
		}
		return out;
	}

	public Integer getExecutionTime() {
		long out = 0;
		if(execution != null && execution.getStart() != null && execution.getEnd() != null) {
			out = execution.getEnd().getTime() -execution.getStart().getTime();
		}
		return (int) out;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public void setExecution(Execution execution) {
		this.execution = execution;
	}

	public Execution getExecution() {

		return execution;
	}
	
	@Override
	public String toString() {
		String id_out = "";
		String creatorId = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
		
		if(creator != null)
			creatorId= String.valueOf(creator.getId());
		
		return id_out+"paid:"+isPaid+", numCPUs:"+getNumCPUs()+", execTime:"+getExecutionTime()+", {user ["+creatorId+"]}, {env:"+environment+"}" ;
	}
	
}
