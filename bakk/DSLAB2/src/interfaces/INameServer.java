package interfaces;

import java.rmi.RemoteException;

import error.CouldNotCreateNewServer;
import error.NoSuchServerFound;
import error.ServerNameAlreadyInUse;

public interface INameServer extends IServer{
	IServer register(IServer ns, String domain)  throws  RemoteException,NoSuchServerFound,ServerNameAlreadyInUse;
	void setChild(String name, IServer ns) throws RemoteException,CouldNotCreateNewServer,ServerNameAlreadyInUse;
	IServer getChild(String name) throws RemoteException,NoSuchServerFound;
	void removeChild(String name)throws RemoteException,NoSuchServerFound;
	void unregister(String name) throws RemoteException,NoSuchServerFound;
}
