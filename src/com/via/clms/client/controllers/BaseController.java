package com.via.clms.client.controllers;

import java.io.File;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.StackController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		mBack = new Button("", new ImageView(getBackArrowImage()));
		mBack.setStyle("-fx-background-color: transparent;");
		mBack.setPadding(new Insets(10, 0, 0, 10));
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
		getWindow().resize();
		
	}
	
	public Image getBackArrowImage() {
		final File f = new File(
				ViewBookDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String removeInvalidTargetPath = "bin";
		String synchronizedPath = filePath.replace(removeInvalidTargetPath, "src");
		String outputPath = "file:" + synchronizedPath + File.separator + "com" + File.separator + "via"
				+ File.separator + "clms" + File.separator + "client" + File.separator + "graphics" + File.separator
				+ "arrow_left.png";
		Image imageDirHelpImg = new Image(outputPath);
		return imageDirHelpImg;
	}
}