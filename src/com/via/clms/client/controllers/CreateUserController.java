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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateUserController implements Controller {

	private GridPane mainPane;
	private GridPane picturePane;
	private GridPane picturePanePlusUserDetails;
	private VBox innerPictureSectionUserDetails;
	private HBox innerPictureSectionButtonActionsSection;
	private Window window;
	
	private TextField tf1UserName;
	private TextField tf2UserPassword;
	private TextField tf3UserEmail;
	
	private Label lbl1UserName;
	private Label lbl2UserPassword;
	private Label lbl3UserEmail;
	private Label lbl4UserRoles;

	private ComboBox<String> cb1userRoles;
	
	private Button btn1CreateUser;
	private Button btn2Cancel;
	
	public CreateUserController() {

		tf1UserName = new TextField();
		tf2UserPassword = new TextField();
		tf3UserEmail = new TextField();

		lbl1UserName = new Label("User name:");
		lbl2UserPassword = new Label("User password:");
		lbl3UserEmail = new Label("User email:");
		lbl4UserRoles = new Label("User role:");

		mainPane = new GridPane();
		picturePane = new GridPane();
		picturePanePlusUserDetails = new GridPane();
		innerPictureSectionUserDetails = new VBox();
		innerPictureSectionButtonActionsSection = new HBox();

	}

	@Override
	public String getTitle() {
		return "Create a user";
	}

	@Override
	public Parent getComponent() {
		
		final File f = new File(ViewBookDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String removeInvalidTargetPath = "bin";
		String synchronizedPath = filePath.replace(removeInvalidTargetPath, "src");
		String outputPath = "file:" + synchronizedPath + File.separator + "com" + File.separator + "via"
				+ File.separator + "clms" + File.separator + "client" + File.separator + "graphics" + File.separator
				+ "users.png";
		Image imageDir = new Image(outputPath);
		ImageView viewImg = new ImageView();
		viewImg.setImage(imageDir);
		
		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\
		
		btn1CreateUser = new Button("Create user");
		btn2Cancel = new Button("Cancel");

		btn1CreateUser.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (tf1UserName.getText().isEmpty() 
						|| tf2UserPassword.getText().isEmpty()
						|| tf3UserEmail.getText().isEmpty()) {
						Alert alertFailiure = new Alert(AlertType.ERROR);
						alertFailiure.setTitle("Error Dialog");
						alertFailiure.setHeaderText("No information was filled in");
						alertFailiure.setContentText("Please fill in all fields!");
						alertFailiure.showAndWait();
					} else {
					// TODO - Execute function ---> Creating a new user
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
		
		innerPictureSectionUserDetails.setAlignment(Pos.CENTER_LEFT);
		innerPictureSectionUserDetails.setPadding(new Insets(5, 0, 0, 0));
		
		innerPictureSectionButtonActionsSection.setAlignment(Pos.CENTER);
		innerPictureSectionButtonActionsSection.setPadding(new Insets(15, 0, 0, 0));
		
		cb1userRoles = new ComboBox<String>();
		cb1userRoles.setPromptText("Select role");
		cb1userRoles.getItems().addAll("System Administrator", "Librarian", "Loaner");
		
		// \\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\
		
		innerPictureSectionUserDetails.getChildren().addAll(lbl1UserName, tf1UserName, lbl2UserPassword, tf2UserPassword, 
				lbl3UserEmail, tf3UserEmail, lbl4UserRoles, cb1userRoles);
		innerPictureSectionUserDetails.setSpacing(5);

		innerPictureSectionButtonActionsSection.getChildren().addAll(btn1CreateUser, btn2Cancel);
		innerPictureSectionButtonActionsSection.setSpacing(5);

		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\
		
		picturePanePlusUserDetails.add(picturePane, 0, 0);
		picturePanePlusUserDetails.add(innerPictureSectionUserDetails, 0, 1);
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