package com.via.clms.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.via.clms.Log;
import com.via.clms.proxy.ILibraryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;
import com.via.clms.shared.Library;

public class LibraryServiceTest {

	public static byte[] token;

	@BeforeClass
	public static void initialize() throws SQLException, IllegalAccessException, InstantiationException {

		ServerInstantiator.initialize();

		UserService userService = (UserService) ServiceManager.getService("user");

		token = userService.getSpecialToken(0, IUserService.ROLE_ADMIN);

		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		db.execute("INSERT INTO Libraries (cName, cLocation) VALUES (?,?)", "TestLib7771", "SOF");

		ResultSet result = db.query("SELECT cName FROM Libraries WHERE cName = 'TestLib7771'");

		if (!result.next()) {
			Assert.fail("Could not retrieve lid from test library in database");
		}
	}

	@AfterClass
	public static void deinitialize() throws SQLException {

		DatabaseService db = (DatabaseService) ServiceManager.getService("database");
		db.execute("DELETE FROM Libraries WHERE cName = 'TestLib773'");
		db.execute("DELETE FROM Libraries WHERE cName = 'TestLib7771'");
		ServerInstantiator.deinitialize();
	}

	@Test
	public void createLibraryTest() throws RemoteException {
		ILibraryService libraryService = (LibraryService) ServiceManager.getService("library");
		boolean resultCreate = libraryService.createLibrary(token, "TestLib773", "FantasyLand");
		assertEquals(true, resultCreate);
	}

	@Test
	public void deleteLibraryTest() throws RemoteException {
		ILibraryService libraryService = (LibraryService) ServiceManager.getService("library");
		boolean resultDelete = libraryService.deleteLibrary(token, 1);
		assertEquals(true, resultDelete);
	}
	
	@Test
	public void getLibraryByNameTest() throws RemoteException {
		ILibraryService service = (ILibraryService) ServiceManager.getService("library");
		Library[] libraries = null;
		libraries = service.getLibraryByName("TestLib773");
		assertTrue(libraries != null);
	}


	@Test
	public void getLibraryByIDTest() throws RemoteException {
		ILibraryService service = (ILibraryService) ServiceManager.getService("library");
		Library[] libraries = null;
		libraries = service.getLibraryByLID(1);
		assertTrue(libraries[0] != null);
	}

	@Test
	public void getLibraries() throws RemoteException {
		ILibraryService libraryService = (ILibraryService) ServiceManager.getService("library");
		Library[] libraries = libraryService.getLibraries(0, 1);
		assertTrue(libraries != null);
	}
}