package com.via.clms;

/**
 * Small logging helper class
 */
public class Log {

	/**
	 * 
	 */
	private static void printStackTrace(Throwable e) {
		StackTraceElement[] elements = e != null ? 
				e.getStackTrace() : Thread.currentThread().getStackTrace();
		
		for (int i=1; i < elements.length; i++) {
			StackTraceElement s = elements[i];
			
			System.err.println("\tat " + s.getClassName() + "." + s.getMethodName()
			        + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
		}
	}
	
	/**
	 * 
	 */
	public static void info(String message) {
		System.out.println(message);
	}
	
	/**
	 * 
	 */
	public static void error(String message, Throwable e) {
		System.err.println(message);
		
		printStackTrace(e);
	}
	
	/**
	 * 
	 */
	public static void error(String message) {
		error(message, null);
	}
	
	/**
	 * 
	 */
	public static void error(Throwable e) {
		error(e.getMessage(), e);
	}
}
