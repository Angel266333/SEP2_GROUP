package com.via.clms.server.services;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.via.clms.proxy.ILibraryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;
import com.via.clms.shared.Library;

/**
 * Library service that can be used to manage libraries
 */
public class LibraryService implements ILibraryService, Service {
	
	@Override
	public boolean createLibrary(byte[] reqToken, String name, String location) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, 0, IUserService.ROLE_ADMIN)) {
			return false;
		}
		String q = "INSERT INTO Libraries (cName, cLocation)VALUES (?, ?);";
		DatabaseService dbs = (DatabaseService) ServiceManager.getService("database");
		int result = dbs.execute(q, name, location);
		if (result == 1) {
			return true;
		} else {
				return false;
			}
		}
	@Override
		public boolean deleteLibrary(int lid) throws RemoteException {
		String q = "DELETE FROM Libraries WHERE cLid = ?;";
		DatabaseService dbs = (DatabaseService) ServiceManager.getService("database");
		int result = dbs.execute(q, lid);
		if (result == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public Library getLibraryByLID(byte[] reqToken, int lid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, 0, IUserService.ROLE_ADMIN)) {
			return null;
		}
		String q = "SELECT * FROM Libraries WHERE cLid = ?;";
		DatabaseService dbs = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = dbs.query(q, lid);
		try {
			while(result.next()) {
				int lLid = result.getInt(1);
				String lName = result.getString(2);
				String lLocation = result.getString(3);
				return new Library(lLid, lName, lLocation);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return null;
	}

	@Override
	public Library[] getLibraries(byte[] reqToken, int offset, int length) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, 0, IUserService.ROLE_ADMIN)) {
			return null;
		}
		// "SELECT * FROM Users LIMIT ?,?", offset, length
		String q = "SELECT * FROM Libraries LIMIT ? OFFSET ?";
		DatabaseService dbs = (DatabaseService) ServiceManager.getService("database");
		ResultSet result = dbs.query(q, length, offset);
		ArrayList<Library> lbrList = new ArrayList<>();
		try {
			while(result.next()) {
			int lLid = result.getInt(1);
			String lName = result.getString(2);
			String lLocation = result.getString(3);
			lbrList.add(new Library(lLid, lName, lLocation));
			}
			Library[] lbrArray = new Library[lbrList.size()];
			lbrList.toArray(lbrArray);
			return lbrArray;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Override
	public boolean onConfigure() {
		return true;
	}

	@Override
	public void onShutdown() {
	}

}
