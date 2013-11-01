package util.menu;

import java.util.List;

public interface IMenuConfiguration {
	
	public void configure();
	
	public void configure(boolean isSubConfig);
	
	public List<IMenuItem> getMenuItems();

	public String setActiveMenuItem(String menuItemId);
	
	public boolean isEmpty();

	public IMenuItem getActiveItem();

	public void reset();
}
