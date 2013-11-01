package util.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.log.Log;

import util.RuntimeConfiguration;

@Name("PluginRegistry")
@Startup(depends="RuntimeConfiguration")
@Scope(ScopeType.APPLICATION)
public class PluginRegistry implements IPluginRegistry {
	
	@In("RuntimeConfiguration")
	private RuntimeConfiguration config;
	
	@Logger
	private Log log;
	
	private Map<String, Component> plugins;
	
	private Map<String, List<IPageFragment>> fragments;
	
	@Create
	public void create() {
		plugins = new HashMap<String, Component>();
		fragments = new HashMap<String, List<IPageFragment>>();
		
		log.debug("starting plugin registration");
		String pluginList = config.getPluginList();
		log.debug("pluginList: #0", pluginList);
		
		StringTokenizer st = new StringTokenizer(pluginList, ",");
		
		while(st.hasMoreTokens()) {
			String pluginName = st.nextToken();
			
			Component pluginComp = Component.forName(pluginName);
			
			if(pluginComp == null) {
				log.warn("failed to load plugin #0 no Seam Component with this name found", pluginName);
				continue;	// skip
			}
			
			Object o = pluginComp.newInstance();
			
			if (o instanceof IPlugin) {
				IPlugin plugin = (IPlugin) o;
				
				log.info("Plugin successfully loaded: #0", plugin.getName());
				
				plugins.put(pluginName, pluginComp);
				
				// register all page fragments
				for(IPageFragment pf : plugin.getPageFragments()) {
					List<IPageFragment> pfList = fragments.get(pf.getPageId());
					
					if(pfList == null) {
						pfList = new ArrayList<IPageFragment>();
						fragments.put(pf.getPageId(), pfList);
					}
					
					pfList.add(pf);
					log.info("Registered pagefragment for page #0 with url #1", pf.getPageId(), pf.getFragmentUrl());
				}
				
			} else {
				log.warn("Found plugin that does not implement IPlugin interface with name #0", pluginName);
			}
		}
	}

	public IPlugin getPluginForName(String name) {
		return (IPlugin)plugins.get(name).newInstance();
	}

	public Set<String> getPluginNames() {
		return plugins.keySet();
	}

	public List<IPageFragment> getPageFragments(String pageId) {
		return fragments.get(pageId);
	}

	

}
