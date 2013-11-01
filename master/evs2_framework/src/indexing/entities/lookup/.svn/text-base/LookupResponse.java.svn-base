package indexing.entities.lookup;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LookupResponse {

	private List<Location> locations;

	@XmlElementWrapper(name="locationsElements")
	@XmlElement(name="location")
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
}
