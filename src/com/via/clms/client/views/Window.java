package com.via.clms.client.views;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This will display a regular window
 */
public class Window {
	
	/** * */
	protected Stage mStage;
	
	/** * */
	protected Controller mController;
	
	/**
	 * @param controller
	 * 		The controller that should be attached to this window
	 */
	public Window(Controller controller) {
		mController = controller;
	}
	
	/**
	 * Internal method used to build the window.
	 * This is invoked by {@link #open()}
	 */
	protected void initializeWindow() {
		mStage = new Stage();
		mStage.setResizable(false);
		mStage.setScene(
				new Scene(
						mController.getComponent()		
				)
		);
		
		mStage.setTitle(mController.getTitle());
		
		mStage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
            	mController.onWindowOpen(Window.this);
            	mController.onWindowResume(Window.this);
            	mController.onWindowRefresh(Window.this);
            }
        });
		
		mStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
            	mController.onWindowPause(Window.this);
            	mController.onWindowClose(Window.this);
            }
        });
	}
	
	/**
	 * Change the title on this window
	 * 
	 * @param title
	 * 		The title to set
	 */
	public void setTitle(String title) {
		ApplicationThread.run(new Runnable(){
			@Override
			public void run() {
				if (mStage != null) {
					mStage.setTitle(title);
				}
			}
		});
	}

	/**
	 * Resize the window to match the content
	 */
	public void resize() {
		ApplicationThread.run(new Runnable(){
			@Override
			public void run() {
				if (mStage != null) {
					mStage.sizeToScene();
				}
			}
		});
	}

	/**
	 * Resize the window to a fixed size
	 * 
	 * @param width
	 * 		The new width of the window
	 * 
	 * @param height
	 * 		The new height of the window
	 */
	public void resize(int width, int height) {
		ApplicationThread.run(new Runnable(){
			@Override
			public void run() {
				if (mStage != null) {
					mStage.setHeight(height);
					mStage.setWidth(width);
				}
			}
		});
	}

	/**
	 * Returns the frame used by this window
	 */
	public Stage getFrame() {
		return mStage;
	}
	
	/**
	 * 
	 */
	public void launchController(final Controller controller) {
		ApplicationThread.run(new Runnable(){
			@Override
			public void run() {
				if (mStage != null) {
					if (!(mController instanceof WindowListener)
							|| !((WindowListener) mController).onLaunchController(controller)) {
						
						mStage.hide();
						mController = controller;
						mStage.show();
					}
				}
			}
		});
	}

	/**
	 * Open the window
	 * 
	 * @param width
	 * 		The width of the window
	 * 
	 * @param height
	 * 		The height of the window
	 */
	public void open(final int width, final int height) {
		ApplicationThread.run(new Runnable(){
			@Override
			public void run() {
				if (mStage == null) {
					initializeWindow();
					resize(width, height);
					
					mStage.show();
				}
			}
		});
	}

	/**
	 * Open the window
	 */
	public void open() {
		ApplicationThread.run(new Runnable(){
			@Override
			public void run() {
				if (mStage == null) {
					initializeWindow();
					resize();
					
					mStage.show();
				}
			}
		});
	}

	/**
	 * Close the window
	 */
	public void close() {
		ApplicationThread.run(new Runnable(){
			@Override
			public void run() {
				if (mStage != null) {
					if (!(mController instanceof WindowListener)
							|| !((WindowListener) mController).onRequestExit()) {
					
						mStage.close();
						mStage = null;
					}
				}
			}
		});
	}
	
	public void minimize() {
		mStage.setIconified(true);
	}
	
	/**
	 * Execute a {@link Runnable} on the UI Thread
	 * 
	 * This is a shortcut to {@link ApplicationThread#run(Runnable)}.
	 * Besides simply making sure that something is executed on the UI thread, 
	 * it also acts as an execution queue. 
	 * 
	 * @param runnable
	 * 		The {@link Runnable} to execute
	 */
	public void run(Runnable runnable) {
		ApplicationThread.run(runnable);
	}
}
