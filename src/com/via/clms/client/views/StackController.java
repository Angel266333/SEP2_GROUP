package com.via.clms.client.views;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;

/**
 * 
 */
public abstract class StackController implements Controller, WindowListener {
	
	/**
	 * 
	 */
	private static class StackElement {
		
		/** * */
		public final Controller controller;
		
		/** * */
		public final Parent view;
		
		/**
		 * 
		 */
		public StackElement(Controller controller, Parent view) {
			this.controller = controller;
			this.view = view;
		}
	}
	
	/** * */
	private final List<StackElement> mElements = new ArrayList<StackElement>();
	
	/** * */
	private Controller mController;

	/** * */
	private Window mWindow;

	/**
	 * 
	 */
	public StackController(Controller mainController) {
		mController = mainController;
	}

	/**
	 * 
	 */
	protected final boolean isStacked() {
		return mElements.size() > 1;
	}

	/**
	 * 
	 */
	protected final Controller popStack() {
		if (mElements.size() > 1) {
			mController.onWindowPause(mWindow);
			mController.onWindowClose(mWindow);
			
			int prev = mElements.size() - 1;
			mElements.remove(prev--);
			
			StackElement stacked = mElements.get(prev);
			onStackChanged(stacked.view, stacked.controller);

			mController = stacked.controller;
			mController.onWindowResume(mWindow);
		}

		return null;
	}

	/**
	 * 
	 */
	protected final void pushStack(Controller controller) {
		if (mController != null && mElements.size() > 0) {
			mController.onWindowPause(mWindow);
		}

		Parent view = controller.getComponent();
		StackElement stacked = new StackElement(controller, view);

		mElements.add(stacked);
		onStackChanged(view, controller);

		mController = controller;
		mController.onWindowOpen(mWindow);
		mController.onWindowResume(mWindow);
		mController.onWindowRefresh(mWindow);
	}

	/**
	 * 
	 */
	protected final Controller getCurrentStack() {
		return mController;
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

		pushStack(mController);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowClose(Window win) {
		for (int i = mElements.size() - 1; i >= 0; i--) {
			mElements.get(i).controller.onWindowClose(mWindow);
		}

		mElements.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowRefresh(Window win) {
		mController.onWindowRefresh(mWindow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowResume(Window win) {
		mController.onWindowResume(mWindow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowPause(Window win) {
		mController.onWindowPause(mWindow);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onLaunchController(Controller controller) {
		// We should support nested Window Listeners
		if (!(mController instanceof WindowListener)
				|| !((WindowListener) mController).onLaunchController(controller)) {

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
		if (!(mController instanceof WindowListener) || !((WindowListener) mController).onRequestExit()) {

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
	public abstract void onStackChanged(Parent view, Controller controller);
}
