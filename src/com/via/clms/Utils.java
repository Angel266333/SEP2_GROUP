package com.via.clms;

import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

/**
 * Utility class that can be used by both server and clients
 */
public class Utils {
	
	/** * */
	public static final Scanner input = new Scanner(System.in);
	
	/**
	 * 
	 */
	public static String readInput(String msg) {
		System.out.print(msg);
		String input = Utils.input.nextLine();
		
		return input;
	}
	
	/**
	 * Convert a raw hash value token into a String representation
	 * 
	 * @param byte[] token
	 * 		The raw token 
	 */
	public static String tokenToString(byte[] token) {
		String hash = "";
		
		for(byte b : token) {
		    String hex = Integer.toHexString( b & 0xFF );
		    
		    if (hex.length() == 1) {
		    	hash += "0";
		    }
		    
		    hash += hex;
		}
		
		return hash;
	}
	
	/**
	 * Convert a hash String representation into raw bytes
	 * 
	 * @param String token
	 * 		The token String representation
	 */
	public static byte[] tokenToBytes(String token) {
		int len = token.length();
	    byte[] data = new byte[len / 2];
	    
	    for (int i=0, y=0, x=1; x < len; i++, y += 2, x += 2) {
	    	data[i] = (byte) (((Character.digit(token.charAt(y), 16) << 4) | Character.digit(token.charAt(x), 16)) & 0xFF);
	    }
	    
	    return data;
	}
}
