package com.via.clms.client.controllers.containers;

/**
 * 
 */
public class UserSession {
	
	/** * */
	public final int lid;
	
	/** * */
	public final byte[] token;
	
	/**
	 * 
	 */
	public UserSession(byte[] token, int lid) {
		this.lid = lid;
		this.token = token;
	}
}
