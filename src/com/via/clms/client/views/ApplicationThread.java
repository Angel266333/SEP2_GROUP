package com.via.clms.client.views;

import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
		if (!Platform.isFxApplicationThread()) {
			if (!mRunning) {
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
				
				/*
				 * JavaFX on some platforms does not finalize building/preparing
				 * window controls and decorations until a normal stage is being rendered. 
				 * This means that the first stage cannot be of the type UTILITY, UNDECORATED etc. 
				 * This hack will ensure that this is done right after the application thread has been started, 
				 * ensuring that any stage type can be used.
				 * 
				 * This is an error in JavaFX, one of many, but may not be fixed in the near future. 
				 * The people behind JavaFX is more interested in adding features rather than making 
				 * the current ones work as expected. 
				 */
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Stage stage = new Stage(StageStyle.TRANSPARENT);
						stage.setHeight(1);
						stage.setWidth(1);
						stage.show();
						Platform.runLater(new Runnable() {
					        @Override
					        public void run() {
					        	stage.close();
					        }
					    });
					}
				});
			}
			
			Platform.runLater(runnable);
			
		} else {
			runnable.run();
		}
	}
}
