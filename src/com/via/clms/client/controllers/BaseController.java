package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.StackController;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class BaseController extends StackController {
	private VBox vBox;

	public BaseController(Controller mainController) {
		super(mainController);
	}

	@Override
	public String getTitle() {
		return "";
	}

	@Override
	public Parent getComponent() {
		vBox = new VBox();
		return vBox;
	}

	@Override
	public void onStackChanged(Parent view, Controller controller) {
		getWindow().setTitle(controller.getTitle());
		if (vBox.getChildren().size() > 0) {
			vBox.getChildren().remove(0);
		}
		vBox.getChildren().add(view);
	}
}