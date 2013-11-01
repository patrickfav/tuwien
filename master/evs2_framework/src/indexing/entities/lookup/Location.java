package indexing.entities.lookup;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Location {
	
	public Location(){
		
	}
	
	private String pkPath;
	
	public Location(String address,String pkPath){
		this.address = address;
		this.pkPath = pkPath;
	}
	
	private String address;

	@XmlElement
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPkPath(String pkPath) {
		this.pkPath = pkPath;
	}
	@XmlElement
	public String getPkPath() {
		return pkPath;
	}
}
