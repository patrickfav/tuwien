package ticketline.dao.interfaces;

import java.util.List;
import java.util.Set;

/**
 * @author geezmo
 */
public interface DAO {

	/**
	 * returns all objects of this entity.
	 *
	 * @return all objects in a java.util.List
	 * @throws RuntimeException
	 */
	public List getAll() throws RuntimeException;

	/**
	 * performs a hql-query in the entitity.
	 *
	 * @param where
	 * @return List
	 * @throws RuntimeException
	 */
	public List find(String where) throws RuntimeException;

	public Set initSet(Set set) throws RuntimeException;

}
