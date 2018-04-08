package com.via.clms.shared;

import java.io.Serializable;

/**
 * Represents a single user 
 */
public class User implements Serializable {

	/** * */
	private static final long serialVersionUID = 1L;

	/** Database UserID */
	public final int uid;
	
	/** Collection of roles/permissions */
	public UserRoles[] roles;
	
	/** Users CPR Nr. */
	public long cpr;
	
	/** Users full name */
	public String name;
	
	/** Users Email */
	public String email;
	
	/**
	 * 
	 */
	public User() {
		this.uid = 0;
	}
	
	/**
	 * 
	 */
	public User(int uid) {
		this.uid = uid;
	}
}
