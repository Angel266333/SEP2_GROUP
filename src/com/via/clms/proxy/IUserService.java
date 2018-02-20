package com.via.clms.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.via.clms.Utils;

/**
 * A user service class that is used to manage multi-user and user permissions
 */
public interface IUserService extends Remote {
	
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
	 * @param byte[] token
	 * 		The user token to validate
	 */
	boolean checkUser(byte[] token) throws RemoteException;

	/**
	 * Check a specific user permission
	 * 
	 * This method can be used to check if a user has a specific permission 
	 * for a specific library. 
	 * 
	 * @param byte[] token
	 * 		The user token
	 * 
	 * @param int libraryid
	 * 		Database id of the library
	 * 
	 * @param String role
	 * 		Permission role identifier
	 */
	boolean checkPermission(byte[] token, int libraryid, String role) throws RemoteException;
}
