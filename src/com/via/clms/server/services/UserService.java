package com.via.clms.server.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.via.clms.Log;
import com.via.clms.Utils;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;
import com.via.clms.shared.User;

/**
 * Implementation of the remote {@link IUserService} service
 */
public class UserService implements IUserService, Service {
	
	/**
	 * Generates a user token that can be used to identify/validate a user
	 * 
	 * The idea with tokens is to have something more safe to transfer between 
	 * server and clients, unlike raw passwords, that should be kept local.
	 * 
	 * @param long cpr
	 * 		The CPR of the user to generate the token for
	 * 
	 * @param String passwd
	 * 		The password used by the user
	 * 
	 * @return byte[]
	 * 		A hash value in raw bytes
	 */
	public static byte[] generateUserToken(long cpr, String passwd) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update( (Long.toString(cpr) + passwd).getBytes( StandardCharsets.UTF_8 ) );
			
			return md.digest();
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getUserToken(long cpr, String passwd) {
		byte[] token = generateUserToken(cpr, passwd);
		String tokenStr = Utils.tokenToString(token);
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT COUNT(*) AS cCount FROM users WHERE cUserToken=?", tokenStr);
		
		try {
			if (result.first()) {
				if (result.getInt("cCount") > 0) {
					return token;
				}
			}
			
		} catch (SQLException e) {
			Log.error(e);
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkPermissions(byte[] token, int libraryid, int roles) {
		String tokenStr = Utils.tokenToString(token);
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT p.cFlags " +
				"FROM permissions p JOIN users AS u ON u.cId = p.cUserId" +
				"WHERE u.cUserToken=? AND (p.cLibraryId=? OR p.cLibraryId=0)", tokenStr, libraryid);
		
		try {
			int perms = 0;
			
			while (result.next()) {
				perms |= result.getInt("cFlags");
			}
			
			return (perms & roles) == roles;
			
		} catch (SQLException e) {
			Log.error(e);
		}
		
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPermissions(byte[] token, int libraryid) {
		String tokenStr = Utils.tokenToString(token);
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT p.cFlags " +
				"FROM permissions p JOIN users AS u ON u.cId = p.cUserId" +
				"WHERE u.cUserToken=? AND p.cLibraryId=?", tokenStr, libraryid);
		
		try {
			if (result.first()) {
				return result.getInt("cFlags");
			}
			
		} catch (SQLException e) {
			Log.error(e);
		}
		
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onConfigure() {
		boolean sbchk = ServiceManager.hasService("database");
		
		if (!sbchk) {
			Log.info("UserService could not be loaded. Depends on the missing DatabaseService");
		}
		
		return sbchk;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Map<String,Integer> getRoles() {
		Map<String,Integer> roles = new LinkedHashMap<>();
		
		roles.put("Book Rental System", ROLE_BOOKRENT);
		roles.put("Book Management", ROLE_BOOKMGR);
		roles.put("User Management", ROLE_USERMGR);
		roles.put("Administrator", ROLE_ADMIN);
		
		return roles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onShutdown() {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getSpecialToken(byte[] token, int libraryid) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User[] getUsers(byte[] token, int offset, int length) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUserByUID(byte[] token, int uid) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUserByCPR(byte[] token, long cpr) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addUser(byte[] token, long cpr, String passwd) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateUser(byte[] token, User data) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] updateUserPasswd(byte[] token, long cpr, String oldPasswd, String newPasswd) {
		return null;
	}
}
