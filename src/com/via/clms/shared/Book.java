package com.via.clms.shared;

import java.io.Serializable;

public class Book implements Serializable {
	public int bid;
	public String title;
	public int inventory;
	public String ISBN;
	public String description;

	public Book(int bid, String title, int inventory, String ISBN, String description) {
		this.bid = bid;
		this.title = title;
		this.inventory = inventory;
		this.ISBN = ISBN;
		this.description = description;
	}
}
