package com.via.clms.server.services;

import com.via.clms.proxy.IInventoryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.ServiceManager;
import com.via.clms.shared.Book;
import com.via.clms.shared.BookRental;
import com.via.clms.shared.BookReservation;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InventoryService implements IInventoryService, Service {
	DatabaseService dbs;

	@Override
	public BookRental[] getRentalsByBID(byte[] reqToken, int lid, int bid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_BOOKMGR)) {
			return null;
		}
		String q = "SELECT * FROM `BookRental` WHERE `cBid` = ? AND `cLid` = ?;";
		ResultSet result = dbs.query(q, bid, lid);
		ArrayList<BookRental> brList = new ArrayList<>();
		try {
			while (result.next()) {
				int nBid = result.getInt(1);
				int nLid = result.getInt(2);
				int nUid = result.getInt(3);
				long nOffset = result.getLong(4);
				long nDuration = result.getLong(5);

				brList.add(new BookRental(nBid, nUid, nLid, nOffset, nDuration));
			}
			BookRental[] brArray = new BookRental[brList.size()];
			brList.toArray(brArray);
			return brArray;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public BookReservation[] getReservationsByUID(byte[] reqToken, int lid, int uid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}
		String q = "SELECT * FROM `BookReservation` WHERE `cUid` = ? AND `cLid` = ?;";
		ResultSet result = dbs.query(q, uid, lid);
		ArrayList<BookReservation> brList = new ArrayList<>();
		try {
			while (result.next()) {
				int nBid = result.getInt(1);
				int nLid = result.getInt(2);
				int nUid = result.getInt(3);

				brList.add(new BookReservation(nBid, nUid, nLid));
			}
			BookReservation[] brArray = new BookReservation[brList.size()];
			brList.toArray(brArray);
			return brArray;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public BookReservation[] getReservationsByBID(byte[] reqToken, int lid, int bid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_BOOKMGR)) {
			return null;
		}
		String q = "SELECT * FROM `BookReservation` WHERE `cBid` = ? AND `cLid` = ?;";
		ResultSet result = dbs.query(q, bid, lid);
		ArrayList<BookReservation> brList = new ArrayList<>();
		try {
			while (result.next()) {
				int nBid = result.getInt(1);
				int nLid = result.getInt(2);
				int nUid = result.getInt(3);

				brList.add(new BookReservation(nBid, nUid, nLid));
			}
			BookReservation[] brArray = new BookReservation[brList.size()];
			brList.toArray(brArray);
			return brArray;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	@Override
	public int addBook(byte[] reqToken, int lid, Book book) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_BOOKMGR)) {
			return -1;
		}

		if (book.bid == -1) {
			return fullAddBook(lid, book);
		} else {
			return simpleAddBook(lid, book);
		}

	}

	private int fullAddBook(int lid, Book book) throws RemoteException {
		String q = "INSERT INTO `Books` (cTitle, cISBN, cDescription, cImage, cRelease, cAuthor) VALUES(?, ?, ?, ?, ?, ?);";
		int r = dbs.execute(q, book.title, book.ISBN, book.description, book.image, book.release, book.author);
		int s = simpleAddBook(lid, book);
		return r * s;
	}

	private int simpleAddBook(int lid, Book book) {
		String q = "UPDATE `BookInventory` SET `cInventory` = `cInventory + 1` WHERE `cBid` = ?;";
		return dbs.execute(q, book.bid);
	}

	@Override
	public int removeBook(byte[] reqToken, int lid, int bid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_BOOKMGR)) {
			return -1;
		}
		String q = "UPDATE `BookInventory` SET `cInventory` = `cInventory - 1` WHERE `cBid` = ? AND `cLid` = ? AND `cInventory` != 0;";
		return dbs.execute(q, bid, lid);
	}

	@Override
	public int addReservation(byte[] reqToken, int lid, int bid, int uid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return -1;
		}

		String q = "INSERT INTO `BookReservations` VALUES(?, ?, ?);";
		return dbs.execute(q, bid, lid, uid);
	}

	private Book[] bookArrayBuild(ResultSet result) {
		try {
			ArrayList<Book> bookList = new ArrayList<>();
			while (result.next()) {
				int bid = result.getInt(1);
				String q = "SELECT `cInventory`,`cLocation` FROM `BookInventory` WHERE `cBid`=?;";
				ResultSet rs = dbs.query(q, bid);
				if(rs.next()) {
					String title = result.getString(2);
					int inventory = rs.getInt(1);
					String isbn = result.getString(3);
					String desc = result.getString(4);
					long release = result.getLong(6);
					String author = result.getString(7);
					String location = rs.getString(2);
					bookList.add(new Book(bid, title, inventory, isbn, desc, release, author, location));
				}
			}
			Book[] bookArray = new Book[bookList.size()];
			bookList.toArray(bookArray);
			return bookArray;
		} catch(Exception e) {
			return null;
		}
	}

	public Book[] getAllBooks(byte[] reqToken, int offset, int length) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}

		String q = "SELECT * FROM `BookInventory` OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
		ResultSet result = dbs.query(q, offset, length);
		return bookArrayBuild(result);
	}

	@Override
	public Book[] getBooks(byte[] reqToken, int lid, int offset, int length) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}
		if (lid == 0) {
			return getAllBooks(reqToken, offset, length);
		}

		String q = "SELECT * FROM `BookInventory` WHERE 'cLid' = ?  OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
		ResultSet result = dbs.query(q, lid, offset, length);
		return bookArrayBuild(result);
	}

	@Override
	public Book[] getBooksByTitle(byte[] reqToken, int lid, String title) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}
		String q = "SELECT * FROM `BookInventory` WHERE `cLid` = ? AND `cTitle` = ?;";
		ResultSet result = dbs.query(q, lid, title);
		return bookArrayBuild(result);
	}

	@Override
	public Book getBookByISBN(byte[] reqToken, int lid, String isbn) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}
		String q = "SELECT FROM `BookInventory` WHERE `cLid` = ? AND `cIsbn` = ?;";
		ResultSet result = dbs.query(q, lid, isbn);
		Book[] books = bookArrayBuild(result);
		if(books.length < 1) {
			return null;
		}
		return books[0];
	}

	@Override
	public Book[] getBooksByDate(byte[] reqToken, int lid, long timeLength) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}
		String q = "SELECT * FROM `BookInventory` WHERE `cLid` = ? AND `cRelease` BETWEEN ? AND ?;";

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(timeLength));
		int year = c.get(Calendar.YEAR);
		c.setTime(new Date(0));
		c.set(Calendar.YEAR, year);
		long begin = c.getTime().getTime();
		c.set(Calendar.YEAR, year + 1);
		long end = c.getTime().getTime();

		ResultSet result = dbs.query(q, lid, begin, end);
		return bookArrayBuild(result);
	}

	@Override
	public Book getBookByBID(byte[] reqToken, int bid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}

		String q = "SELECT FROM `BookInventory` WHERE `cBid` = ?;";
		ResultSet result = dbs.query(q, bid);
		Book[] books = bookArrayBuild(result);
		if(books.length < 1) {
			return null;
		}
		return books[0];
	}

	@Override
	public BookRental[] getRentalsByUID(byte[] reqToken, int lid, int uid) throws RemoteException {
		IUserService userService = (IUserService) ServiceManager.getService("user");
		if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_USERMGR)) {
			return null;
		}
		String q = "SELECT * FROM `BookRental` WHERE `cLid` = ? AND `cUid` = ?;";
		ResultSet result = dbs.query(q, lid, uid);
		ArrayList<BookRental> brList = new ArrayList<>();
		try {
			while (result.next()) {
				int nBid = result.getInt(1);
				int nUid = result.getInt(2);
				int nLid = result.getInt(3);
				long nOffset = result.getLong(4);
				long nDuration = result.getLong(5);

				brList.add(new BookRental(nBid, nUid, nLid, nOffset, nDuration));
			}
			BookRental[] brArray = new BookRental[brList.size()];
			brList.toArray(brArray);
			return brArray;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean onConfigure() {
		dbs = (DatabaseService) ServiceManager.getService("database");
		return true;
	}

	@Override
	public void onShutdown() {

	}
}
