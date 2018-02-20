package com.via.clms.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import com.via.clms.Log;
import com.via.clms.server.services.Service;

/**
 * Class used to manage and access services
 * 
 * This class can manage both local and remote services.
 * Remote service can be accessed by clients via Java's RMI system.
 */
public class ServiceManager {

	/** * */
	private static int RMI_PORT = 2019;
	
	/** * */
	private static Map<String, Service> SERVICES = new HashMap<>();
	
	/** * */
	private static Registry REGISTRY;
	
	/**
	 * Static block used to initiate the registry
	 * that is used to export remote services. 
	 */
	static {
        try {
        	REGISTRY = LocateRegistry.createRegistry(RMI_PORT);
            
        } catch (RemoteException e) {
            // Error means that registry already exists
			try {
				REGISTRY = LocateRegistry.getRegistry(RMI_PORT);

			} catch (Exception ei) {
				throw new RuntimeException(ei.getMessage(), e);
			}
        }
	}
	
	/**
	 * Register a new service within the manager
	 * 
	 * If you want to export the service as a remote service, 
	 * then it must implement the {@link Remote} interface.
	 * 
	 * Registering a service will invoke it's {@link Service#onConfigure()} method.
	 * 
	 * @param String name
	 * 		A name that is used to identify the service later
	 * 
	 * @param Service service
	 * 		The service class to register
	 */
	public static boolean registerService(String name, Service service) {
		if (SERVICES.containsKey(name)) {
			Log.error("There is already a service registered using the name " + name);

		} else {
			try {
				if (service.onConfigure()) {
					if (service instanceof Remote) {
						Remote stub = UnicastRemoteObject.exportObject((Remote) service, 0);
						REGISTRY.rebind(name, stub);
					}
					
					SERVICES.put(name, service);
					
					return true;
				}
				
			} catch (Exception e) {
				Log.error(e);
			}
		}
		
		return false;
	}
	
	/**
	 * Unregister a service from the manager
	 * 
	 * If the service has been exported as a remote service, 
	 * then it will be unexported and unaccessible to clients. 
	 * 
	 * Unregistering a service will invoke it's {@link Service#onShutdown()} method.
	 * 
	 * @param String name
	 * 		The name that was used to register the service
	 */
	public static boolean unregisterService(String name) {
		if (!SERVICES.containsKey(name)) {
			Log.error("There is no service registered using the name " + name);

		} else {
			try {
				Service service = SERVICES.get(name);
				
				if (service instanceof Remote) {
					REGISTRY.unbind(name);
					UnicastRemoteObject.unexportObject((Remote) service, true);
				}
				
				service.onShutdown();
				
				SERVICES.remove(name);
				
				return true;
				
			} catch (Exception e) {
				Log.error(e);
			}
		}
		
		return false;
	}
	
	/**
	 * Find and return a service that has been registered with this manager
	 * 
	 * @param String name
	 * 		The name that was used to register the service
	 */
	public static Service getService(String name) {
		if (!SERVICES.containsKey(name)) {
			Log.error("There is no service registered using the name " + name);

		} else {
			return SERVICES.get(name);
		}
		
		return null;
	}
	
	/**
	 * Check to see if a service has been registered with this manager
	 * 
	 * @param String name
	 * 		The name that was used to register the service
	 */
	public static boolean hasService(String name) {
		return SERVICES.containsKey(name);
	}
}
