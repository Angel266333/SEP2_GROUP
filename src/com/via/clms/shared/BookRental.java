package com.via.clms.shared;

import java.io.Serializable;

public class BookRental implements Serializable {
	public int bid;
	public int uid;
	public long timeoffset;
	public long timelength;

	public BookRental(int bid, int uid, long timeoffset, long timelength) {
		this.bid = bid;
		this.uid = uid;
		this.timeoffset = timeoffset;
		this.timelength = timelength;
	}
}
