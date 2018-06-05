package com.via.clms.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.via.clms.Utils;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;
import com.via.clms.shared.User;

/**
 * 
 */
public class UserServiceTest {
	
	/** * */
	private static byte[] mAuthToken;
	
	/** * */
	private static final byte[] mToken;
	
	/** * */
	private static final long mCpr = 1234567878L;
	
	/** * */
	private static final String mPasswd = "123456";
	
	/**
	 * 
	 */
	static {
		mToken = UserService.generateUserToken(mCpr, mPasswd);
	}
	
	/**
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	@BeforeClass
	public static void initialize() throws InstantiationException, IllegalAccessException, SQLException {		
		ServerInstantiator.initialize();
		
		UserService user = (UserService) ServiceManager.getService("user");
		mAuthToken = user.getSpecialToken(0, IUserService.ROLE_ADMIN);
		
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		int uid = getUid();
		
		if (uid < 0) {
			int res = db.execute("INSERT INTO Users (cCpr, cName, cEmail, cToken) VALUES (?,?,?,?)",
					1234567878L,
					"Test",
					"test@domain.com",
					Utils.tokenToString(mToken)
			);
			
			if (res <= 0) {
				Assert.fail("Failed to setup test user in the database");
			}
			
			uid = getUid();
		}

		if (!hasRoles() && uid > 0) {
			int res = db.execute("INSERT INTO UserRoles (cUid, cLid, cRole) VALUES (?,?,?)", uid, 0, IUserService.ROLE_LOGIN);
			
			if (res <= 0) {
				Assert.fail("Failed to setup test user roles in the database");
			}
			
		} else if (uid <= 0) {
			Assert.fail("Failed to setup test user roles in the database");
		}
		
		// Make sure that byte/string parsing works as expected
		Assert.assertEquals(Utils.tokenToString(mToken), Utils.tokenToString(Utils.tokenToBytes(Utils.tokenToString(mToken))));
	}
	
	/**
	 * @throws SQLException 
	 * 
	 */
	@AfterClass
	public static void deinitialize() throws SQLException {
		int uid = getUid();
		
		if (uid > 0) {
			DatabaseService db = (DatabaseService) ServiceManager.getService("database");
			db.execute("DELETE FROM Users WHERE cUid = ?", uid);
			db.execute("DELETE FROM UserRoles WHERE cUid = ?", uid);
		}
	}
	
	/**
	 * 
	 */
	private static int getUid() {
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT cUid FROM Users WHERE cCpr = ?", mCpr);
		int uid = -1;
		
		try {
			if (result.next()) {
				uid = result.getInt("cUid");
			}
			
			result.close();
			
		} catch (SQLException ignore) {}
		
		return uid;
	}
	
	/**
	 * 
	 */
	private static boolean hasRoles() {
		int uid = getUid();
		
		if (uid < 0) {
			return false;
		}
		
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = db.query("SELECT COUNT(*) as cCount FROM UserRoles WHERE cLid = ? AND cUid = ?", 0, uid);
		
		try {
			if (result.next()) {
				return result.getInt("cCount") > 0;
			}
			
		} catch (SQLException ignore) {} finally {
			try {
				result.close();
				
			} catch (SQLException e) {}
		}
		
		return false;
	}
	
	/**
	 * 
	 */
	@Test
	public void checkTokenTest() {
		UserService user = (UserService) ServiceManager.getService("user");
		Assert.assertEquals(true, user.checkToken(mToken));
	}
	
	/**
	 * 
	 */
	@Test
	public void getTokenTest() {
		UserService user = (UserService) ServiceManager.getService("user");
		Assert.assertEquals(
				Utils.tokenToString(mToken),
				Utils.tokenToString(user.getUserToken(mCpr, mPasswd))
		);
	}
	
	/**
	 * 
	 */
	@Test
	public void checkPermissionsTest() {
		UserService user = (UserService) ServiceManager.getService("user");
		
		Assert.assertEquals(true, user.checkPermissions(
				mToken,
				0,
				IUserService.ROLE_LOGIN
		));
		
		Assert.assertEquals(true, user.checkPermissions(
				mToken,
				IUserService.ROLE_LOGIN
		));
	}
	
	/**
	 * 
	 */
	@Test
	public void getUserByCPRTest() {
		UserService service = (UserService) ServiceManager.getService("user");
		User user = service.getUserByCPR(mAuthToken, mCpr);
		
		Assert.assertNotNull(user);
		Assert.assertEquals(mCpr, user.cpr);
	}
	
	/**
	 * 
	 */
	@Test
	public void getUserByUIDTest() {
		UserService service = (UserService) ServiceManager.getService("user");
		User user = service.getUserByUID(mAuthToken, getUid());
		
		Assert.assertNotNull(user);
		Assert.assertEquals(mCpr, user.cpr);
	}
}
