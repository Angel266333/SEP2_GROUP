package com.via.clms.client.controllers;

import java.io.File;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateLibraryController implements Controller {
	
	private GridPane mainPane;
	private GridPane picturePane;
	private GridPane picturePanePlusUserDetails;
	private VBox innerPictureSectionLibraryDetails;
	private HBox innerPictureSectionButtonActionsSection;
	private Window window;
	
	private TextField tf1LibraryName;
	private TextField tf2LibraryLocation;
	
	private Label lbl1LibraryName;
	private Label lbl2LibraryLocation;
	
	private Button btn1CreateLibrary;
	private Button btn2Cancel;
	
	public CreateLibraryController() {

		tf1LibraryName = new TextField();
		tf2LibraryLocation = new TextField();

		lbl1LibraryName = new Label("Library name:");
		lbl2LibraryLocation = new Label("Library location:");

		mainPane = new GridPane();
		picturePane = new GridPane();
		picturePanePlusUserDetails = new GridPane();
		innerPictureSectionLibraryDetails = new VBox();
		innerPictureSectionButtonActionsSection = new HBox();

	}

	@Override
	public String getTitle() {
		return "Create a library";
	}

	@Override
	public Parent getComponent() {
		
		final File f = new File(ViewBookDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String removeInvalidTargetPath = "bin";
		String synchronizedPath = filePath.replace(removeInvalidTargetPath, "src");
		String outputPath = "file:" + synchronizedPath + File.separator + "com" + File.separator + "via"
				+ File.separator + "clms" + File.separator + "client" + File.separator + "graphics" + File.separator
				+ "libraries.png";
		Image imageDir = new Image(outputPath);
		ImageView viewImg = new ImageView();
		viewImg.setImage(imageDir);
		
		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\
		
		btn1CreateLibrary = new Button("Create library");
		btn2Cancel = new Button("Cancel");

		btn1CreateLibrary.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (tf1LibraryName.getText().isEmpty() 
					|| tf2LibraryLocation.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No information was filled in");
					alertFailiure.setContentText("Please fill in all fields!");
					alertFailiure.showAndWait();
				} else {
				// TODO - Execute function ---> Adding a library
				}
				
			}
		});
		
		btn2Cancel.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				window.close();
			}
		});
		
		// \\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\

		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));

		picturePane.setAlignment(Pos.CENTER);
		picturePane.add(viewImg, 0, 0);
		
		innerPictureSectionLibraryDetails.setAlignment(Pos.CENTER_LEFT);
		innerPictureSectionLibraryDetails.setPadding(new Insets(5, 0, 0, 0));
		
		innerPictureSectionButtonActionsSection.setAlignment(Pos.CENTER);
		innerPictureSectionButtonActionsSection.setPadding(new Insets(15, 0, 0, 0));
		
		// \\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\
		
		innerPictureSectionLibraryDetails.getChildren().addAll(lbl1LibraryName, tf1LibraryName, lbl2LibraryLocation, tf2LibraryLocation);
		innerPictureSectionLibraryDetails.setSpacing(5);

		innerPictureSectionButtonActionsSection.getChildren().addAll(btn1CreateLibrary, btn2Cancel);
		innerPictureSectionButtonActionsSection.setSpacing(5);

		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\
		
		picturePanePlusUserDetails.add(picturePane, 0, 0);
		picturePanePlusUserDetails.add(innerPictureSectionLibraryDetails, 0, 1);
		picturePanePlusUserDetails.add(innerPictureSectionButtonActionsSection, 0, 2);
		
		mainPane.add(picturePanePlusUserDetails, 0, 0);

		return mainPane;
	}

	@Override
	public void onWindowOpen(Window win) {
		this.window = win;
	}

	@Override
	public void onWindowClose(Window win) {
		this.window = win;
	}

	@Override
	public void onWindowRefresh(Window win) {
		
	}

	@Override
	public void onWindowResume(Window win) {

	}

	@Override
	public void onWindowPause(Window win) {
		
	}

}