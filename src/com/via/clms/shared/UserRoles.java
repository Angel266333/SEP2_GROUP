package com.via.clms.shared;

import java.io.Serializable;

/**
 * Represents set of roles/permission for a single library
 */
public class UserRoles implements Serializable {

	/** * */
	private static final long serialVersionUID = 1L;

	/** Database UserID */
	public final int uid;
	
	/** Database LibraryID */
	public final int lid;
	
	/** Permission bitwise flags */
	public int role = 0;
	
	/**
	 * 
	 */
	public UserRoles() {
		this.uid = 0;
		this.lid = 0;
	}
	
	/**
	 * 
	 */
	public UserRoles(int uid, int lid) {
		this.uid = uid;
		this.lid = lid;
	}
}
