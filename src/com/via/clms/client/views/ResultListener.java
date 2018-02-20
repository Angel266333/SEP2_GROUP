package com.via.clms.client.views;

/**
 * This listener can be used to get a result from a {@link Window}
 * 
 * @see ResultController
 */
public interface ResultListener<T> {

	/**
	 * This is invoked by the {@link Controller} that it is currently 
	 * attached to once the window closes. It will parse the 
	 * result from that controller. 
	 * 
	 * @param result
	 * 		The result from the controller
	 */
	void onReturnResult(T result);
}
