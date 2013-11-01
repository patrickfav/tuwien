package util.charting;

import java.io.Serializable;

public class NatAttributeIdentifier implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public String groupName;
	public String attributeName;
	
	// the id of the attribute in the db.
	// not used for matching.
	// perhaps set during init by ChartRepository
	public Long id;

	public NatAttributeIdentifier() {
	}

	public NatAttributeIdentifier(String groupName, String attributeName) {
		this.groupName = groupName;
		this.attributeName = attributeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result
				+ ((groupName == null) ? 0 : groupName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final NatAttributeIdentifier other = (NatAttributeIdentifier) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (groupName == null) {
			if (other.groupName != null)
				return false;
		} else if (!groupName.equals(other.groupName))
			return false;
		return true;
	}
}
