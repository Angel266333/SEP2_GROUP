package com.via.clms.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	public boolean checkPermission(byte[] token, int libraryid, String role) {
		String tokenStr = Utils.tokenToString(token);
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT COUNT(*) AS cCount " +
				"FROM permissions p JOIN roles AS r ON r.cId = p.cRoleId JOIN users AS u ON u.cId = p.cUserId" +
				"WHERE r.cRoleIdentifier=? AND u.cUserToken=? AND (p.cLibraryId=? OR p.cLibraryId=0)", role, tokenStr, libraryid);
		
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
	@Override
	public void onShutdown() {
		
	}
}
