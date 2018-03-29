package com.via.clms.server.services;

import com.via.clms.shared.Book;
import com.via.clms.shared.BookRental;
import com.via.clms.shared.BookReservation;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryService implements Service {
	Book[] someBooks;
	BookRental[] someRentals;
	BookReservation[] someReservations;
	public InventoryService() {
		someBooks = new Book[20];
		someBooks[0] = new Book(1001, "aaa", 2, "2001", "aaa");
		someBooks[1] = new Book(1002, "bbb", 2, "2002", "bbb");
		someBooks[2] = new Book(1003, "ccc", 1, "2003", "ccc");
		someBooks[3] = new Book(1004, "ddd", 2, "2004", "ddd");
		someBooks[4] = new Book(1005, "eee", 4, "2005", "eee");
		someBooks[5] = new Book(1006, "fff", 2, "2006", "fff");
		someBooks[6] = new Book(1007, "ggg", 2, "2007", "ggg");
		someBooks[7] = new Book(1008, "hhh", 1, "2008", "hhh");
		someBooks[8] = new Book(1009, "iii", 5, "2009", "iii");
		someBooks[9] = new Book(1010, "jjj", 3, "2010", "jjj");
		someBooks[10] = new Book(1011, "kkk", 2, "2011", "kkk");
		someBooks[11] = new Book(1012, "lll", 4, "2012", "lll");
		someBooks[12] = new Book(1013, "mmm", 5, "2013", "mmm");
		someBooks[13] = new Book(1014, "nnn", 1, "2014", "nnn");
		someBooks[14] = new Book(1015, "ooo", 2, "2015", "ooo");
		someBooks[15] = new Book(1016, "ppp", 3, "2016", "ppp");
		someBooks[16] = new Book(1017, "qqq", 3, "2017", "qqq");
		someBooks[17] = new Book(1018, "rrr", 1, "2018", "rrr");
		someBooks[18] = new Book(1019, "sss", 3, "2019", "sss");
		someBooks[19] = new Book(1020, "ttt", 2, "2020", "ttt");

		someRentals = new BookRental[5];
		someRentals[0] = new BookRental(1002, 1, 100, 200);
		someRentals[1] = new BookRental(1003, 2, 100, 200);
		someRentals[2] = new BookRental(1004, 3, 100, 200);
		someRentals[3] = new BookRental(1005, 4, 100, 200);
		someRentals[4] = new BookRental(1006, 5, 100, 200);

		someReservations = new BookReservation[2];
		someReservations[0] = new BookReservation(1002, 6);
		someReservations[1] = new BookReservation(1003, 6);
	}

	@Override
	public boolean onConfigure() {
		return true;
	}

	@Override
	public void onShutdown() {

	}

	public Book[] getBooks(byte[] reqToken, int offset, int length) {
		return Arrays.copyOfRange(someBooks, offset, offset + length);
	}

	public Book[] getBooksByTitle(byte[] reqToken, String title) {
		ArrayList<Book> result = new ArrayList<>();
		for(Book b : someBooks) {
			if(b.title.equals(title)) {
				result.add(b);
			}
		}
		Book[] books = new Book[result.size()];
		result.toArray(books);
		return books;
	}

	public Book[] getBooksByISBN(byte[] reqToken, String ISBN) {
		ArrayList<Book> result = new ArrayList<>();
		for(Book b : someBooks) {
			if(b.ISBN.equals(ISBN)) {
				result.add(b);
			}
		}
		Book[] books = new Book[result.size()];
		result.toArray(books);
		return books;
	}

	public Book[] getBooksByDate(byte[] reqToken, long timeoffset, long timelength) {
		//Returns one book per year in the time span
		int years = new Long((timelength - (timelength % 31557600L)) / 31557600L).intValue();
		years = Math.min(20, years);

		return Arrays.copyOfRange(someBooks, 0, years);
	}

	public Book getBookByBID(byte[] reqToken, int bid) {
		for(Book b : someBooks) {
			if(b.bid == bid) {
				return b;
			}
		}
		return null;
	}

	public BookRental[] getRentalsByUID(byte[] reqToken, int uid) {
		ArrayList<BookRental> result = new ArrayList<>();
		for(BookRental br : someRentals) {
			if(br.uid == uid) {
				result.add(br);
			}
		}
		BookRental[] rentals = new BookRental[result.size()];
		result.toArray(rentals);
		return rentals;
	}

	public BookRental[] getRentalsByBID(byte[] reqToken, int bid) {
		ArrayList<BookRental> result = new ArrayList<>();
		for(BookRental br : someRentals) {
			if(br.bid == bid) {
				result.add(br);
			}
		}
		BookRental[] rentals = new BookRental[result.size()];
		result.toArray(rentals);
		return rentals;
	}

	public BookReservation[] getReservationsByUID(byte[] reqToken, int uid) {
		ArrayList<BookReservation> result = new ArrayList<>();
		for(BookReservation br : someReservations) {
			if(br.uid == uid) {
				result.add(br);
			}
		}
		BookReservation[] reservations = new BookReservation[result.size()];
		result.toArray(reservations);
		return reservations;
	}

	public BookReservation[] getReservationsByBID(byte[] reqToken, int bid) {
		ArrayList<BookReservation> result = new ArrayList<>();
		for(BookReservation br : someReservations) {
			if(br.bid == bid) {
				result.add(br);
			}
		}
		BookReservation[] reservations = new BookReservation[result.size()];
		result.toArray(reservations);
		return reservations;
	}

	public int addBook(byte[] reqToken, Book book) {
		return 0;
	}

	public int removeBook(byte[] reqToken, int bid) {
		return 0;
	}

	public int addReservation(byte[] reqToken, int bid, int uid) {
		return 0;
	}

	public int cancelReservation(byte[] reqToken, int bid, int uid) {
		return 0;
	}

	public int addRental(byte[] reqToken, int bid, int uid, long offset, long duration) {
		return 0;
	}

	public int returnRental(byte[] reqToken, int bid, int uid) {
		return 0;
	}
}
