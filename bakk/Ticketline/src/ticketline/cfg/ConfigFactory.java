package ticketline.cfg;
/**
 * Factory Class for generating/retrieving the config
 * 
 * @author PatrickF
 * @version 0.1
 */
public class ConfigFactory {
	
	private static Config cfg = null;
	
	/**
	 * Gets the config object. Generates one if none exists, 
	 * otherwise returns the previous generated.
	 * 
	 * @return Config Object
	 */
	public static Config getConfig() {
		if(cfg == null) {
			cfg = new Config();
		}
		
		return cfg;
	}
}
