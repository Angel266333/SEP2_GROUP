package com.via.clms.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import com.via.clms.Log;
import com.via.clms.Utils;
import com.via.clms.server.services.DatabaseService;
import com.via.clms.server.services.Service;
import com.via.clms.server.services.UserService;

/**
 * 
 */
public class Main {
	
	/** * */
	private static Map<String, Class<? extends Service>> SERVICES = new LinkedHashMap<>();
	
	/** * */
	private static Set<String> LOADED = new HashSet<>();
	
	/**
	 * 
	 */
	static {
		SERVICES.put("database", DatabaseService.class);
		SERVICES.put("user", UserService.class);
	}

	/**
	 * 
	 */
	public static void main(String[] args) {
		print("Wellcome to CLMS Server", "Type 'help' to get a list of options");
		
		daemon:
		for (;;) {
			String[] parts = Utils.readInput("CLMS Shell $ ").split("\\s+");
			
			switch (parts[0]) {
				case "help": 
					print(
						"Commands:",
						"\ttoken <cpr> <pass>:\tCreate user token",
						"\tstatus:\tGet service registry status",
						"\tstart:\tStart the service registry",
						"\tstop:\tStop the service registry",
						"\texit:\tExit the server"
					);
					
					break;
					
				case "status":
					String[] msgs = new String[SERVICES.size()+1];
					int pos = 1;
					
					msgs[0] = "Services";
					
					for (Entry<String, Class<? extends Service>> entry : SERVICES.entrySet()) {
						boolean loaded = LOADED.contains(entry.getKey());
						msgs[pos++] = "\t" + entry.getKey() + ": " + (loaded ? "Loaded" : "Pending");
					}
					
					print(msgs);
					
					break;
					
				case "start": 
					if (SERVICES.size() != LOADED.size()) {
						boolean status = true;
						
						for (Entry<String, Class<? extends Service>> entry : SERVICES.entrySet()) {
							if (!LOADED.contains(entry.getKey())) {
								try {
									if (!ServiceManager.registerService(entry.getKey(), entry.getValue().newInstance())) {
										status = false;
										
									} else {
										LOADED.add(entry.getKey());
									}
									
								} catch (Exception e) {
									status = false;
									Log.error(e);
								}
							}
						}
						
						if (status) {
							print("Service registry has been fully loaded");
							
						} else {
							print("Warning: Some services failed to load!");
						}
						
					} else {
						print("All services is currently loaded");
					}
					
					break;
					
				case "stop": 
					if (LOADED.size() > 0) {
						// Stopping services should be in reversed order
						ListIterator<String> iterator = new ArrayList<String>(SERVICES.keySet()).listIterator(SERVICES.size());
						
						while (iterator.hasPrevious()) {
							String name = iterator.previous();
							
							if (LOADED.contains(name)) {
								ServiceManager.unregisterService(name);
							}
						}
						
						LOADED.clear();
						
						print("Service registry has been stopped");
						
					} else {
						print("Service registry is already stopped");
					}
					
					break;
					
				case "token":
					if (parts.length < 3) {
						print("Tokens must have a CPR number and a password"); break;
					}
					
					long cpr = Long.parseLong(parts[1]);
					String passwd = parts[2];
					byte[] tok = UserService.generateUserToken(cpr, passwd);
					
					print("Token: " + Utils.tokenToString(tok));
					
					break;
					
				case "exit": 
					break daemon;
					
				default:
					print("Unknown command '" + parts[0] + "'!");
			}
		}
		
		if (LOADED.size() > 0) {
			// Stopping services should be in reversed order
			ListIterator<String> iterator = new ArrayList<String>(SERVICES.keySet()).listIterator(SERVICES.size());
			
			while (iterator.hasPrevious()) {
				String name = iterator.previous();
				
				if (LOADED.contains(name)) {
					ServiceManager.unregisterService(name);
				}
			}
			
			LOADED.clear();
			
			print("Service registry has been stopped", "Server has been shutdown");
			
		} else {
			System.out.println("Server has been shutdown");
		}
		
		System.exit(0);
	}
	
	/**
	 * 
	 */
	protected static void print(String... msgs) {
		for (String msg : msgs) {
			System.out.println(msg);
		}
		
		System.out.println("");
	}
}
