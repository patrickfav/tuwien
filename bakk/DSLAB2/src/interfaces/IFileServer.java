package interfaces;

import java.rmi.RemoteException;

import error.FileServerFileNotFound;

public interface IFileServer extends IServer{
	String download(String path) throws RemoteException,FileServerFileNotFound;
}
