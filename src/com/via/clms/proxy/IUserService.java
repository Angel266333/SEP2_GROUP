package com.via.clms.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import com.via.clms.Utils;
import com.via.clms.shared.User;

/**
 * A user service class that is used to manage multi-user and user permissions
 */
public interface IUserService extends Remote {
	
	/** Allow access to the rental system */
	public final static int ROLE_BOOKRENT = 0b0000001; // 1
	
	/** Allow access to book management, including rental system */
	public final static int ROLE_BOOKMGR = 0b0100001; // 32 + 1
	
	/** Allow access to user management.
	 *  This only allows creating regular users, 
	 *  handling permissions requires {@link #ROLE_ADMIN} */
	public final static int ROLE_USERMGR = 0b1000000; // 64
	
	/** Allow full administrative access */
	public final static int ROLE_ADMIN = 0b11111111; // -1
	
	/**
	 * Get a user token that can be used by a user for further 
	 * access to the server features.
	 * 
	 * @param cpr
	 * 		The user CPR
	 * 
	 * @param passwd 
	 * 		The user password
	 */
	byte[] getUserToken(long cpr, String passwd) throws RemoteException;

	/**
	 * Check a specific users permission flags
	 * 
	 * This method can be used to check if a user has a specific permission 
	 * for a specific library. 
	 * 
	 * @param token
	 * 		The user token
	 * 
	 * @param libraryid
	 * 		Database id of the library
	 * 
	 * @param roles
	 * 		One or more flags to match against. 
	 * 		Multiple flags can be merged via logical operations
	 */
	boolean checkPermissions(byte[] token, int libraryid, int roles) throws RemoteException;
	
	/**
	 * Get the permission bit flag for a specific user
	 * 
	 * The permissions is relative to a specific library. 
	 * 
	 * @param token
	 * 		The user token
	 * 
	 * @param libraryid
	 * 		Database id of the library
	 */
	int getPermissions(byte[] token, int libraryid) throws RemoteException;
	
	/**
	 * Get a list of all available roles used by the system. 
	 * The map keys are the role labels
	 */
	Map<String,Integer> getRoles() throws RemoteException;
}
