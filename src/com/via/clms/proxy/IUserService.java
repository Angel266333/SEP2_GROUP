package com.via.clms.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import com.via.clms.Utils;

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
	 * Validate a user token against the database
	 * 
	 * This is used to ensure that the token is valid and exists 
	 * in the database.
	 * 
	 * Tokens should be created based on user name and password 
	 * and can be used to check if a user has been registered with the server. 
	 * Tokens can be anything, but {@link Utils#getUserToken(String, String)} 
	 * can be used to create the them. 
	 * 
	 * @param token
	 * 		The user token to validate
	 */
	boolean checkUser(byte[] token) throws RemoteException;

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
