package com.via.clms.server.services;

import static org.junit.Assert.assertEquals;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.via.clms.server.ServiceManager;
import com.via.clms.proxy.ILibraryService;
import com.via.clms.proxy.IUserService;

public class LibraryServiceTesting {
	
	public static byte[] token;
	
	@BeforeClass
	public static void initialize() throws SQLException, IllegalAccessException, InstantiationException {
		
		ServerInstantiator.initialize();
		
		UserService userService = (UserService) ServiceManager.getService("user");
		
		token = userService.getSpecialToken(0, IUserService.ROLE_ADMIN);
		
		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		int res = db.execute("INSERT INTO Libraries (cLid, cName, cLocation) VALUES (?,?,?)",
				0,
				"CLMS",
				"Horsens");
		
		if (res <= 0) {
			Assert.fail("Failed to setup test user in the database");
		}
		
		ResultSet result = db.query("SELECT cLid FROM Libraries WHERE cLid = 0");
		
		if (!result.next()) {
			Assert.fail("Could not retrieve lid from test library in database");
		}
	}
	
	@Test
	public void testCreateLibrary() throws RemoteException {	
	UserService userService = (UserService) ServiceManager.getService("user");
	ILibraryService libraryService = (LibraryService) ServiceManager.getService("library");
	boolean result = libraryService.createLibrary(token, "CLMS");
	assertEquals(true, result);
	}
}