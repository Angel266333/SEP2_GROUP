package com.via.clms.client.controllers;

import java.io.File;

import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ViewBookDetailsController implements Controller {
	
	Window windowInstance;

	private GridPane mainPane;
	private GridPane picturePane;
	private GridPane picturePanePlusBookDetails;
	private VBox descriptionSection;
	private GridPane descriptionPane;

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

	private UserSession userSession;
	
	public ViewBookDetailsController(UserSession userSession) {
		this.userSession = userSession;
	}

	@Override
	public String getTitle() {
		return "Book details";
	}

	@Override
	public Parent getComponent() {
		
		tf1BookName = new TextField();
		tf1BookName.setEditable(false);
		tf2BookAuthor = new TextField();
		tf2BookAuthor.setEditable(false);
		tf3BookYear = new TextField();
		tf3BookYear.setEditable(false);
		tf4BookISBN = new TextField();
		tf4BookISBN.setEditable(false);
		
		tAR5BookDescription = new TextArea();
		tAR5BookDescription.setPrefHeight(276);
		tAR5BookDescription.setPrefWidth(276);
		tAR5BookDescription.setEditable(false);
		
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
		descriptionPane = new GridPane();
		
		final File f = new File(ViewBookDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String removeInvalidTargetPath = "bin";
		String synchronizedPath = filePath.replace(removeInvalidTargetPath, "src");
		String outputPath = "file:" + synchronizedPath + File.separator + "com" + File.separator + "via"
				+ File.separator + "clms" + File.separator + "client" + File.separator + "graphics" + File.separator
				+ "books.png";
		Image imageDir = new Image(outputPath);
		ImageView viewImg = new ImageView();
		viewImg.setImage(imageDir);

		// \\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\

		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));
		
		picturePanePlusBookDetails.setPadding(new Insets(0, 2, 0, 0));
		
		descriptionSection.setPadding(new Insets(0, 0, 5, 0));
		
		descriptionPane.setPadding(new Insets(0, 0, 0, 5));

		picturePane.add(viewImg, 0, 0);
		picturePane.setPadding(new Insets(0, 0, 5, 40));
		
		descriptionSection.getChildren().addAll(lbl5BookDescription, tAR5BookDescription);
		descriptionSection.setSpacing(5);
		
		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\
		
		descriptionPane.add(descriptionSection, 0, 0);

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
		mainPane.add(descriptionPane, 1, 0);

		return mainPane;
	}

	@Override
	public void onWindowOpen(Window win) {
		
	}

	@Override
	public void onWindowClose(Window win) {

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