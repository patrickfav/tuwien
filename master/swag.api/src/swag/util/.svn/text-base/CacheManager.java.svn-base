package swag.util;

import net.sf.swarmcache.*;

public class CacheManager {
	private CacheFactory cacheFactory;
	
	public CacheManager()
	{
		// Configure and Initialize the cache manager
		CacheConfiguration conf = new CacheConfiguration();
		conf.setCacheType(CacheConfiguration.TYPE_LRU);
		cacheFactory = new CacheFactory(conf);
		
	}
	public CacheFactory getCacheFactory()
	{
		return cacheFactory;
	}
}
