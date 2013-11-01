package dao.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import products.Teddybear;
import rmi.interfaces.IRemoteServer;
import dao.interfaces.ITeddybearDao;
import exceptions.IDaoReadException;
import exceptions.IDaoSaveException;

public class RmiTeddybearDao implements ITeddybearDao {

	@Override
	public void saveToAssembled(Teddybear teddybear) throws IDaoSaveException {
		try {
			getRemoteServer().addAssembled(teddybear);
		} catch (Exception e) {
			throw new IDaoSaveException();
		}
	}

	@Override
	public void saveToChecked(Teddybear teddybear)
			throws IDaoSaveException {
		try {
			getRemoteServer().addChecked(teddybear);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveToShipping(Teddybear teddybear)
			throws IDaoSaveException {
		try {
			getRemoteServer().addShipped(teddybear);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveToDefect(Teddybear teddybear)
			throws IDaoSaveException {
		try {
			getRemoteServer().addDefect(teddybear);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Teddybear> getAssembled() {
		try {
			return getRemoteServer().getAssembled();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Teddybear> getShipped() {
		try {
			return getRemoteServer().getShipped();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Teddybear> getDefect() {
		try {
			return getRemoteServer().getDefect();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Teddybear> getChecked() {
		try {
			return getRemoteServer().getChecked();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public IRemoteServer getRemoteServer() throws MalformedURLException, RemoteException, NotBoundException {
		IRemoteServer remoteServer = (IRemoteServer) Naming.lookup("//localhost/RMIStorageServer");
		return remoteServer;
	}


	@Override
	public Teddybear takeAssembledForDefectCheck() throws IDaoReadException {
		return takeAssembled();
	}

	@Override
	public Teddybear takeAssembledForWeightCheck() throws IDaoReadException {
		return takeAssembled();
	}
	
	public Teddybear takeAssembled() throws IDaoReadException {
		try {
			return getRemoteServer().takeAssembled();
		} catch (RemoteException e) {
			throw new IDaoReadException();
		} catch (MalformedURLException e) {
			throw new IDaoReadException();
		} catch (NotBoundException e) {
			throw new IDaoReadException();
		} catch (InterruptedException e) {
			throw new IDaoReadException();
		}
	}

	@Override
	public Teddybear takeChecked() throws IDaoReadException {
		try {
			return getRemoteServer().takeChecked();
		} catch (RemoteException e) {
			throw new IDaoReadException();
		} catch (MalformedURLException e) {
			throw new IDaoReadException();
		} catch (NotBoundException e) {
			throw new IDaoReadException();
		} catch (InterruptedException e) {
			throw new IDaoReadException();
		}
	}


}
