package com.via.clms.server.services;


import com.via.clms.Utils;
import com.via.clms.client.ServiceManager;
import com.via.clms.proxy.IInventoryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.shared.Book;
import com.via.clms.shared.BookRental;
import com.via.clms.shared.BookReservation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class InventoryServiceTest {
	static IInventoryService service;
	static byte[] token;
	static DatabaseService dbs;

	@BeforeClass
	public static void setup() {
		try {
			ServerInstantiator.initialize();
		} catch(Exception e) {
			e.printStackTrace();
		}
		service = (IInventoryService) ServiceManager.getService("inventory");
		dbs = (DatabaseService) com.via.clms.server.ServiceManager.getService("database");
	}

	@Before
	public void setDatabase() {
		dbs.execute("TRUNCATE users");
		dbs.execute("TRUNCATE userroles");
		dbs.execute("TRUNCATE libraries");
		dbs.execute("TRUNCATE books");
		dbs.execute("TRUNCATE bookreservation");
		dbs.execute("TRUNCATE bookrental");
		dbs.execute("TRUNCATE bookinventory");
		token = UserService.generateUserToken(123456, "asdf");
		String q = "INSERT INTO `users` (`cUid`, `cCpr`, `cName`, `cEmail`, `cToken`) VALUES (1, 123456, 'John', 'John', ?)";
		dbs.execute(q, Utils.tokenToString(token));
		q = "INSERT INTO `userroles` (`cUid`, `cLid`, `cRole`) VALUES (1, 1, ?);";
		dbs.execute(q, IUserService.ROLE_ADMIN);
		q = "INSERT INTO `userroles` (`cUid`, `cLid`, `cRole`) VALUES (1, 2, ?);";
		dbs.execute(q, IUserService.ROLE_ADMIN);
		q = "INSERT INTO `libraries` (`cName`, `cLocation`) VALUES ('abc', 'here');";
		dbs.execute(q);
		q = "INSERT INTO `libraries` (`cName`, `cLocation`) VALUES ('def', 'here');";
		dbs.execute(q);
	}

	@Test
	public void addGetRemoveBook() {
		Book bookx = new Book(-1, "xyz", -1, "1000", "abc", 2000L, "john", null);
		bookx.image = "";
		Book book;
		try {
			int i = service.addBook(token, 1, bookx);

			bookx.ISBN = "9999";
			bookx.title = "qwer";
			int j = service.addBook(token, 1, bookx);

			book = service.getBookByISBN(token, 1, "1000");
			assertTrue(book.title.equals("xyz"));
			assertTrue(book.ISBN.equals("1000"));
			assertEquals(i, book.bid);

			book = service.getBookByBID(token, i);
			assertTrue(book.title.equals("xyz"));
			assertTrue(book.ISBN.equals("1000"));
			assertEquals(i, book.bid);

			book = service.getBooksByTitle(token, 1, "xyz")[0];
			assertTrue(book.title.equals("xyz"));
			assertTrue(book.ISBN.equals("1000"));
			assertEquals(i, book.bid);

			book = service.getBookByISBN(token, 1, "9999");
			assertTrue(book.title.equals("qwer"));
			assertTrue(book.ISBN.equals("9999"));
			assertEquals(j, book.bid);

			book = service.getBookByBID(token, j);
			assertTrue(book.title.equals("qwer"));
			assertTrue(book.ISBN.equals("9999"));
			assertEquals(j, book.bid);

			book = service.getBooksByTitle(token, 1, "qwer")[0];
			assertTrue(book.title.equals("qwer"));
			assertTrue(book.ISBN.equals("9999"));
			assertEquals(j, book.bid);

			Book[] b = service.getBooksByDate(token, 1, 500);
			assertEquals(i, b[0].bid);
			assertEquals(j, b[1].bid);

			b = service.getBooks(token, 1, 0, 10);
			assertTrue(b[0].title.equals("xyz"));
			assertTrue(b[0].ISBN.equals("1000"));
			assertEquals(i, b[0].bid);
			assertEquals(1, b[0].inventory);

			assertTrue(b[1].title.equals("qwer"));
			assertTrue(b[1].ISBN.equals("9999"));
			assertEquals(j, b[1].bid);
			assertEquals(1, b[1].inventory);

			service.removeBook(token, 1, i);
			service.removeBook(token, 1, j);
			b = service.getBooks(token, 1, 0, 10);
			assertEquals(0, b[0].inventory);
			assertEquals(0, b[1].inventory);

			bookx.ISBN = "500";
			service.addBook(token, 2, bookx);
			boolean check = false;
			for(Book a : service.getAllBooks(token, 0, 10)) {
				if(a.ISBN.equals("500")) {
					check = true;
				}
			}
			assertTrue(check);


		} catch(RemoteException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void addGetRemoveRentals() {
		try {
			Book bookx = new Book(-1, "xyz", -1, "1000", "abc", 2000L, "john", null);
			bookx.image = "";
			service.addBook(token, 1, bookx);
			bookx.ISBN = "9999";
			bookx.title = "qwer";
			service.addBook(token, 1, bookx);
			bookx.ISBN = "300";
			bookx.title = "asdf";
			service.addBook(token, 2, bookx);

			service.addRental(token, 1, 1, 1);
			service.addRental(token, 1, 2, 1);
			service.addRental(token, 2, 3, 1);

			BookRental[] rentals = service.getRentalsByUID(token, 1, 1);
			assertEquals(1, rentals[0].bid);
			assertEquals(2, rentals[1].bid);

			rentals = service.getRentalsByUID(token, 2, 1);
			assertEquals(3, rentals[0].bid);

			rentals = service.getRentalsByBID(token, 1, 1);
			assertEquals(1, rentals[0].uid);
			rentals = service.getRentalsByBID(token, 2, 3);
			assertEquals(1, rentals[0].uid);

			service.removeRental(token, 3, 2, 1);
			rentals = service.getRentalsByBID(token, 2, 3);
			assertEquals(0, rentals.length);
		} catch(RemoteException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addGetRemoveReservation() {
		try {
			Book bookx = new Book(-1, "xyz", -1, "1000", "abc", 2000L, "john", null);
			bookx.image = "";
			service.addBook(token, 1, bookx);
			bookx.ISBN = "9999";
			bookx.title = "qwer";
			service.addBook(token, 1, bookx);
			bookx.ISBN = "300";
			bookx.title = "asdf";
			service.addBook(token, 2, bookx);

			service.addReservation(token, 1, 1, 1);
			service.addReservation(token, 2, 3, 1);

			BookReservation[] reservations = service.getReservationsByUID(token, 1, 1);
			assertEquals(1, reservations[0].bid);
			reservations = service.getReservationsByUID(token, 2, 1);
			assertEquals(3, reservations[0].bid);

			reservations = service.getReservationsByBID(token, 1, 1);
			assertEquals(1, reservations[0].uid);
			reservations = service.getReservationsByBID(token, 2, 3);
			assertEquals(1, reservations[0].uid);

			service.removeReservation(token, 1, 1, 1);
			service.removeReservation(token, 2, 3, 1);

			reservations = service.getReservationsByBID(token, 1, 1);
			assertEquals(0, reservations.length);
			reservations = service.getReservationsByBID(token, 2, 3);
			assertEquals(0, reservations.length);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
