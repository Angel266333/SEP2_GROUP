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
import java.sql.Timestamp;
import java.time.*;
import java.util.*;

public class InventoryService implements IInventoryService, Service {
	DatabaseService dbs;

	@Override
	public BookRental[] getRentalsByBID(byte[] reqToken, int lid, int bid) throws RemoteException {
		 IUserService userService = (IUserService) ServiceManager.getService("user");
		 if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_BOOKMGR))
		 {
		 return null;
		 }
		String q = "SELECT * FROM BookRental WHERE cBid = ? AND cLid = ?;";
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
		String q = "SELECT * FROM BookReservation WHERE cUid = ? AND cLid = ?;";
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
		 if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_BOOKMGR))
		 {
		 return null;
		 }
		String q = "SELECT * FROM BookReservation WHERE cBid = ? AND cLid = ?;";
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
		if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_BOOKMGR))
		{
			return -1;
		}

		if (book.bid == -1) {
			return fullAddBook(lid, book);
		} else {
			return simpleAddBook(lid, book);
		}

	}

	private int fullAddBook(int lid, Book book) throws RemoteException {
		String q = "INSERT INTO Books (cTitle, cISBN, cDescription, cImage, cRelease, cAuthor) VALUES(?, ?, ?, ?, ?, ?);";
		int i = dbs.executeReturnKey(q, book.title, book.ISBN, book.description, book.image, book.release, book.author);
		book.bid = i;

		q = "SELECT cLocation FROM libraries WHERE cLid=?;";
		ResultSet rs = dbs.query(q, lid);
		try {
			if(!rs.next()) {
				return -1;
			}
			q = "INSERT INTO BookInventory VALUES(?,?,?,?);";
			i = dbs.execute(q, book.bid, lid, 1, rs.getString(1));
			if(i == 0) {
				return -1;
			}
		} catch(SQLException e) {
			return -1;
		}
		return book.bid;
	}

	private int simpleAddBook(int lid, Book book) {
		String q = "UPDATE BookInventory SET cInventory = cInventory + 1 WHERE cBid = ?;";
		int i = dbs.execute(q, book.bid);
		if(i == 0) {
			return 0;
		}
		return book.bid;
	}

	@Override
	public int removeBook(byte[] reqToken, int lid, int bid) throws RemoteException {
		 IUserService userService = (IUserService) ServiceManager.getService("user");
		 if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_BOOKMGR))
		 {
		 return -1;
		 }
		String q = "UPDATE BookInventory SET cInventory = cInventory - 1 WHERE cBid = ? AND cLid = ? AND cInventory != 0;";
		return dbs.execute(q, bid, lid);
	}

	@Override
	public int addReservation(byte[] reqToken, int lid, int bid, int uid) throws RemoteException {
		UserService userService = (UserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return -1;
		}

		String q = "INSERT INTO BookReservation VALUES(?, ?, ?);";
		return dbs.execute(q, bid, lid, uid);
	}

	@Override
	public int addRental(byte[] reqToken, int lid, int bid, int uid) throws RemoteException {
		UserService uService = (UserService) ServiceManager.getService("user");
		if(!uService.checkToken(reqToken)) {
			return -1;
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		LocalDate ld = LocalDate.of(year, month, day);
		long offset = ld.atStartOfDay().atZone(ZoneOffset.UTC).toEpochSecond();
		long length = 31 * 24 * 3600;


		String q = "SELECT cInventory FROM BookInventory WHERE cBid=? AND cLid=?;";
		ResultSet rs = dbs.query(q, bid, lid);
		try {
			if(!rs.next()) {
				return -1;
			}
			int inv = rs.getInt(1);
			q = "SELECT COUNT(*) FROM BookRental WHERE cBid=? AND cLid=?;";
			rs = dbs.query(q, bid, lid);
			if(!rs.next()) {
				return -1;
			}
			int rents = rs.getInt(1);
			if(rents >= inv) {
				return -1;
			}
		} catch(SQLException e) {
			return -1;
		}

		q = "INSERT INTO BookRental (cBid, cLid, cUid, cDateoffset, cDateduration) VALUES(?,?,?,?,?);";
		return dbs.execute(q, bid, lid, uid, offset, length);
	}

	@Override
	public int removeReservation(byte[] reqToken, int lid, int bid, int uid) throws RemoteException {
		UserService userService = (UserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return -1;
		}
		String q = "DELETE FROM BookReservation WHERE cBid=? AND cLid=? AND cUid=?;";
		return dbs.execute(q, bid, lid, uid);
	}

	@Override
	public int removeRental(byte[] reqToken, int bid, int lid, int uid) throws RemoteException {
		String q = "DELETE FROM BookRental WHERE cBid=? AND cLid=? AND cUid=?;";
		return dbs.execute(q, bid, lid, uid);
	}

	private Book[] bookArrayBuild(ResultSet result) {
		try {
			ArrayList<Book> bookList = new ArrayList<>();
			while (result.next()) {
				int bid = result.getInt(1);
				String q = "SELECT * FROM Books WHERE cBid=?;";
				ResultSet rs = dbs.query(q, bid);
				if (rs.next()) {
					String title = rs.getString(2);
					int inventory = result.getInt(3);
					String isbn = rs.getString(3);
					String desc = rs.getString(4);
					long release = rs.getLong(6);
					String author = rs.getString(7);
					String location = result.getString(4);
					bookList.add(new Book(bid, title, inventory, isbn, desc, release, author, location));
				}
			}
			Book[] bookArray = new Book[bookList.size()];
			bookList.toArray(bookArray);
			return bookArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Book[] getAllBooks(byte[] reqToken, int offset, int length) throws RemoteException {
		UserService userService = (UserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}

		String q = "SELECT * FROM Books LIMIT ? OFFSET ?;";
		ResultSet result = dbs.query(q, length, offset);
		ArrayList<Book> books = new ArrayList<>();
		try {
			while(result.next()) {
				int bid = result.getInt(1);
				String title = result.getString(2);
				String Isbn = result.getString(3);
				String desc = result.getString(4);
				String img = result.getString(5);
				long rls = result.getLong(6);
				String author = result.getString(7);
				Book book = new Book(bid, title, -1, Isbn, desc, rls, author, null);
				books.add(book);
			}
			Book[] res = new Book[books.size()];
			books.toArray(res);
			return res;
		} catch(Exception e) {
			return null;
		}
	}

	@Override
	public Book[] getBooks(byte[] reqToken, int lid, int offset, int length) throws RemoteException {
		 UserService userService = (UserService) ServiceManager.getService("user");
		 if (!userService.checkToken(reqToken)) {
		 return null;
		 }
		if (lid == 0) {
			return getAllBooks(reqToken, offset, length);
		}

		String q = "SELECT * FROM BookInventory WHERE cLid = ?  LIMIT ? OFFSET ?;";
		ResultSet result = dbs.query(q, lid, length, offset);
		return bookArrayBuild(result);
	}

	@Override
	public Book[] getBooksByTitle(byte[] reqToken, int lid, String title) throws RemoteException {
		UserService userService = (UserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}
		String q = "SELECT * FROM BookInventory WHERE cLid=?;";
		ResultSet rs = dbs.query(q, lid);
		Book[] books = bookArrayBuild(rs);
		ArrayList<Book> result = new ArrayList<>();
		for (Book b : books) {
			if (b.title.equals(title)) {
				result.add(b);
			}
		}
		Book[] r = new Book[result.size()];
		result.toArray(r);
		return r;
	}

	@Override
	public Book getBookByISBN(byte[] reqToken, int lid, String isbn) throws RemoteException {
		 UserService userService = (UserService) ServiceManager.getService("user");
		 if (!userService.checkToken(reqToken)) {
		 return null;
		 }
		String q = "SELECT * FROM Books WHERE cIsbn=? LIMIT 1;";
		ResultSet rs = dbs.query(q, isbn);
		try {
			if (!rs.next()) {
				return null;
			}
			return new Book(rs.getInt(1), rs.getString(2), -1, isbn, rs.getString(4), rs.getLong(6), rs.getString(7), null);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Book[] getBooksByDate(byte[] reqToken, int lid, long timeLength) throws RemoteException {
		UserService userService = (UserService) ServiceManager.getService("user");
		if (!userService.checkToken(reqToken)) {
			return null;
		}

		LocalDateTime ldt = LocalDateTime.ofEpochSecond(timeLength, 0, ZoneOffset.UTC);
		int year = ldt.getYear();
		ldt = LocalDate.of(year, 1, 1).atStartOfDay();
		long begin = ldt.atZone(ZoneOffset.UTC).toEpochSecond();
		ldt = LocalDate.of(year + 1, 1, 1).atStartOfDay();
		long end = ldt.atZone(ZoneOffset.UTC).toEpochSecond();

		try {
			String q = "SELECT cBid FROM Books WHERE cRelease BETWEEN ? AND ?;";
			ResultSet rs = dbs.query(q, begin, end);
			ArrayList<Integer> bids = new ArrayList<>();
			while (rs.next()) {
				bids.add(rs.getInt(1));
			}

			q = "SELECT * FROM BookInventory WHERE cLid=?;";
			rs = dbs.query(q, lid);
			Book[] books = bookArrayBuild(rs);

			ArrayList<Book> br = new ArrayList<>();
			for (Book b : books) {
				if (bids.contains(b.bid)) {
					br.add(b);
				}
			}

			if (br.isEmpty()) {
				return null;
			}

			books = new Book[br.size()];
			br.toArray(books);
			return books;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Book getBookByBID(byte[] reqToken, int bid) throws RemoteException {
		 UserService userService = (UserService) ServiceManager.getService("user");
		 if (!userService.checkToken(reqToken)) {
		 return null;
		 }

		String q = "SELECT * FROM Books WHERE cBid=?;";
		ResultSet rs = dbs.query(q, bid);
		try {
			if (!rs.next()) {
				return null;
			}
			String title = rs.getString(2);
			String isbn = rs.getString(3);
			String desc = rs.getString(4);
			long release = rs.getLong(6);
			String author = rs.getString(7);
			return new Book(bid, title, -1, isbn, desc, release, author, null);

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public BookRental[] getRentalsByUID(byte[] reqToken, int lid, int uid) throws RemoteException {
		 UserService userService = (UserService) ServiceManager.getService("user");
		 if (!userService.checkPermissions(reqToken, lid, IUserService.ROLE_USERMGR))
		 {
		 return null;
		 }
		String q = "SELECT * FROM BookRental WHERE cLid = ? AND cUid = ?;";
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
