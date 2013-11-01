package dao.interfaces;

/**
 * 
 * @author Favre-Bulle, Rauscha
 *
 * Data Access Object witch combines Part DAO and Teddybear DAO
 */
public interface IStorageDAO {

	/**
	 * Returns the Part DAO
	 * @return
	 */
	public IPartDao getPartDao();
	
	/**
	 * Returns the Teddybear DAO
	 * @return
	 */
	public ITeddybearDao getTeddybearDao();
	
}
