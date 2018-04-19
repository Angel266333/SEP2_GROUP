package com.via.clms.proxy;
import com.via.clms.shared.Book;
import com.via.clms.shared.BookRental;
import com.via.clms.shared.BookReservation;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IInventoryService extends Remote {

	Book[] getAllBooks(byte[] reqToken, int offset, int length) throws RemoteException;
	Book[] getBooks(byte[] reqToken, int lid, int offset, int length) throws RemoteException;

	Book[] getBooksByTitle(byte[] reqToken, int lid, String title) throws RemoteException;
	Book  getBookByISBN(byte[] reqToken, int lid, String isbn) throws RemoteException;
	Book[] getBooksByDate(byte[] reqToken, int lid, long timeLength) throws RemoteException;
	Book getBookByBID(byte[] reqToken, int bid) throws RemoteException;
	BookRental[] getRentalsByUID(byte[] reqToken, int lid, int uid) throws RemoteException;
	BookRental[] getRentalsByBID(byte[] reqToken, int lid, int bid) throws RemoteException;
	BookReservation[] getReservationsByUID(byte[] reqToken, int lid, int uid);
	BookReservation[] getReservationsByBID(byte[] reqToken, int lid, int bid);
	int addBook(byte[] reqToken, int lid, Book book);
	int removeBook(byte[] reqToken, int lid, int bid);
	int addReservation(byte[] reqToken, int lid, int bid, int uid);

}
