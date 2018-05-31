package com.via.clms.client.controllers.containers;

/**
 * 
 */
public class UserSession {
	
	/** * */
	public final int lid;
	
	/** * */
	public final int cpr;
	
	/** * */
	public final byte[] token;
	
	/**
	 * 
	 */
	public UserSession(byte[] token, long cpr, int lid) {
		this.lid = lid;
		this.token = token;
		this.cpr = cpr;
	}
}
