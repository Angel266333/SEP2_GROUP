package com.via.clms.client.views;

import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This will display content in a dialog like view
 */
public class DialogWindow extends Window {
	
	/** * */
	protected Window mParent;

	/**
	 * @param controller
	 * 		The controller that should be attached to this window
	 * 
	 * @param parent
	 * 		The window that should be set as owner of this dialog
	 */
	public DialogWindow(Controller controller, Window parent) {
		super(controller);
		
		mParent = parent;
	}
	
	/**
	 * @param controller
	 * 		The controller that should be attached to this window
	 */
	public DialogWindow(Controller controller) {
		super(controller);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initializeWindow() {
		super.initializeWindow();

		mStage.initStyle(StageStyle.UTILITY);
		mStage.setResizable(false);
		
		if (mParent != null) {
			mStage.initOwner(mParent.getFrame());
			mStage.initModality(Modality.WINDOW_MODAL);
			mStage.setAlwaysOnTop(true);
			
		} else {
			mStage.initModality(Modality.APPLICATION_MODAL);
		}
	}
}
