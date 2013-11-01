package bean.action;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class AttributeIdentifier {
	
	@XmlElement
	private String attributeName;
	
	@XmlElement
	private String attributeGroupName;
	
	@XmlElement
	private Long attributeId;
	
	public AttributeIdentifier() {	}

	public AttributeIdentifier(String attributeName, String attributeGroupName,
			Long attributeId) {
		this.attributeName = attributeName;
		this.attributeGroupName = attributeGroupName;
		this.attributeId = attributeId;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeGroupName() {
		return attributeGroupName;
	}

	public void setAttributeGroupName(String attributeGroupName) {
		this.attributeGroupName = attributeGroupName;
	}

	public Long getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}

}
