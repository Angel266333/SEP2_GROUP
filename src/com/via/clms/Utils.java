package com.via.clms;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	 * Generates a user token that can be used to identify/validate a user
	 * 
	 * The idea with tokens is to have something more safe to transfer between 
	 * server and clients, unlike raw passwords, that should be kept local.
	 * 
	 * @param String username
	 * 		The username of the user to generate the token for
	 * 
	 * @param String passwd
	 * 		The password used by the user
	 * 
	 * @return byte[]
	 * 		A hash value in raw bytes
	 */
	public static byte[] getUserToken(String username, String passwd) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update( (username + passwd).getBytes( StandardCharsets.UTF_8 ) );
			
			return md.digest();
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
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
