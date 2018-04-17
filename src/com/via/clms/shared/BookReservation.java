package com.via.clms.shared;

import java.io.Serializable;

public class BookReservation implements Serializable {
	public int bid;
	public int uid;
	public int lid;

	public BookReservation(int bid, int uid, int lid) {
		this.bid = bid;
		this.uid = uid;
		this.lid = lid;
	}
}
