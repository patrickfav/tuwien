package dao;

import dao.interfaces.IPartDao;
import dao.interfaces.IStorageDAO;
import dao.interfaces.ITeddybearDao;
import dao.rmi.RmiPartDao;
import dao.rmi.RmiTeddybearDao;

public class RMIStorageDAOImpl implements IStorageDAO {

	private IPartDao partDao;
	private ITeddybearDao teddybearDao;
	
	public RMIStorageDAOImpl() {
		this.partDao = new RmiPartDao();
		this.teddybearDao = new RmiTeddybearDao();
	}

	@Override
	public IPartDao getPartDao() {
		return partDao;
	}

	@Override
	public ITeddybearDao getTeddybearDao() {
		return teddybearDao;
	}

}
