package com.via.clms.server.services.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.via.clms.Log;
import com.via.clms.Utils;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.services.DatabaseService;
import com.via.clms.server.services.UserService;

/**
 * 
 */
public class DatabaseSetup {
	
	/** * */
	private static final String TPL_LIBRATIRES = "CREATE TABLE IF NOT EXISTS Libraries (\n" + 
			"    cLid serial NOT NULL,\n" + 
			"    cName varchar(255) NOT NULL,\n" +
			"    cLocation varchar(255) NOT NULL,\n" +
			"    PRIMARY KEY (cLid),\n" + 
			"    UNIQUE (cName)\n" + 
			");";
	
	/** * */
	private static final String TPL_USERS = "CREATE TABLE IF NOT EXISTS Users (\n" + 
			"    cUid serial NOT NULL,\n" + 
			"    cCpr bigint NOT NULL,\n" + 
			"    cName varchar(255) NOT NULL,\n" + 
			"    cEmail varchar(255) NOT NULL,\n" + 
			"    cToken varchar(255) NOT NULL,\n" + 
			"    PRIMARY KEY (cUid),\n" + 
			"    UNIQUE (cCpr,cToken)\n" + 
			");";
	
	/** * */
	private static final String TPL_USER_ROLES = "CREATE TABLE IF NOT EXISTS UserRoles (\n" + 
			"    cUid int NOT NULL,\n" + 
			"    cLid int NOT NULL,\n" + 
			"    cRole int NOT NULL\n" +
			");";
	
	/** * */
	private static final String TPL_BOOKS = "CREATE TABLE IF NOT EXISTS Books (\n" + 
			"  cBid serial NOT NULL,\n" + 
			"  cTitle varchar(255) NOT NULL,\n" + 
			"  cIsbn varchar(255) NOT NULL,\n" + 
			"  cDescription varchar(255) NOT NULL,\n" + 
			"  cImage varchar(255) NOT NULL,\n" + 
			"  cRelease bigint NOT NULL,\n" + 
			"  cAuthor varchar(255) NOT NULL,\n" + 
			"  PRIMARY KEY(cBid)\n" + 
			");";
	
	/** * */
	private static final String TPL_BOOK_INVENT = "CREATE TABLE IF NOT EXISTS BookInventory (\n" + 
			"  cBid int NOT NULL,\n" + 
			"  cLid int NOT NULL,\n" + 
			"  cInventory int NOT NULL,\n" + 
			"  cLocation varchar(256) DEFAULT NULL,\n" + 
			"  PRIMARY KEY (cBid)\n" + 
			");";
	
	/** * */
	private static final String TPL_BOOK_RENTAL = "CREATE TABLE IF NOT EXISTS BookRental (\n" + 
			"  cBid int NOT NULL,\n" + 
			"  cLid int NOT NULL,\n" + 
			"  cUid int NOT NULL,\n" + 
			"  cDateoffset bigint NOT NULL,\n" + 
			"  cDateduration bigint NOT NULL, \n" + 
			"  PRIMARY KEY(cBid)\n" + 
			");";
	
	/** * */
	private static final String TPL_BOOK_RESERV = "CREATE TABLE IF NOT EXISTS BookReservation (\n" + 
			"  cBid int NOT NULL,\n" + 
			"  cLid int NOT NULL,\n" + 
			"  cUid int NOT NULL,\n" + 
			"  PRIMARY KEY (cBid)\n" + 
			");";
	
	/** * */
	private static final String TPL_EXTRA = "" + 
			"CREATE TRIGGER bookinventory_zero AFTER UPDATE ON BookInventory FOR EACH ROW DELETE FROM BookRental WHERE BookRental.cBid = NEW.cBid AND NEW.cInventory = 0;\n" + 
			"CREATE TRIGGER bookinventory_zero2 AFTER UPDATE ON BookInventory FOR EACH ROW DELETE FROM BookReservation WHERE bookreservations.cBid = NEW.cBid AND NEW.cInventory = 0;";
	
	/** * */
	private static final String QU_TBL = "SELECT COUNT(*) as cCount \n" + 
			"FROM information_schema.tables \n" + 
			"WHERE table_schema = ? \n" + 
			"AND table_name = ?";
	
	/**
	 * 
	 */
	public static boolean configureTables(DatabaseService connection, String database) {
		boolean invent = false;
		
		if (!chkTable(connection, database, "Libraries")) {
			createTable(connection, TPL_LIBRATIRES);
		}
		
		if (!chkTable(connection, database, "Users")) {
			createTable(connection, TPL_USERS);
		}
		
		if (!chkTable(connection, database, "Books")) {
			createTable(connection, TPL_BOOKS);
		}
		
		if (!chkTable(connection, database, "UserRoles")) {
			createTable(connection, TPL_USER_ROLES);
		}
		
		if (!(invent = chkTable(connection, database, "BookInventory"))) {
			createTable(connection, TPL_BOOK_INVENT);
		}
		
		if (!chkTable(connection, database, "BookRental")) {
			createTable(connection, TPL_BOOK_RENTAL);
		}
		
		if (!chkTable(connection, database, "BookReservation")) {
			createTable(connection, TPL_BOOK_RESERV);
		}
		
		if (!invent) {
			// connection.execute(TPL_EXTRA);
		}
		
		ResultSet result = connection.query("SELECT COUNT(*) as cCount FROM Users WHERE cUid = 0");
		
		try {
			if (result.next() && result.getInt("cCount") == 0) {
				connection.execute("INSERT INTO Users (cUid, cCpr, cName, cEmail, cToken) VALUES (?,?,?,?,?)",
						0,
						0L,
						"Test",
						"test@domain.com",
						Utils.tokenToString(UserService.generateUserToken(0L, "test"))
				);
				
				connection.execute("INSERT INTO UserRoles (cUid, cLid, cRole) VALUES (?,?,?)", 0, 0, IUserService.ROLE_ADMIN);
			}
			
		} catch (SQLException e) {
			Log.error(e);
		}
		
		return true;
	}
	
	/**
	 * 
	 */
	private static boolean chkTable(DatabaseService connection, String database, String tpl) {
		/*ResultSet result = connection.query(QU_TBL, database, tpl);
		
		try {
			if (result.first()) {
				return result.getInt("cCount") > 0;
			}
			
		} catch (SQLException e) {}
		
		return false;*/
		
		return false;
	}
	
	/**
	 * 
	 */
	private static void createTable(DatabaseService connection, String sql) {
		connection.execute(sql);
	}
}
