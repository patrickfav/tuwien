package interfaces;

public interface DAOListener {
	
	public void beforeInsert(Class<?> c);

	public void afterInsert(Class<?> c);

	public void beforeUpdate(Class<?> c);

	public void afterUpdate(Class<?> c);

	public void beforeDelete(Class<?> c);

	public void afterDelete(Class<?> c);

	public void beforeFind(Class<?> c, String search);

	public void afterFind(Class<?> c, String search);
}
