package com.via.clms.client.views;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;

/**
 * 
 */
public abstract class StackController implements Controller, WindowListener {
	
	/** * */
	private final List<Controller> mStack = new ArrayList<Controller>();
	
	/** * */
	private final List<Parent> mViews = new ArrayList<Parent>();
	
	/** * */
	private Controller mCurrentController;
	
	/** * */
	private Window mWindow;

	/**
	 * 
	 */
	public StackController(Controller mainController) {
		mCurrentController = mainController;
	}
	
	/**
	 * 
	 */
	protected final boolean isStacked() {
		return mStack.size() > 1;
	}
	
	/**
	 * 
	 */
	protected final Controller popStack() {
		if (mStack.size() > 1) {
			mCurrentController.onWindowPause(mWindow);
			mCurrentController.onWindowClose(mWindow);
			Controller prevController = mStack.remove(mStack.size()-1);
			Parent prevView = mViews.remove(mViews.size()-1);
			
			onStackChanged(prevView, prevController);
			
			mCurrentController = prevController;
			mCurrentController.onWindowResume(mWindow);
		}
		
		return null;
	}
	
	/**
	 * 
	 */
	protected final void pushStack(Controller controller) {
		if (mCurrentController != null) {
			mCurrentController.onWindowPause(mWindow);
		}
		
		Parent view = controller.getComponent();
		
		mStack.add(controller);
		mViews.add(view);
		
		onStackChanged(view, controller);
		
		mCurrentController = controller;
		mCurrentController.onWindowOpen(mWindow);
		mCurrentController.onWindowResume(mWindow);
		mCurrentController.onWindowRefresh(mWindow);
	}
	
	/**
	 * 
	 */
	protected final Controller getCurrentStack() {
		return mCurrentController;
	}
	
	/**
	 * 
	 */
	protected final Window getWindow() {
		return mWindow;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowOpen(Window win) {
		mWindow = win;
		
		pushStack(mCurrentController);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowClose(Window win) {
		for (int i=mStack.size()-1; i >= 0; i--) {
			mStack.get(i).onWindowClose(mWindow);
		}
		
		mStack.clear();
		mViews.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowRefresh(Window win) {
		mCurrentController.onWindowRefresh(mWindow);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowResume(Window win) {
		mCurrentController.onWindowResume(mWindow);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowPause(Window win) {
		mCurrentController.onWindowPause(mWindow);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onLaunchController(Controller controller) {
		// We should support nested Window Listeners
		if (!(mCurrentController instanceof WindowListener)
				|| !((WindowListener) mCurrentController).onLaunchController(controller)) {
		
			pushStack(controller);
		}
		
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onRequestExit() {
		// We should support nested Window Listeners
		if (!(mCurrentController instanceof WindowListener)
				|| !((WindowListener) mCurrentController).onRequestExit()) {
		
			if (isStacked()) {
				popStack();
				
				return true;
			}
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 */
	abstract void onStackChanged(Parent view, Controller controller);
}
