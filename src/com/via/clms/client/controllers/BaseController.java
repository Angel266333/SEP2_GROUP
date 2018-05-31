package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.StackController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class BaseController extends StackController {
	private VBox vBox;
	private Button mBack;

	public BaseController(Controller mainController) {
		super(mainController);
	}

	@Override
	public String getTitle() {
		return "";
	}

	@Override
	public Parent getComponent() {
		mBack = new Button("Back");
		mBack.setVisible(false);
		mBack.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				popStack();
			}

		});
		
		vBox = new VBox();
		vBox.getChildren().add(mBack);
		
		return vBox;
	}

	@Override
	public void onStackChanged(Parent view, Controller controller) {
		getWindow().setTitle(controller.getTitle());
		if (vBox.getChildren().size() > 1) {
			vBox.getChildren().remove(1);
		}
		vBox.getChildren().add(view);
		
		if (isStacked()) {
			mBack.setVisible(true);
		}
	}
}