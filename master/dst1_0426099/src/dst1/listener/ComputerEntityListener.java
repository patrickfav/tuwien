package dst1.listener;

import java.util.Date;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import dst1.model.hardware.Computer;

public class ComputerEntityListener {
	
	@PostPersist
	public void setCreationBeforePresistingComputer(Computer c) {
		c.setCreation(new Date());
		c.setLastUpdate(new Date());
	}
	
	@PostUpdate
	public void setUpdateBeforeUdpateingComputer(Computer c) {
		c.setLastUpdate(new Date());
	}
	
}
