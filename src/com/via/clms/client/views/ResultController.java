package com.via.clms.client.views;

/**
 * This is a controller that is able to deal with a {@link ResultListener}
 *
 * @param <T>
 * 		The result type that listeners should expect
 */
public abstract class ResultController<T> extends MultiController {
	
	/** * */
	private ResultListener<T> mListener;
	
	/** * */
	private T mResult;
	
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
	public void onWindowClose(Window win) {
		super.onWindowClose(win);
		
		if (mListener != null) {
			mListener.onReturnResult(getResult());
		}
	}
}
