package com.via.clms.client.views;

/**
 * Interface that provides controllers with additional 
 * information about window state changes
 */
public interface WindowListener {

	/**
	 * Intercept a controller that is do be launched. 
	 * 
	 * @return 
	 * 		This should return `true` if it's handled by the controller or `false` otherwise
	 */
	boolean onLaunchController(Controller controller);
	
	/**
	 * Intercept a request to close the window
	 * 
	 * @return 
	 * 		This should return `true` if it's handled by the controller or `false` otherwise
	 */
	boolean onRequestExit();
}
