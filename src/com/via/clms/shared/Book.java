package com.via.clms.shared;

import java.io.Serializable;
import javafx.scene.image.Image;

public class Book implements Serializable {
	public int bid;
	public String title;
	public int inventory;
	public String ISBN;
	public String description;
	public String image;
	public long release;
	public String author;
	public String location;

	public Book(int bid, String title, int inventory, String ISBN, String description) {
		this.bid = bid;
		this.title = title;
		this.inventory = inventory;
		this.ISBN = ISBN;
		this.description = description;
	}
	
	public Image getImage() {
		return null;
	}
}
