package bean;


import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;

import util.BeanResetter;
import util.JSFNavigationConstants;
import util.menu.IMenuConfiguration;
import util.menu.IMenuItem;
import util.menu.MenuConfiguration;
import util.menu.MenuItem;

@Name("Menu")
@Scope(ScopeType.SESSION)
public class Menu {
	
	@In(value="BeanResetter", create=true) @Out
	private BeanResetter beanResetter;
	
	public boolean showSubmenu() {
		return false;	// TODO: remove completely!
	}
	
	private void resetGrids() {
		beanResetter.resetAll();
	}
	
	
	private IMenuConfiguration configuration;
	
	@Create
	public void createMenuConfiguration() {
		configuration = new MenuConfiguration();
		
		/* main menu */
		List<IMenuItem> items = configuration.getMenuItems();
		
		if(Identity.instance().hasRole("contributor")) {
			MenuItem myStudies = new MenuItem(JSFNavigationConstants.SHOWMYSTUDIES, 
					new MenuItem.MessageString("button.mystudies"),
					JSFNavigationConstants.SHOWMYSTUDIES);
			
			IMenuConfiguration trialSubCnfg = myStudies.getSubConfiguration();
			trialSubCnfg.getMenuItems().add(
					new MenuItem(JSFNavigationConstants.VIEWTRIAL, 
							new MenuItem.MessageString("button.general"),
							JSFNavigationConstants.VIEWTRIAL));
			
			if(Identity.instance().hasRole("creator")) {
				trialSubCnfg.getMenuItems().add(
					new MenuItem(JSFNavigationConstants.VIEWPARTNERS, 
							new MenuItem.MessageString("button.partners"),
							JSFNavigationConstants.VIEWPARTNERS));
			}
			
			trialSubCnfg.getMenuItems().add(
					new MenuItem(JSFNavigationConstants.VIEWPATIENTS, 
							new MenuItem.MessageString("button.patients"),
							JSFNavigationConstants.VIEWPATIENTS));
			
			if(Identity.instance().hasRole("creator")) {
				trialSubCnfg.getMenuItems().add(
					new MenuItem(JSFNavigationConstants.VIEWTRIALFORM, 
							new MenuItem.PermissionBasedMessageString(
									"button.definetrialforms",
									"button.viewtrialforms", 
									"#{s:hasPermission(sessionInfo.trial, 'editTrialForms')}"),
							JSFNavigationConstants.VIEWTRIALFORM));
			}
			
			trialSubCnfg.getMenuItems().add(
					new MenuItem(JSFNavigationConstants.VIEWIMPORTEXPORT, 
							new MenuItem.MessageString("button.importexport"),
							JSFNavigationConstants.VIEWIMPORTEXPORT));
			
			trialSubCnfg.getMenuItems().add(
					new MenuItem(JSFNavigationConstants.VIEWAPPOINTMENTS,
							new MenuItem.MessageString("button.appointments"),
							JSFNavigationConstants.VIEWAPPOINTMENTS));
			
			items.add(myStudies);
		}
		
		if(Identity.instance().hasRole("creator")) {
			items.add(new MenuItem(JSFNavigationConstants.NEWTRIAL, 
					new MenuItem.MessageString("button.newstudy"),
					JSFNavigationConstants.NEWTRIAL));
		}
		
		IMenuItem showMyProfileItem = new MenuItem(JSFNavigationConstants.SHOWMYPROFILE, 
				new MenuItem.MessageString("button.myprofile"),
				JSFNavigationConstants.SHOWMYPROFILE);
		
		// ticket #449
//		showMyProfileItem.getSubConfiguration().getMenuItems().add(new MenuItem(
//				"Sub" + JSFNavigationConstants.SHOWMYPROFILE,
//				new MenuItem.MessageString("button.userdata"),
//				JSFNavigationConstants.SHOWMYPROFILE));
		
		items.add(showMyProfileItem);
		
		
		if(Identity.instance().hasRole("admin")) {
			IMenuItem adminItem = new MenuItem(JSFNavigationConstants.ADMINISTRATION, 
					new MenuItem.MessageString("button.administration"),
					JSFNavigationConstants.ADMINISTRATION);
			
			List<IMenuItem> adminSubList = adminItem.getSubConfiguration().getMenuItems();
			
			adminSubList.add(new MenuItem("Sub" + JSFNavigationConstants.ADMINISTRATION,
					new MenuItem.MessageString("button.users"),
					JSFNavigationConstants.ADMINISTRATION));
			
			adminSubList.add(new MenuItem("Sub" + JSFNavigationConstants.EVENTS,
					new MenuItem.MessageString("button.events"),
					JSFNavigationConstants.EVENTS));
			
			adminSubList.add(new MenuItem("Sub" + JSFNavigationConstants.PROPS,
					new MenuItem.MessageString("button.properties"),
					JSFNavigationConstants.PROPS));
			
			items.add(adminItem);
		}
		
		items.add(new MenuItem(JSFNavigationConstants.ABOUT, 
				new MenuItem.MessageString("button.about"),
				JSFNavigationConstants.ABOUT));
		
		/* end main menu */
		
		configuration.configure();
	}
	
	public IMenuConfiguration getConfiguration() {
		return configuration;
	}
	
	public IMenuConfiguration getSubConfiguration() {
		return configuration.getActiveItem().getSubConfiguration();
	}
	
	public String setActive(String menuItemId) {
		
		resetGrids();
		
		return configuration.setActiveMenuItem(menuItemId);
	}
}
