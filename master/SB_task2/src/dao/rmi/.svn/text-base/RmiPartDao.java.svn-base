package dao.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import products.PartsContainer;
import products.parts.Arm;
import products.parts.Body;
import products.parts.Head;
import products.parts.Leg;
import products.parts.abstracts.Cap;
import products.parts.abstracts.Product;
import rmi.interfaces.IRemoteServer;
import dao.interfaces.IPartDao;
import exceptions.IDaoSaveException;

public class RmiPartDao implements IPartDao {

	@Override
	public void saveArm(Arm arm) throws IDaoSaveException {
		try {
			getRemoteServer().addArm(arm);
		} catch (Exception e) {
			throw new IDaoSaveException();
		}
	}

	@Override
	public void saveLeg(Leg leg) throws IDaoSaveException {
		try {
			getRemoteServer().addLeg(leg);
		} catch (Exception e) {
			throw new IDaoSaveException();
		}
	}

	@Override
	public void saveBody(Body body) throws IDaoSaveException {
		try {
			getRemoteServer().addBody(body);
		} catch (Exception e) {
			throw new IDaoSaveException();
		}
	}

	@Override
	public void saveCap(Cap cap) throws IDaoSaveException {
		try {
			getRemoteServer().addCap(cap);
		} catch (Exception e) {
			throw new IDaoSaveException();
		}
	}

	@Override
	public void saveHead(Head head) throws IDaoSaveException {
		try {
			getRemoteServer().addHead(head);
		} catch (Exception e) {
			throw new IDaoSaveException();
		}
	}

	@Override
	public PartsContainer getAllPartsAtomic() {
		try {
			return getRemoteServer().getPartsContainer();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getAllParts() {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			products.addAll(getRemoteServer().getHeads());
			products.addAll(getRemoteServer().getBodies());
			products.addAll(getRemoteServer().getArms());
			products.addAll(getRemoteServer().getLegs());
			products.addAll(getRemoteServer().getCaps());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return products;
	}
	
	public IRemoteServer getRemoteServer() throws MalformedURLException, RemoteException, NotBoundException {
		IRemoteServer remoteServer = (IRemoteServer) Naming.lookup("//localhost/RMIStorageServer");
		return remoteServer;
	}

}
