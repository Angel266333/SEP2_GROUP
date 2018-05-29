package com.via.clms.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.via.clms.shared.Library;

/**
 * A library service class that is used to manage libraries
 */

public interface ILibraryService extends Remote {

	public boolean createLibrary(byte[] reqToken, String name, String location) throws RemoteException;
	
	public Library getLibraryByLID(byte[] reqToken, int lid) throws RemoteException;
	
	public Library[] getLibraries(byte[] reqToken, int offset, int length) throws RemoteException;

	
}
