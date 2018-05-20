package com.via.clms.server.services;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.LLOAD;
import com.via.clms.proxy.ILibraryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;
import com.via.clms.shared.Library;

/**
 * Library service that can be used to manage libraries
 */
public class LibraryService implements ILibraryService, Service {
	
	DatabaseService dbs;

	public LibraryService() {
		dbs = new DatabaseService();
	}
	
	@Override
	public boolean createLibrary(byte[] reqToken, String name) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, 0, IUserService.ROLE_ADMIN)) {
			return false;
		}
		String q = "INSERT INTO 'Libraries' (cName)VALUES (?);";
		int result = dbs.execute(q, name);
		if (result == 1) {
			return true;
		} else {
				return false;
			}
		}
	
	@Override
	public Library getLibraryByLID(byte[] reqToken, int lid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, 0, IUserService.ROLE_ADMIN)) {
			return null;
		}
		String q = "SELECT * FROM 'Libraries' WHERE 'lid' = ?;";
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
		String q = "SELECT * FROM 'Library' OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
		ResultSet result = dbs.query(q, offset, length);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShutdown() {
		
	}

}
