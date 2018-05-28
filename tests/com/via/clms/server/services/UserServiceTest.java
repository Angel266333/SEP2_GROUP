package com.via.clms.server.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.via.clms.Utils;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;

/**
 * 
 */
public class UserServiceTest {
	
	/**
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	@BeforeClass
	public static void initialize() throws InstantiationException, IllegalAccessException, SQLException {
		
		ServerInstantiator.initialize();
		
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		int res = db.execute("INSERT INTO Users (cCpr, cName, cEmail, cToken) VALUES (?,?,?,?)",
				1234567878L,
				"Test",
				"test@domain.com",
				Utils.tokenToString(
						UserService.generateUserToken(1234567878L, "123456")	
				)
		);
		
		if (res <= 0) {
			Assert.fail("Failed to setup test user in the database");
		}
		
		ResultSet result = db.query("SELECT cUid FROM Users WHERE cCpr = ?", 1234567878L);
		
		if (!result.next()) {
			Assert.fail("Could not retrieve uid from test user in database");
		}
		
		int uid = result.getInt("cUid");
		res = db.execute("INSERT INTO UserRoles (cUid, cLid, cRole) VALUES (?,?,?)", 0, uid, IUserService.ROLE_LOGIN);
		
		if (res <= 0) {
			Assert.fail("Failed to setup test user roles in the database");
		}
	}
	
	/**
	 * @throws SQLException 
	 * 
	 */
	@AfterClass
	public static void deinitialize() throws SQLException {
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		
		ResultSet result = db.query("SELECT cUid FROM Users WHERE cCpr = ?", 1234567878L);
		
		if (!result.next()) {
			Assert.fail("Could not retrieve uid from test user in database");
		}
		
		int uid = result.getInt("cUid");
		int res = db.execute("DELETE FROM Users WHERE cUid = ?", uid);
		int res2 = db.execute("DELETE FROM Users WHERE cUid = ?", uid);
		
		ServerInstantiator.deinitialize();
		
		if (res <= 0) {
			Assert.fail("Failed to remove test user from the database");
		}
		
		if (res2 <= 0) {
			Assert.fail("Failed to remove test user roles from the database");
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void checkTokenTest() {
		UserService user = (UserService) ServiceManager.getService("user");
		Assert.assertEquals(true, user.checkToken(
				UserService.generateUserToken(1234567878L, "123456")
		));
	}
	
	/**
	 * 
	 */
	@Test
	public void getTokenTest() {
		UserService user = (UserService) ServiceManager.getService("user");
		Assert.assertEquals(
				UserService.generateUserToken(1234567878L, "123456"),
				user.getUserToken(1234567878L, "123456")
		);
	}
	
	/**
	 * 
	 */
	@Test
	public void checkPermissionsTest() {
		UserService user = (UserService) ServiceManager.getService("user");
		Assert.assertEquals(true, user.checkPermissions(
				UserService.generateUserToken(1234567878L, "123456"),
				0,
				IUserService.ROLE_LOGIN
		));
	}
}
