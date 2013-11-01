package rmi.interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import products.PartsContainer;
import products.Teddybear;
import products.parts.Arm;
import products.parts.Body;
import products.parts.Head;
import products.parts.Leg;
import products.parts.abstracts.Cap;

public interface IRemoteServer extends Remote, Serializable {

	public List<Head> getHeads() throws RemoteException;
	public void addHead(Head h) throws RemoteException;	
	public Head takeHead() throws RemoteException;
	
	public List<Body> getBodies() throws RemoteException;
	public void addBody(Body b) throws RemoteException;
	public Body takeBody() throws RemoteException;
	
	public List<Arm> getArms() throws RemoteException;
	public void addArm(Arm a) throws RemoteException;
	public Arm takeArm() throws RemoteException;
	
	public List<Leg> getLegs() throws RemoteException;
	public void addLeg(Leg l) throws RemoteException;
	public Leg takeLeg() throws RemoteException;
	
	public List<Cap> getCaps() throws RemoteException;
	public void addCap(Cap c) throws RemoteException;
	public Cap takeCap() throws RemoteException;
	
	public List<Teddybear> getAssembled() throws RemoteException;
	public void addAssembled(Teddybear t) throws RemoteException;
	public Teddybear takeAssembled() throws RemoteException, InterruptedException;
	
	public List<Teddybear> getShipped() throws RemoteException;
	public void addShipped(Teddybear t) throws RemoteException;
	
	public List<Teddybear> getDefect() throws RemoteException;
	public void addDefect(Teddybear t) throws RemoteException;
	
	public List<Teddybear> getChecked() throws RemoteException;
	public void addChecked(Teddybear t) throws RemoteException;
	public Teddybear takeChecked() throws RemoteException, InterruptedException;
	
	public PartsContainer getPartsContainer() throws RemoteException, InterruptedException;
	
	
}
