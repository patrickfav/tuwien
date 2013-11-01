package util;

import java.io.Serializable;

public class ComponentMapKey implements Serializable, Comparable<ComponentMapKey> {

	private static final long serialVersionUID = 1L;

	private Long attributeId;

	private Long trialDataId;

	private String componentName;

	private Integer agSort;

	private Integer attribSort;

	public ComponentMapKey(Long attributeId, Long trialDataId,
			String componentName, Integer agSort, Integer attribSort) {
		super();
		this.attributeId = attributeId;
		this.trialDataId = trialDataId;
		this.componentName = componentName;
		this.agSort = agSort;
		this.attribSort = attribSort;
	}

	public ComponentMapKey(Long attributeId, Long trialDataId, Integer agSort,
			Integer attribSort) {
		super();
		this.attributeId = attributeId;
		this.trialDataId = trialDataId;
		this.agSort = agSort;
		this.attribSort = attribSort;
	}

	public static String generateKeyString(Long attributeId, Long trialDataId) {
		return attributeId + "_" + trialDataId;
	}
	
	public String getKeyString() {
		return generateKeyString(attributeId, trialDataId);
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((agSort == null) ? 0 : agSort.hashCode());
		result = PRIME * result
				+ ((attribSort == null) ? 0 : attribSort.hashCode());
		result = PRIME * result
				+ ((attributeId == null) ? 0 : attributeId.hashCode());
		result = PRIME * result
				+ ((componentName == null) ? 0 : componentName.hashCode());
		result = PRIME * result
				+ ((trialDataId == null) ? 0 : trialDataId.hashCode());
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
		final ComponentMapKey other = (ComponentMapKey) obj;
		if (agSort == null) {
			if (other.agSort != null)
				return false;
		} else if (!agSort.equals(other.agSort))
			return false;
		if (attribSort == null) {
			if (other.attribSort != null)
				return false;
		} else if (!attribSort.equals(other.attribSort))
			return false;
		if (attributeId == null) {
			if (other.attributeId != null)
				return false;
		} else if (!attributeId.equals(other.attributeId))
			return false;
		if (componentName == null) {
			if (other.componentName != null)
				return false;
		} else if (!componentName.equals(other.componentName))
			return false;
		if (trialDataId == null) {
			if (other.trialDataId != null)
				return false;
		} else if (!trialDataId.equals(other.trialDataId))
			return false;
		return true;
	}

	public Integer getAgSort() {
		return agSort;
	}

	public void setAgSort(Integer agSort) {
		this.agSort = agSort;
	}

	public Integer getAttribSort() {
		return attribSort;
	}

	public void setAttribSort(Integer attribSort) {
		this.attribSort = attribSort;
	}

	public Long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public Long getTrialDataId() {
		return trialDataId;
	}

	public void setTrialDataId(Long trialDataId) {
		this.trialDataId = trialDataId;
	}

	/*
	 * compare agSort and attribute sort for unique sorting within a trialform
	 */
	public int compareTo(ComponentMapKey key) {
		if(this.getAgSort().equals(key.getAgSort())) {
			if(this.getAttribSort().equals(key.getAttribSort())) {
				return 0;
			} else {
				return (this.getAttribSort() < key.getAttribSort() ? -1 : 1);
			}
		}
		return (this.getAgSort() < key.getAgSort() ? -1 : 1);
	}

}
