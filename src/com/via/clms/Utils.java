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
		String input = Utils.input.next();
		
		return input;
	}
	
	/**
	 * Convert a raw hash value token into a String representation
	 * 
	 * @param byte[] token
	 * 		The raw token 
	 */
	public static String tokenToString(byte[] token) {
		return DatatypeConverter.printHexBinary(token);
	}
	
	/**
	 * Convert a hash String representation into raw bytes
	 * 
	 * @param String token
	 * 		The token String representation
	 */
	public static byte[] tokenToBytes(String token) {
		return DatatypeConverter.parseHexBinary(token);
	}
}
