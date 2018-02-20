package com.via.clms.server.services;

import com.via.clms.server.ServiceManager;

/**
 *
 */
public interface Service {
	
	/**
	 * Event method that is invoked by {@link ServiceManager}
	 */
	boolean onConfigure();

	/**
	 * Event method that is invoked by {@link ServiceManager}
	 */
	void onShutdown();
}
