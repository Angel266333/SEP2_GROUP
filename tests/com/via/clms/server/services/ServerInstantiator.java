package com.via.clms.server.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;

import com.via.clms.server.ServiceManager;

public class ServerInstantiator {

	/** * */
	private static Map<String, Class<? extends Service>> SERVICES = new LinkedHashMap<>();
	
	/**
	 * 
	 */
	static {
		SERVICES.put("database", DatabaseService.class);
		SERVICES.put("library", LibraryService.class);
		SERVICES.put("user", UserService.class);
	}

	/**
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SQLException 
	 */
	public static void initialize() throws InstantiationException, IllegalAccessException, SQLException {
		for (Entry<String, Class<? extends Service>> entry : SERVICES.entrySet()) {
			if (!ServiceManager.registerService(entry.getKey(), entry.getValue().newInstance())) {
				Assert.fail("Could not load the service '" + entry.getKey() + "'");
			}
		}
	}
		
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public static void deinitialize() throws SQLException {
		
		// Stopping services should be in reversed order
		ListIterator<String> iterator = new ArrayList<String>(SERVICES.keySet()).listIterator(SERVICES.size());
		
		while (iterator.hasPrevious()) {
			ServiceManager.unregisterService(iterator.previous());
		}
	}
}
