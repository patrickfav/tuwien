package dao;

import dao.interfaces.IPartDao;
import dao.interfaces.IStorageDAO;
import dao.interfaces.ITeddybearDao;
import dao.space.SpacePartDao;
import dao.space.SpaceTeddybearDao;

public class SpaceStorageDAOImpl implements IStorageDAO {

	private IPartDao partDao;
	private ITeddybearDao teddybearDao;
	
	public SpaceStorageDAOImpl() {
		this.partDao = new SpacePartDao();
		this.teddybearDao = new SpaceTeddybearDao();
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
