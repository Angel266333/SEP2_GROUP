package com.via.clms.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.junit.BeforeClass;
import org.junit.Test;

import com.via.clms.proxy.IInventoryService;
import com.via.clms.shared.Book;
import com.via.clms.shared.BookRental;
import com.via.clms.shared.User;

class InventoryTest {
	static IInventoryService service;
	
	@BeforeClass
	public static void setup() throws RemoteException, NotBoundException {
		service = (IInventoryService) LocateRegistry.getRegistry(2019).lookup("inventory");
	}
	
	/*
	 * Creating objects needed for testing
	 */
	InventoryService inventory = new InventoryService();
	
	//Attributes for the books: (ID, Title, ISBN, Description, Image, Release, Author, location)
	Book b1 = new Book(-1, "Title1", 1111, "Description1", null, 100000, "Author1", "Location1");
	Book b2 = new Book(-1, "Title2", 2222, "Description2", null, 200000, "Author2", "Location2");
	Book b3 = new Book(-1, "Title3", 3333, "Description3", null, 300000, "Author3", "Location3");
	Book b4 = new Book(1, "Title1", 4444, "Description1", null, 100000, "Author1", "Location1");
	Book b5 = new Book(1, "Title1", 5555, "Description1", null, 100000, "Author1", "Location1");
	Book b6 = new Book(6, "Title6", 6666, "Description6", null, 600000, "Author6", "Location2");
	

	//existing users
	User u1 = new User(1);
	User u2 = new User(2);
	
	// new users
	User u3 = new User();
	User u4 = new User();
	
	//Some libraries must be added to the DataBase first
	
	
	/*
	 * Testing
	 */
	
	/*
	 * TODO test passing the wrong token to all the methods
	 */


	@Test
	void addBook() throws RemoteException {
		
		int test = inventory.addBook(null, 1, b1); 
		assertEquals(1,test);
	}
	
	
	@Test
	void fullAddBook() throws RemoteException {
		int test = inventory.addBook(null, 1, b1);
		assertEquals(1,test);
		
		test = inventory.addBook(null, 1, b6);
		assertEquals(1,test);

		
	}
		
	@Test
	void simpleAddBook() throws RemoteException {
		
		int test = inventory.addBook(null, 1, b4);
		assertEquals(1,test);
		
	}

		
	@Test
	public void getBooks() throws RemoteException {
		Book[] books = service.getBooks(null, 1, 0, 3);
		assertEquals(3, books.length);

		books = service.getBooks(null, 1, 0, 10);
		assertEquals(4, books.length);
	}
		

	@Test
	public void getBooksByTitle() throws RemoteException {
		Book[] books = service.getBooksByTitle(null, 1, "Shit book");
		assertEquals(2, books.length);

		assertEquals("Shit book", books[0].title);
		assertEquals("Shit book", books[1].title);
		assertNotEquals(books[0].ISBN, books[1].ISBN);
	}

	@Test
	public void getBookByISBN() throws RemoteException {
		Book b = service.getBookByISBN(null, 1, "444545444");
		assertNotEquals(null, b);
		assertEquals("444545444", b.ISBN);
		assertEquals("Lone", b.title);
		assertEquals("Lone", b.description);
	}

	@Test
	public void getRentalsByUID() throws RemoteException {
		BookRental[] rentals = service.getRentalsByUID(null, 2, 100);
		assertEquals(1, rentals.length);
		assertEquals(3, rentals[0].bid);
	}
	
	

		
	@Test 
	public void removeBook() throws RemoteException {
		int test = inventory.removeBook(null, 1, 1);
		assertEquals(1,test);
		
//		test = inventory.removeBook(null, 1, 1); // on permission fail
//		assertEquals(-1,test);
	}
				

	void addReservation() throws RemoteException {
		
		int test = inventory.addReservation(null, 1, 1, 1); 
		assertEquals(1,test);
		
//		int test = inventory.addReservation(null, 1, 1, 1); // permission fail
//		assertEquals(-1,test);
	}
	

	
	void addRental() throws RemoteException {
		int test = inventory.addRental(null, 1, 1, 1);
		assertEquals(1,test);
	}
	
	
}
