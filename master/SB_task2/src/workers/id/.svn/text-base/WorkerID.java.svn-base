package workers.id;

import java.io.Serializable;
import java.util.UUID;

public class WorkerID implements Serializable{

	private static final long serialVersionUID = -5655291858676949700L;
	
	private String id;
	private UUID uuid;
	
	public WorkerID(String id) {
		this.id = id;
		setUuid(UUID.randomUUID());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}
	
}
