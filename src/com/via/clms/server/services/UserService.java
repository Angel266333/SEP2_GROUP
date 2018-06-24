package com.via.clms.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.via.clms.Log;
import com.via.clms.Utils;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;
import com.via.clms.shared.User;

/**
 * Implementation of the remote {@link IUserService} service
 */
public class UserService implements IUserService, Service {
	
	/** * */
	private Map<String,SpecialToken> mSpecialTokens = new HashMap<>();
	
	/**
	 * {@inheritDoc}
	 */
	private class SpecialToken {
		public int lid;
		public int roles;
	}
	
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
			SecretKeySpec keySpec = new SecretKeySpec(passwd.getBytes(), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);
			mac.update((Long.toString(cpr) + passwd).getBytes());

			return mac.doFinal();
			
		} catch (Exception e) {
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
		ResultSet result = db.query("SELECT COUNT(*) AS cCount FROM Users WHERE cToken = ?", tokenStr);
		
		try {
			if (result.next()) {
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
	public boolean checkToken(byte[] token) {
		if (isSpecialToken(token)) {
			return true;
		}
		
		String tokenStr = Utils.tokenToString(token);
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT COUNT(*) AS cCount FROM Users WHERE cToken = ?", tokenStr);
		
		try {
			if (result.next()) {
				if (result.getInt("cCount") > 0) {
					return true;
				}
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
		
		if (mSpecialTokens.containsKey(tokenStr)) {
			SpecialToken st = mSpecialTokens.get(tokenStr);
			
			if (st.lid == 0 || libraryid == 0 || st.lid == libraryid) {
				return (st.roles & roles) == roles;
			}
		}
		
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT ur.cRole " +
				"FROM UserRoles ur JOIN Users u ON u.cUid = ur.cUid " +
				"WHERE u.cToken = ? AND (ur.cLid = ? OR ur.cLid = 0)", tokenStr, libraryid);
		
		try {
			int perms = 0;
			
			while (result.next()) {
				perms |= result.getInt("cRole");
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
	public boolean checkPermissions(byte[] token, int roles) {
		String tokenStr = Utils.tokenToString(token);
		
		if (mSpecialTokens.containsKey(tokenStr)) {
			return (mSpecialTokens.get(tokenStr).roles & roles) == roles;
		}
		
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT ur.cRole " +
				"FROM UserRoles ur JOIN Users u ON u.cUid = ur.cUid " +
				"WHERE u.cToken = ?", tokenStr);
		
		try {
			int perms = 0;
			
			while (result.next()) {
				perms |= result.getInt("cRole");
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
	public int getPermissions(byte[] token, int uid, int libraryid) {
		if (checkToken(token)) {
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			ResultSet result = db.query("SELECT ur.cRole " +
					"FROM UserRoles ur JOIN Users u ON u.cUid = ur.cUid " +
					"WHERE u.cUid = ? AND ur.cLid = ?", uid, libraryid);
			
			try {
				if (result.next()) {
					return result.getInt("cRole");
				}
				
			} catch (SQLException e) {
				Log.error(e);
			}
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
		
		roles.put("Login", ROLE_LOGIN);
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
	public boolean isSpecialToken(byte[] token) {
		return mSpecialTokens.containsKey(Utils.tokenToString(token));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getSpecialToken(byte[] token, int libraryid, int roles) {
		String tokenStr = Utils.tokenToString(token);
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT ur.cRole " +
				"FROM UserRoles ur JOIN Users u ON u.cUid = ur.cUid " +
				"WHERE u.cToken = ? AND (ur.cLid = ? OR ur.cLid = 0)", tokenStr, libraryid);
		
		try {
			int perms = 0;
			
			while (result.next()) {
				perms |= result.getInt("cRole");
			}
			
			// Users cannot create a token with more permissions than they originally have themselves 
			roles &= perms;
			
			for (Entry<String,SpecialToken> entry : mSpecialTokens.entrySet()) {
				SpecialToken st = entry.getValue();
				
				if (st.lid == libraryid && (st.roles & roles) == st.roles) {
					return Utils.tokenToBytes(entry.getKey());
				}
			}
			
			byte[] rand = new byte[64];
			new Random().nextBytes(rand);
			
			SpecialToken st = new SpecialToken();
			st.lid = libraryid;
			st.roles = roles;
			
			mSpecialTokens.put(Utils.tokenToString(rand), st);
			
			return rand;
			
		} catch (SQLException e) {
			Log.error(e);
		}
		
		return null;
	}
	
	/**
	 * Server internal method for creating special tokkens
	 * 
	 * This method can only be used from within the server. 
	 * It allows you to create special tokens with any pre-defined 
	 * verifications. 
	 * 
	 * @param libraryid
	 * @param roles
	 * @return
	 */
	public byte[] getSpecialToken(int libraryid, int roles) {

		byte[] rand = new byte[64];
		new Random().nextBytes(rand);
		
		SpecialToken st = new SpecialToken();
		st.lid = libraryid;
		st.roles = roles;
		
		mSpecialTokens.put(Utils.tokenToString(rand), st);
		
		return rand;		
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User[] getUsers(byte[] token, int offset, int length) {
		if (checkToken(token)) {
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			ResultSet result = db.query("SELECT * FROM Users LIMIT ? OFFSET ?", length, offset);
			
			try {
				ArrayList<User> uList = new ArrayList<>();

				while (result.next()) {
					User user = new User(result.getInt("cUid"));
					user.cpr = result.getLong("cCpr");
					user.name = result.getString("cName");
					user.email = result.getString("cEmail");
					uList.add(user);
				}

				User[] users = new User[uList.size()];
				uList.toArray(users);
				return users;
				

			} catch (SQLException e) {
				Log.error(e);
			}
		}
		
		return new User[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUserByUID(byte[] token, int uid) {
		if (checkToken(token)) {
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			ResultSet result = db.query("SELECT * FROM Users WHERE cUid = ?", uid);
			
			try {
				if (result.next()) {
					User user = new User(result.getInt("cUid"));
					user.cpr = result.getLong("cCpr");
					user.name = result.getString("cName");
					user.email = result.getString("cEmail");
					
					return user;
				}
				
			} catch (SQLException e) {
				Log.error(e);
			}
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getUserByCPR(byte[] token, long cpr) {
		if (checkToken(token)) {
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			ResultSet result = db.query("SELECT * FROM Users WHERE cCpr = ?", cpr);
			
			try {
				if (result.next()) {
					User user = new User(result.getInt("cUid"));
					user.cpr = result.getLong("cCpr");
					user.name = result.getString("cName");
					user.email = result.getString("cEmail");
					
					return user;
				}
				
			} catch (SQLException e) {
				Log.error(e);
			}
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addUser(byte[] token, long cpr, String passwd) {
		if (getUserByCPR(token, cpr) == null && checkPermissions(token, IUserService.ROLE_USERMGR)) {
			String tokenStr = Utils.tokenToString( generateUserToken(cpr, passwd) );
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			
			try {
				db.execute("INSERT INTO Users (cCpr, cToken) VALUES (?,?)", cpr, tokenStr);
				
				return true;
				
			} catch (Exception e) {
				Log.error(e);
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateUser(byte[] token, User data) {
		if (getUserByCPR(token, data.cpr) != null && checkPermissions(token, IUserService.ROLE_USERMGR)) {
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			
			try {
				db.execute("UPDATE Users SET cCpr=?, cName=?, cEmail=? WHERE cUid=?", data.cpr, data.name, data.email, data.uid);
				
				return true;
				
			} catch (Exception e) {
				Log.error(e);
			}
		}
		
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] updateUserPasswd(byte[] token, int uid, String oldPasswd, String newPasswd) {
		User user = getUserByUID(token, uid);
		
		if (user != null && checkPermissions(token, IUserService.ROLE_USERMGR)) {
			byte[] tokenByt = generateUserToken(user.cpr, newPasswd);
			String tokenStr = Utils.tokenToString( tokenByt );
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			
			try {
				db.execute("UPDATE Users SET cToken=? WHERE cUid=?", tokenStr, user.uid);
				
				return tokenByt;
				
			} catch (Exception e) {
				Log.error(e);
			}
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateUserPermissions(byte[] token, int uid, int libraryid, int role) {
		User user = getUserByUID(token, uid);
		
		if (user != null && checkPermissions(token, libraryid, IUserService.ROLE_USERMGR)) {
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			ResultSet result = db.query("SELECT ur.cRole " +
					"FROM UserRoles ur JOIN Users u ON u.cUid = ur.cUid " +
					"WHERE u.cUid = ? AND ur.cLid = ?", uid, libraryid);
			
			try {
				if (result.next()) {
					db.execute("UPDATE UserRoles SET cRole=? WHERE cUid=? AND cLid=?", role, uid, libraryid);
					
				} else {
					db.execute("INSERT INTO UserRoles (cRole, cUid, cLid) VALUES (?,?,?)", role, uid, libraryid);
				}
				
			} catch (SQLException e) {
				Log.error(e);
			}
		}
		
		return false;
	}
}
