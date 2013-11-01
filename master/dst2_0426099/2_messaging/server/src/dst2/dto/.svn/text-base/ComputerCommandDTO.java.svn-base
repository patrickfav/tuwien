package dst2.dto;

import java.io.Serializable;

public class ComputerCommandDTO implements Serializable{

	private static final long serialVersionUID = 2575722253767111929L;
	
	private String command;
	private String cluster;
	private String complexity;
	private Long id;
	
	public ComputerCommandDTO() {
		super();
	}
	
	public ComputerCommandDTO(String command, String cluster,
			String complexity, Long id) {
		super();
		this.command = command;
		this.cluster = cluster;
		this.complexity = complexity;
		this.id = id;
	}
	
	public ComputerCommandDTO(String command, String cluster,
			String complexity) {
		super();
		this.command = command;
		this.cluster = cluster;
		this.complexity = complexity;
		this.id = null;
	}
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getComplexity() {
		return complexity;
	}
	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "DTO: "+command+",cluster: "+cluster+",complex.: "+complexity+" , id:"+id;
	}
}
