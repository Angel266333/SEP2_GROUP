package com.via.clms.client.views;

import java.util.HashSet;
import java.util.Set;


/**
 * This is a root controller that can be used to deal with multiple nested controllers
 * 
 */
public abstract class MultiController implements Controller {
	
	/** * */
	private Window mWindow;
	
	/** * */
	private Set<Controller> mControllers = new HashSet<>();
	
	/**
	 * Closes the window that this controller is attached to
	 */
	public final void exit() {
		mWindow.close();
	}
	
	/**
	 * Get the window that this controller is attached to
	 */
	public final Window getWindow() {
		return mWindow;
	}
	
	/**
	 * Register a nested controller so that it will 
	 * receive events from the window
	 */
	public void registerController(Controller controller) {
		mControllers.add(controller);
	}
	
	/**
	 * Unregister a nested controller
	 */
	public void unregisterController(Controller controller) {
		mControllers.remove(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowOpen(Window win) {
		mWindow = win;
		
		for (Controller controller : mControllers) {
			controller.onWindowOpen(win);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowClose(Window win) {
		for (Controller controller : mControllers) {
			controller.onWindowClose(win);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowRefresh(Window win) {
		for (Controller controller : mControllers) {
			controller.onWindowRefresh(win);
		}
	}
}
