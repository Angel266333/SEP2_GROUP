package com.via.clms.shared;

import java.io.Serializable;

public class BookReservation implements Serializable {
	public int bid;
	public int uid;

	public BookReservation(int bid, int uid) {
		this.bid = bid;
		this.uid = uid;
	}
}
