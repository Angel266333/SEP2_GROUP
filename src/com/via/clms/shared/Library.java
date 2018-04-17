package com.via.clms.shared;

import java.io.Serializable;

public class Library implements Serializable {
	public int lid;
	public String name;

	public Library(int lid, String name) {
		this.lid = lid;
		this.name = name;
	}
}
