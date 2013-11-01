package util.menu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MenuConfiguration implements IMenuConfiguration {

	private static final String STYLE_FIRST_SELECTED = "first_selected";
	private static final String STYLE_FIRST = "first";
	private static final String STYLE_DEFAULT_SELECTED = "default_selected";
	private static final String STYLE_DEFAULT = "default";
	private static final String STYLE_TAB_SELECTED = "tab_selected";
	private static final String STYLE_TAB = "tab";

	private List<IMenuItem> menuItems;

	private Map<String, Integer> mapping;
	
	private IMenuItem activeItem;

	public List<IMenuItem> getMenuItems() {
		if (menuItems == null)
			menuItems = new LinkedList<IMenuItem>();
		return menuItems;
	}

	public void configure() {
		configure(false);
	}

	public void configure(boolean isSubConfig) {
		mapping = new HashMap<String, Integer>();
		Integer position = 0;
		for (IMenuItem mi : menuItems) {
			mapping.put(mi.getItemId(), position);

			if (isSubConfig) {
				mi.setSelectedStyleClass(STYLE_TAB_SELECTED);
				mi.setStyleClass(STYLE_TAB);
			} else {
				if (position == 0) {
					mi.setSelectedStyleClass(STYLE_FIRST_SELECTED);
					mi.setStyleClass(STYLE_FIRST);
				} else {
					mi.setSelectedStyleClass(STYLE_DEFAULT_SELECTED);
					mi.setStyleClass(STYLE_DEFAULT);
				}
			}

			if (!mi.getSubConfiguration().isEmpty())
				mi.getSubConfiguration().configure(true);

			position++;
		}
		
		activateFirst();
	}

	public String setActiveMenuItem(String itemId) {
		Integer position = mapping.get(itemId);

		if (position == null)
			throw new IllegalArgumentException("menuitem with id " + itemId
					+ " does not exist");

		IMenuItem toActivate = menuItems.get(position);
		
		if(activeItem != null) {
			activeItem.setActive(false);
		}

		toActivate.setActive(true);
		
		activeItem = toActivate;

		return toActivate.getNavigationString();

	}

	public boolean isEmpty() {
		return getMenuItems().isEmpty();
	}

	public IMenuItem getActiveItem() {
		return activeItem;
	}
	
	public void reset() {
		if(activeItem != null) {
			activeItem.setActive(false);
			activeItem = null;
		}
		
		activateFirst();
	}
	
	private void activateFirst() {
		if(!menuItems.isEmpty()) {
			IMenuItem first = menuItems.get(0);
			first.setActive(true);
			activeItem = first;
		}
	}

}
