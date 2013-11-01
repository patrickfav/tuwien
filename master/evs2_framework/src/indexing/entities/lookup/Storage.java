package indexing.entities.lookup;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Storage {
	
	public Storage(){
		
	}
	
	public Storage(String address){
		this.address = address;
	}
	
	private String address;

	@XmlElement
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
