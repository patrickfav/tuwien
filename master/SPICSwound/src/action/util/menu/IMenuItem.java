package util.menu;

public interface IMenuItem {
	
	public String getActiveStyleClass();
	
	public String getItemId();
	
	public void setItemId(String itemId);

	public String getLabel();

	public String getNavigationString();

	public void setNavigationString(String navigationString);

	public String getStyleClass();

	public void setStyleClass(String styleClass);

	public String getSelectedStyleClass();

	public void setSelectedStyleClass(String selectedStyleClass);
	
	public boolean isActive();
	
	public void setActive(boolean active);
	
	public IMenuConfiguration getSubConfiguration();

}
