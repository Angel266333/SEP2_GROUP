package com.via.clms.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.via.clms.Log;

/**
 * Class used to access remote services
 * 
 * This class is used to connect to a remote RMI registry 
 * and create proxy stubs to remote service classes. 
 */
public class ServiceManager {
	
	/** * */
	private static String RMI_ADDR = "localhost";

	/** * */
	private static int RMI_PORT = 2019;
	
	/** * */
	private static Registry REGISTRY;

	/**
	 * Static block used to establish connection to a remote registry
	 */
	static {
		try {
			REGISTRY = LocateRegistry.getRegistry(RMI_ADDR, RMI_PORT);
			
		} catch (RemoteException e) {
			Log.error(e);
		}
	}
	
	/**
	 * Get a proxy stub to a remote service
	 * 
	 * @param String name
	 * 		Name that the service has been registered with
	 */
	public static Remote getService(String name) {
		try {
			return REGISTRY.lookup(name);
			
		} catch (Exception e) {
			Log.error(e);
		}
		
		return null;
	}
	
	/**
	 * Check to see if a remote service has been registered with the registry
	 * 
	 * @param String name
	 * 		Name that the service has been registered with
	 */
	public static boolean hasService(String name) {
		return getService(name) != null;
	}
	
	/**
	 * Check to see if a registry connection was established
	 */
	public static boolean isConnected() {
		return REGISTRY != null;
	}
}
