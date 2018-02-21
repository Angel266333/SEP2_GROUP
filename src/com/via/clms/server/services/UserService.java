package com.via.clms.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.via.clms.Log;
import com.via.clms.Utils;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;

/**
 * Implementation of the remote {@link IUserService} service
 */
public class UserService implements IUserService, Service {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkUser(byte[] token) {
		String tokenStr = Utils.tokenToString(token);
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT COUNT(*) AS cCount FROM users WHERE cUserToken=?", tokenStr);
		
		try {
			if (result.first()) {
				return result.getInt("cCount") > 0;
			}
			
		} catch (SQLException e) {
			Log.error(e);
		}
		
		return false;
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
		/* If a user has registered permissions at a specific library (ID > 0), 
		 * while at the same time being registered as a system administrator (ID = 0), 
		 * we need to return the permission for the requested library, which means that we must 
		 * sort descending by library id to skip possible (ID = 0) and return the requested (ID > 0)
		 */
		String tokenStr = Utils.tokenToString(token);
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT p.cFlags " +
				"FROM permissions p JOIN users AS u ON u.cId = p.cUserId" +
				"WHERE u.cUserToken=? AND (p.cLibraryId=? OR p.cLibraryId=0) ORDER BY p.cLibraryId DESC", tokenStr, libraryid);
		
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
}
