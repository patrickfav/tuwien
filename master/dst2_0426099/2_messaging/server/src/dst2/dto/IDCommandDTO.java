package dst2.dto;

import java.io.Serializable;

public class IDCommandDTO implements Serializable{

	private static final long serialVersionUID = 4418184865831022971L;
	
	private String command;
	private long id;
	
	public IDCommandDTO(String command, long id) {
		super();
		this.command = command;
		this.id = id;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "DTO: "+command+", id:"+id;
	}
}
