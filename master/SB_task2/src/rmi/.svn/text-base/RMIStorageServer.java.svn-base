package rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import products.PartsContainer;
import products.Teddybear;
import products.parts.Arm;
import products.parts.Body;
import products.parts.Head;
import products.parts.Leg;
import products.parts.abstracts.Cap;
import rmi.interfaces.IRemoteServer;

public class RMIStorageServer extends UnicastRemoteObject implements
		IRemoteServer {

	private static final long serialVersionUID = -2920847114875743806L;

	private List<Head> heads;
	private List<Body> bodies;
	private List<Arm> arms;
	private List<Leg> legs;
	private List<Cap> caps;
	private List<Teddybear> assembled;
	private List<Teddybear> shipped;
	private List<Teddybear> defect;
	private List<Teddybear> checked;

	public RMIStorageServer() throws RemoteException {
		this.heads = Collections.synchronizedList(new ArrayList<Head>());
		this.bodies = Collections.synchronizedList(new ArrayList<Body>());
		this.arms = Collections.synchronizedList(new ArrayList<Arm>());
		this.legs = Collections.synchronizedList(new ArrayList<Leg>());
		this.caps = Collections.synchronizedList(new ArrayList<Cap>());
		this.assembled = Collections
				.synchronizedList(new ArrayList<Teddybear>());
		this.shipped = Collections
		.synchronizedList(new ArrayList<Teddybear>());
		this.defect = Collections
		.synchronizedList(new ArrayList<Teddybear>());
		this.checked = Collections
		.synchronizedList(new ArrayList<Teddybear>());
	}

	public static void main(String args[]) {
		System.out.println("RMI server started");

		/*
		 * Security Manager if (System.getSecurityManager() == null) {
		 * System.setSecurityManager(new RMISecurityManager());
		 * System.out.println("Security manager installed."); } else {
		 * System.out.println("Security manager already exists."); }
		 */

		try {
			LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			System.out.println("java RMI registry already exists.");
		}

		try {

			// Instantiate RMIStorageServer
			RMIStorageServer obj = new RMIStorageServer();
			Naming.rebind("//localhost/RMIStorageServer", obj);
			System.out.println("PeerServer bound in registry");

			// Exit on Enter
			Scanner sc = new Scanner(System.in);
			while (sc.nextLine() == null)
				;

			unexportObject(obj, true);

			System.out.println("Goodbye...");
		} catch (Exception e) {
			System.err.println("RMI server exception:" + e);
			e.printStackTrace();
		}

	}

	@Override
	public List<Head> getHeads() throws RemoteException {
		return heads;
	}

	@Override
	public synchronized void addHead(Head h) throws RemoteException {
		heads.add(h);
		notifyAll();
	}

	@Override
	public Head takeHead() throws RemoteException {	
		if (!heads.isEmpty()) {
			Head h = heads.get(0);
			heads.remove(h);
			return h;
		} else {
			return null;
		}
	}

	@Override
	public List<Body> getBodies() throws RemoteException {
		return bodies;
	}

	@Override
	public synchronized void addBody(Body b) throws RemoteException {
		bodies.add(b);
		notifyAll();
	}

	@Override
	public Body takeBody() throws RemoteException {
		if (!bodies.isEmpty()) {
			Body b = bodies.get(0);
			bodies.remove(b);
			return b;
		} else {
			return null;
		}
	}

	@Override
	public List<Arm> getArms() throws RemoteException {
		return arms;
	}

	@Override
	public synchronized void addArm(Arm a) throws RemoteException {
		arms.add(a);
		notifyAll();
	}

	@Override
	public Arm takeArm() throws RemoteException {
		if (!arms.isEmpty()) {
			Arm a = arms.get(0);
			arms.remove(a);
			return a;
		} else {
			return null;
		}
	}

	@Override
	public List<Leg> getLegs() throws RemoteException {
		return legs;
	}

	@Override
	public synchronized void addLeg(Leg l) throws RemoteException {
		legs.add(l);
		notifyAll();
	}

	@Override
	public Leg takeLeg() throws RemoteException {
		if (!legs.isEmpty()) {
			Leg l = legs.get(0);
			legs.remove(l);
			return l;
		} else {
			return null;
		}
	}

	@Override
	public List<Cap> getCaps() throws RemoteException {
		return caps;
	}

	@Override
	public synchronized void addCap(Cap c) throws RemoteException {
		caps.add(c);
		notifyAll();
	}

	@Override
	public Cap takeCap() throws RemoteException {
		if (!caps.isEmpty()) {
			Cap c = caps.get(0);
			caps.remove(c);
			return c;
		} else {
			return null;
		}
	}

	@Override
	public List<Teddybear> getAssembled() throws RemoteException {
		return assembled;
	}

	@Override
	public synchronized void addAssembled(Teddybear t) throws RemoteException {
		assembled.add(t);
		notifyAll();
	}

	@Override
	public synchronized Teddybear takeAssembled() throws RemoteException, InterruptedException {
		while(assembled.isEmpty()) {
			wait();
		}
		Teddybear t = assembled.get(0);
		assembled.remove(t);
		return t;
	}

	@Override
	public List<Teddybear> getShipped() throws RemoteException {
		return shipped;
	}

	@Override
	public void addShipped(Teddybear t) throws RemoteException {
		shipped.add(t);
	}

	@Override
	public List<Teddybear> getDefect() throws RemoteException {
		return defect;
	}

	@Override
	public void addDefect(Teddybear t) throws RemoteException {
		defect.add(t);
	}
	
	@Override
	public List<Teddybear> getChecked() throws RemoteException {
		return checked;
	}

	@Override
	public synchronized void addChecked(Teddybear t) throws RemoteException {
		checked.add(t);
		notifyAll();
	}

	@Override
	public synchronized Teddybear takeChecked() throws RemoteException, InterruptedException {
		while(checked.isEmpty()) {
			wait();
		}
		Teddybear t = checked.get(0);
		checked.remove(t);
		return t;
	}

	@Override
	public synchronized PartsContainer getPartsContainer() throws RemoteException, InterruptedException {
		while(getArms().size() < 2 || getHeads().isEmpty() || getBodies().isEmpty() || getLegs().size() < 2 || getCaps().isEmpty()) {
			wait();
		}
		
		PartsContainer pc = new PartsContainer();
		pc.setHead(takeHead());
		pc.setBody(takeBody());
		pc.setLeftArm(takeArm());
		pc.setRightArm(takeArm());
		pc.setLeftLeg(takeLeg());
		pc.setRightLeg(takeLeg());
		pc.setCap(takeCap());
		
		return pc;
	}

}
