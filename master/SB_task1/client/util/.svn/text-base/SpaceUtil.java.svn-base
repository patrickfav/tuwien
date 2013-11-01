package util;

import net.jini.core.transaction.server.TransactionManager;
import net.jini.space.JavaSpace;

import com.j_spaces.core.client.SpaceFinder;

@SuppressWarnings("deprecation")
public class SpaceUtil {
	
	private static TransactionManager mgr;
	
	static public JavaSpace findSpace(String spaceName) throws Exception {
		return (JavaSpace) SpaceFinder.find(spaceName);
	}

	/*public static Transaction getTransaction() {
		
		if(mgr == null)
			mgr = (TransactionManager) new ServiceTemplate(null,new Class[]{TransactionManager.class}, null); 
		
		//PlatformTransactionManager ptm = new LookupJiniTxManagerConfigurer().lookupTimeout(5000).transactionManager();

		try {
			long leaseTime = 1000 * 10 * 60;
			// ten minutes
			Transaction.Created created = TransactionFactory.create(mgr,
					leaseTime);
			return created.transaction;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		} catch (LeaseDeniedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static TransactionManager getTransactionManager() throws IOException, ClassNotFoundException {
		//System.setSecurityManager (new RMISecurityManager ());
	      LookupLocator lookup = new LookupLocator ("jini://X200-FAVRE:10099");
	      ServiceRegistrar registrar = lookup.getRegistrar ();
	      Entry[] serverAttributes = new Entry[1];
	      serverAttributes[0] = new Name ("TransactionManager");
	      ServiceTemplate template = new ServiceTemplate (null, null, serverAttributes);
	      return (TransactionManager) registrar.lookup (template);

	}*/

}
