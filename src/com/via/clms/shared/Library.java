package com.via.clms.shared;

import java.io.Serializable;

public class Library implements Serializable {
	public int lid;
	public String name;
	public String location;

	public Library(int lid, String name, String location) {
		this.lid = lid;
		this.name = name;
		this.location = location;
	}
}
