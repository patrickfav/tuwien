package util.plugin;

import java.util.List;
import java.util.Set;


public interface IPluginRegistry {
	
	public Set<String> getPluginNames();
	
	public IPlugin getPluginForName(String name);

	public List<IPageFragment> getPageFragments(String pageId);
}
