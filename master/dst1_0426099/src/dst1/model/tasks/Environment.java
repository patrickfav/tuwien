package dst1.model.tasks;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Environment implements Serializable{

	private static final long serialVersionUID = -8735885776651966008L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String workflow;
	
	@ElementCollection
	private List<String> params;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getWorkflow() {
		return workflow;
	}
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	
	@Override
	public String toString() {
		String id_out = "";
		
		if(id != null)
			id_out= "[id:"+id+"] ";
	
		return id_out+"workflow:"+workflow+", params:"+Arrays.toString(params.toArray());
	}
}
