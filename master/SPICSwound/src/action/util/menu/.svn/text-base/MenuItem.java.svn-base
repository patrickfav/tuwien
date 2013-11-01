package util.menu;

import org.jboss.seam.security.AuthorizationException;
import org.jboss.seam.security.Identity;

import util.MessageUtilsBean;


public class MenuItem implements IMenuItem {

	private String itemId;
	private IConfigurableString label;
	private String navigationString;
	private String styleClass;
	private String selectedStyleClass;
	private boolean active = false;
	private IMenuConfiguration subMenuConfiguration;

	public MenuItem(String itemId, IConfigurableString label, String navigationString,
			String styleClass, String selectedStyleClass) {
		this.itemId = itemId;
		this.label = label;
		this.navigationString = navigationString;
		this.styleClass = styleClass;
		this.selectedStyleClass = selectedStyleClass;
	}
	
	public MenuItem(String itemId, IConfigurableString label, String navigationString) {
		this.itemId = itemId;
		this.label = label;
		this.navigationString = navigationString;
	}

	public String getActiveStyleClass() {
		return active ? selectedStyleClass : styleClass;
	}
	/*
	 * getters/setters
	 */
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getLabel() {
		if(label == null)
			throw new NullPointerException("label for MenuItem " + itemId);
		
		return label.getString();
	}

	public String getNavigationString() {
		return navigationString;
	}

	public void setNavigationString(String navigationString) {
		this.navigationString = navigationString;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getSelectedStyleClass() {
		return selectedStyleClass;
	}

	public void setSelectedStyleClass(String selectedStyleClass) {
		this.selectedStyleClass = selectedStyleClass;
	}

	public IMenuConfiguration getSubConfiguration() {
		if(subMenuConfiguration == null)
			subMenuConfiguration = new MenuConfiguration();
		return subMenuConfiguration;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		if(!active) {	// reset subconfig if it exists
			if(this.subMenuConfiguration != null)
				subMenuConfiguration.reset();
		}
	}
	
	public static class MessageString implements IConfigurableString {

		private String key;
		
		public MessageString(String key) {
			super();
			this.key = key;
		}

		public String getString() {
			return MessageUtilsBean.getInstance().get(key);
		}

	}
	
	public static class PermissionBasedMessageString extends MessageString {

		private MessageString alternativeString;
		private String permissionCheck;
		
		public PermissionBasedMessageString(String key, String alternativeKey, String permissionCheck) {
			super(key);
			this.alternativeString = new MessageString(alternativeKey);
			this.permissionCheck = permissionCheck;
		}

		@Override
		public String getString() {
			
			try {
				Identity.instance().checkRestriction(permissionCheck);
				return super.getString();
			} catch (AuthorizationException ae) {
				return alternativeString.getString();
			}
		}
	}

}
