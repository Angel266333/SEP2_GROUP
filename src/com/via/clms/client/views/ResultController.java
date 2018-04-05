package com.via.clms.client.views;

/**
 * This is a controller that is able to deal with a {@link ResultListener}
 *
 * @param <T>
 * 		The result type that listeners should expect
 */
public abstract class ResultController<T> implements Controller {
	
	/** * */
	private ResultListener<T> mListener;
	
	/** * */
	private T mResult;
	
	/** * */
	private Window mWindow;
	
	/**
	 * 
	 */
	protected final Window getWindow() {
		return mWindow;
	}
	
	/**
	 * Attach a result listener to this controller
	 * 
	 * Once the window closes, 
	 * the listener will receive the result from this controller
	 * 
	 * @param listener
	 * 		The listener to attach
	 */
	public final void setResultListener(ResultListener<T> listener) {
		mListener = listener;
	}
	
	/**
	 * Set the result that will be sent to the listener when the window is closed
	 * 
	 * @param result
	 * 		The result to send to the listener
	 */
	public final void setResult(T result) {
		mResult = result;
	}
	
	/**
	 * Get the result that currently set on this controller
	 */
	public final T getResult() {
		return mResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onWindowClose(Window win) {
		if (mListener != null) {
			mListener.onReturnResult(getResult());
			mListener = null;
		}
	}
	
	/**
	 * Invoked by the {@link Window} when it has been opened
	 */
	@Override
	public void onWindowOpen(Window win) {
		mWindow = win;
	}
	
	/**
	 * Invoked by the {@link Window} when the controller 
	 * should update the UI
	 */
	@Override
	public void onWindowRefresh(Window win) {
		
	}
	
	/**
	 * Invoked by the {@link Window} when the controller 
	 * should pause
	 */
	@Override
	public void onWindowResume(Window win) {
		
	}
	
	/**
	 * Invoked by the {@link Window} when the controller 
	 * should resume
	 */
	@Override
	public void onWindowPause(Window win) {
		
	}
}
