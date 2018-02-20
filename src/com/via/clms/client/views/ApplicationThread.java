package com.via.clms.client.views;

import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * This is used to start a GUI Thread that can be used 
 * by JavaFX. 
 * 
 * There should not be created any instances of this class. 
 * Use the static {@link ApplicationThread#run(Runnable)} method 
 * to execute a {@link Runnable} on the thread. If the thread 
 * does not yet exist, it will be created. 
 * 
 * This class is mainly meant to be used internally by the {@link Window} classes. 
 */
public final class ApplicationThread extends Application {
	
	/** * */
	private static volatile boolean mRunning = false;
	
	/** * */
	private static final Object mLock = new Object();

	/**
	 * @ignore
	 */
	@Override
	public void start(Stage stage) throws Exception {
		mRunning = true;
		
		synchronized(mLock) {
			mLock.notifyAll();
		}
	}
	
	/**
	 * @ignore
	 */
	@Override
	public void stop() throws Exception {
		System.exit(0);
	}
	
	/**
	 * Execute a {@link Runnable} on the GUI thread
	 * 
	 * @param runnable
	 * 		The {@link Runnable} to execute on the GUI thread
	 */
	public static synchronized void run(Runnable runnable) {
		if (!Platform.isFxApplicationThread() && !mRunning) {
			Executors.newSingleThreadExecutor().execute(new Runnable(){
				@Override
				public void run() {
					ApplicationThread.launch(ApplicationThread.class, new String[]{});
				}
			});
			
			while (!mRunning) {
				try {
					synchronized(mLock) {
						mLock.wait();
					}
					
				} catch (Exception e) {}
			}
		}
		
		Platform.runLater(runnable);
	}
}
