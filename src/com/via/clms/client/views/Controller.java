package com.via.clms.client.views;

import javafx.scene.Parent;

/**
 * GUI controller that is atteched to a {@link Window}
 * and provides/handles the GUI elements to display.
 */
public interface Controller {

	/**
	 * This method should return the window title
	 */
	String getTitle();
	
	/**
	 * This method should return the GUI elements 
	 * that is to be displayed in the window
	 */
	Parent getComponent();
	
	/**
	 * Invoked by the {@link Window} when it has been opened
	 */
	void onWindowOpen(Window win);
	
	/**
	 * Invoked by the {@link Window} when it is being closed
	 */
	void onWindowClose(Window win);
	
	/**
	 * Invoked by the {@link Window} when the controller 
	 * should update the UI
	 */
	void onWindowRefresh(Window win);
}
