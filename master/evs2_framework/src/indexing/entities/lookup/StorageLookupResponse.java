package indexing.entities.lookup;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StorageLookupResponse {

	private List<Storage> storages;

	@XmlElementWrapper(name="storagesElements")
	@XmlElement(name="storage")
	public List<Storage> getStorages() {
		return storages;
	}
	public void setStorages(List<Storage> storages) {
		this.storages = storages;
	}
}
