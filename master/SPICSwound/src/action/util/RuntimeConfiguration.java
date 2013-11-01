package util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;

import db.interfaces.IPropertyDAO;
import entities.Property;

@Name("RuntimeConfiguration")
@Startup(depends="DeploymentAction")
@Scope(ScopeType.APPLICATION)
public class RuntimeConfiguration {

	public static final String PROP_LOGO_URL = "logo.path";
	public static final String PROP_SUPPORT_EMAIL = "support.email";
	public static final String PROP_RICHFACES_SKIN = "richfaces.skin";
	private static final String PROP_RICHFACES_SKIN_DEFAULT = "classic";
	public static final String PROP_STYLESHEET_URL = "stylesheet.path";
	public static final String PROP_PLUGIN_LIST = "plugin.list";
	public static final String PROP_ACTIVE_CHARTS = "activecharts";

	private HashMap<String, List<RuntimeConfigurationListener>> listeners = new HashMap<String, List<RuntimeConfigurationListener>>();

	@In
	private IPropertyDAO propertyDAO;
	
	@In("DeploymentAction")
	private DeploymentAction deploymentAction;

	@Logger
	private Log log;

	public String getStylesheetUrl() {
		return getStringProperty(PROP_STYLESHEET_URL);
	}

	public String getPluginList() {
		return getStringProperty(PROP_PLUGIN_LIST);
	}

	public String getRichfacesSkin() {
		String skin = getStringProperty(PROP_RICHFACES_SKIN);
		return StringUtils.isEmpty(skin) ? PROP_RICHFACES_SKIN_DEFAULT : skin;
	}

	public String getLogoUrl() {
		return getStringProperty(PROP_LOGO_URL);
	}

	public String getActiveCharts() {
		return getStringProperty(PROP_ACTIVE_CHARTS);
	}

	public String getSupportEmail() {
		return getStringProperty(PROP_SUPPORT_EMAIL);
	}

	public String getStringProperty(String key) {
		Property p = propertyDAO.findByID(key);

		if (p == null) {
			log.debug("Missing Resource in runtime config: #0", key);
			
			// trying fallback
			String fallback = deploymentAction.getStringProperty("runtime." + key);
			
			if (fallback == null){
				log.error("Missing Resource in runtime AND deployment config: #0", key);
				return "";
			}
			
			return fallback;
		}

		return p.getValue();
	}
	
	public void registerListener(RuntimeConfigurationListener listener, String key){
		
		List<RuntimeConfigurationListener> l = listeners.get(key);
		
		// first listener for property
		if (l == null){
			l = new LinkedList<RuntimeConfigurationListener>();
			listeners.put(key, l);
			l.add(listener);
			return;
		}
		
		if (l.contains(listener)){
			return;
		}
		
		l.add(listener);
	}
	
	public void removeListener(RuntimeConfigurationListener listener, String key){
		List<RuntimeConfigurationListener> l = listeners.get(key);
		
		if (l == null){
			return;
		}
		
		l.remove(listener);
	}
	
	public void notifiyListeners(String key, String value){
		List<RuntimeConfigurationListener> l = listeners.get(key);
		
		log.debug("call to notify with #0 and #1", key, value);
		
		if (l == null){
			return;
		}
		
		for (RuntimeConfigurationListener listener : l){
			listener.valueChanged(key, value);
			log.debug("notified #0", l);
		}
	}
	
	public void setStringProperty(String key, String value, Boolean required){
		
		Property p = new Property(key, value, required);
		
		propertyDAO.persist(p);
		
		notifiyListeners(key, value);
	}

}
