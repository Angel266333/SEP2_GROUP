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
	 * Can be used like a user token, but cannot be used to login with 
	 * 
	 * This is used to produce tokens that can be used for verification on 
	 * specific things. For example to lock a client to a renting position
	 * for renting terminals. 
	 */
	byte[] getSpecialToken(byte[] token, int libraryid, int roles) throws RemoteException;
	
	/**
	 * Checks is a specified token is a special token
	 * 
	 * @param token
	 * 		Token to check
	 */
	boolean isSpecialToken(byte[] token) throws RemoteException;

	/**
	 * Check a specific users permission flags
	 * 
	 * This method can be used to check if a user has a specific permission 
	 * for a specific library. 
	 * 
	 * @param token
	 * 		The session token
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
	 * 		The session token
	 * 
	 * @param uid
	 * 		The user id to get permissions for
	 * 
	 * @param libraryid
	 * 		Database id of the library
	 */
	int getPermissions(byte[] token, int uid, int libraryid) throws RemoteException;
	
	/**
	 * Get a list of all available roles used by the system. 
	 * The map keys are the role labels
	 */
	Map<String,Integer> getRoles() throws RemoteException;
	
	/**
	 * Get all users that is registered on the server
	 * 
	 * @param token
	 * 		The session token
	 * 
	 * @param offset 
	 * 		Offset row number starting from '0'
	 * 
	 * @param length 
	 * 		Number of total rows to return
	 */
	User[] getUsers(byte[] token, int offset, int length) throws RemoteException;
	
	/**
	 * Get data on a specific user via user id
	 * 
	 * @param token
	 * 		The session token
	 * 
	 * @param uid 
	 * 		The user id
	 */
	User getUserByUID(byte[] token, int uid) throws RemoteException;
	
	/**
	 * Get data on a specific user via the user CPR
	 * 
	 * @param token
	 * 		The session token
	 * 
	 * @param cpr 
	 * 		The user CPR
	 */
	User getUserByCPR(byte[] token, long cpr) throws RemoteException;
	
	/**
	 * Create a new user
	 * 
	 * @param token
	 * 		The session token
	 * 
	 * @param cpr 
	 * 		The user CPR
	 * 
	 * @param passwd
	 * 		The user password
	 */
	boolean addUser(byte[] token, long cpr, String passwd) throws RemoteException;
	
	/**
	 * Update information about a user
	 * 
	 * This method takes a {@link User} instance. 
	 * All data within this instance will be changed in the database except 
	 * the user id, which must be valid.
	 * 
	 * @param token
	 * 		The session token
	 * 
	 * @param data 
	 * 		A {@link User} instance containing the data to store
	 */
	boolean updateUser(byte[] token, User data) throws RemoteException;
	
	/**
	 * Change a users current password
	 * 
	 * This method returns a user token to allow the current user to change he's/her own. 
	 * Changing the password will generate a new token which in turn will render any old tokens useless. 
	 * If the token being changes is used by the current session, this needs to be updated. 
	 * 
	 * @param token
	 * 		The session token
	 * 
	 * @param cpr 
	 * 		The user CPR
	 * 
	 * @param oldPasswd
	 * 		The old user password
	 * 
	 * @param newPasswd
	 * 		The new user password
	 */
	byte[] updateUserPasswd(byte[] token, long cpr, String oldPasswd, String newPasswd) throws RemoteException;
}
