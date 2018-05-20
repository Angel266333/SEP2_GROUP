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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditBookDetailsController implements Controller {
	
	Window windowInstance;

	private GridPane mainPane;
	private GridPane picturePane;
	private GridPane picturePanePlusBookDetails;
	private VBox descriptionSection;
	private HBox operationsSection;
	private GridPane descriptionPanePlusOperations;

	private TextField tf1BookName;
	private TextField tf2BookAuthor;
	private TextField tf3BookYear;
	private TextField tf4BookISBN;
	
	private TextArea tAR5BookDescription;

	private Label lbl1BookName;
	private Label lbl2BookAuthor;
	private Label lbl3BookYear;
	private Label lbl4BookISBN;
	private Label lbl5BookDescription;

	public String bookName;
	public String bookAuthor;
	public String bookYear;
	public String bookAvailability;
	
	private Button btn1Update;
	private Button btn2Cancel;

	public EditBookDetailsController() {

		tf1BookName = new TextField();
		tf2BookAuthor = new TextField();
		tf3BookYear = new TextField();
		tf4BookISBN = new TextField();
		
		tAR5BookDescription = new TextArea();
		tAR5BookDescription.setPrefHeight(276);
		tAR5BookDescription.setPrefWidth(276);
		
		lbl1BookName = new Label("Book name:");
		lbl2BookAuthor = new Label("Book author:");
		lbl3BookYear = new Label("Book year:");
		lbl4BookISBN = new Label("ISBN:");
		lbl5BookDescription = new Label("Description:");
		lbl5BookDescription.setPadding(new Insets(0, 0, 5, 0));

		mainPane = new GridPane();
		picturePane = new GridPane();
		picturePanePlusBookDetails = new GridPane();
		descriptionSection = new VBox();
		operationsSection = new HBox();
		descriptionPanePlusOperations = new GridPane();

	}

	@Override
	public String getTitle() {
		return "Edit a book";
	}

	@Override
	public Parent getComponent() {
		
		final File f = new File(EditBookDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String removeInvalidTargetPath = "bin";
		String synchronizedPath = filePath.replace(removeInvalidTargetPath, "src");
		String outputPath = "file:" + synchronizedPath + File.separator + "com" + File.separator + "via"
				+ File.separator + "clms" + File.separator + "client" + File.separator + "graphics" + File.separator
				+ "books.png";
		Image imageDir = new Image(outputPath);
		ImageView viewImg = new ImageView();
		viewImg.setImage(imageDir);
		
		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\
		
		btn1Update = new Button("Update information");
		btn2Cancel = new Button("Cancel");
		
		btn1Update.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			if (tf1BookName.getText().isEmpty()
				|| tf2BookAuthor.getText().isEmpty()
				|| tf3BookYear.getText().isEmpty()
				|| tf4BookISBN.getText().isEmpty()) {
				Alert alertFailiure = new Alert(AlertType.ERROR);
				alertFailiure.setTitle("Error Dialog");
				alertFailiure.setHeaderText("No information was filled in");
				alertFailiure.setContentText("Please fill in all fields!");
				alertFailiure.showAndWait();
			} else {
			// TODO - Execute function ---> Edit book details
			}
			}
			});
		
		btn2Cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				windowInstance.close();
			}
		});

		// \\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\

		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));
		
		picturePanePlusBookDetails.setPadding(new Insets(0, 2, 0, 0));
		
		descriptionSection.setPadding(new Insets(0, 0, 5, 0));
		
		operationsSection.setAlignment(Pos.CENTER_RIGHT);
		
		descriptionPanePlusOperations.setPadding(new Insets(0, 0, 0, 5));

		picturePane.add(viewImg, 0, 0);
		picturePane.setPadding(new Insets(0, 0, 5, 40));
		
		descriptionSection.getChildren().addAll(lbl5BookDescription, tAR5BookDescription);
		descriptionSection.setSpacing(5);

		operationsSection.getChildren().addAll(btn1Update, btn2Cancel);
		operationsSection.setSpacing(5);
		
		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\
		
		descriptionPanePlusOperations.add(descriptionSection, 0, 0);
		descriptionPanePlusOperations.add(operationsSection, 0, 1);

		picturePanePlusBookDetails.add(picturePane, 0, 0);
		picturePanePlusBookDetails.add(lbl1BookName, 0, 1);
		picturePanePlusBookDetails.add(tf1BookName, 0, 2);
		picturePanePlusBookDetails.add(lbl2BookAuthor, 0, 3);
		picturePanePlusBookDetails.add(tf2BookAuthor, 0, 4);
		picturePanePlusBookDetails.add(lbl3BookYear, 0, 5);
		picturePanePlusBookDetails.add(tf3BookYear, 0, 6);
		picturePanePlusBookDetails.add(lbl4BookISBN, 0, 7);
		picturePanePlusBookDetails.add(tf4BookISBN, 0, 8);

		mainPane.add(picturePanePlusBookDetails, 0, 0);
		mainPane.add(descriptionPanePlusOperations, 1, 0);

		return mainPane;
	}

	@Override
	public void onWindowOpen(Window win) {
		windowInstance = win;
	}

	@Override
	public void onWindowClose(Window win) {
		windowInstance = win;
	}

	@Override
	public void onWindowRefresh(Window win) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWindowResume(Window win) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWindowPause(Window win) {
		// TODO Auto-generated method stub

	}

}