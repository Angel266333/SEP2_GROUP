package com.via.clms.client.controllers.administration;

import java.io.File;
import java.rmi.RemoteException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;
import com.via.clms.proxy.IInventoryService;
import com.via.clms.server.services.InventoryService;
import com.via.clms.server.services.LibraryService;

import com.via.clms.shared.Book;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class CreateBookController implements Controller {
	
	private GridPane mainPane;
	private GridPane picturePane;
	private GridPane picturePanePlusBookDetails;
	private VBox descriptionSection;
	private HBox operationsSection;
	private GridPane descriptionPanePlusOperations;

	private TextField tf1BookName;
	private TextField tf2BookAuthor;
	private TextField tf4BookISBN;

	private TextArea tAR5BookDescription;

	private Label lbl1BookName;
	private Label lbl2BookAuthor;
	private Label lbl4BookISBN;
	private Label lbl5BookDescription;
	private Label lbl6Release;

	public String bookName;
	public String bookAuthor;
	public String bookYear;
	public String bookAvailability;
	
	private Button btn1AddBook;
	private Button btn2Cancel;

	private DatePicker datePicker;
	
	private Window windowInstance;
	private UserSession userSession;

	public CreateBookController(UserSession userSession) {
		this.userSession = userSession;
	}

	@Override
	public String getTitle() {
		return "Add a book";
	}

	@Override
	public Parent getComponent() {
		
		tf1BookName = new TextField();
		tf2BookAuthor = new TextField();
		tf4BookISBN = new TextField();

		tAR5BookDescription = new TextArea();
		tAR5BookDescription.setPrefHeight(276);
		tAR5BookDescription.setPrefWidth(276);
		
		lbl1BookName = new Label("Book name:");
		lbl2BookAuthor = new Label("Book author:");
		lbl4BookISBN = new Label("ISBN:");
		lbl5BookDescription = new Label("Description (Optional):");
		lbl5BookDescription.setPadding(new Insets(0, 0, 5, 0));
		lbl6Release = new Label("Release date:");

		datePicker = new DatePicker();

		mainPane = new GridPane();
		picturePane = new GridPane();
		picturePanePlusBookDetails = new GridPane();
		descriptionSection = new VBox();
		operationsSection = new HBox();
		descriptionPanePlusOperations = new GridPane();

		final File f = new File(CreateBookController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
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
		
		btn1AddBook = new Button("Add book");
		btn2Cancel = new Button("Cancel");

		btn1AddBook.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tf1BookName.getText().isEmpty()
						|| tf2BookAuthor.getText().isEmpty()
						|| tf4BookISBN.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No information was filled in");
					alertFailiure.setContentText("Please fill in all fields!");
					alertFailiure.showAndWait();
				} else {
					try {
						String name = tf1BookName.getText();
						String author = tf2BookAuthor.getText();
						long isbn = Long.parseLong(tf4BookISBN.getText());
						LocalDate ld = datePicker.getValue();
						Calendar cal = Calendar.getInstance();
						cal.setTime(Date.from(Instant.EPOCH));
						cal.set(Calendar.YEAR, ld.getYear());
						cal.set(Calendar.MONTH, ld.getMonthValue());
						cal.set(Calendar.DAY_OF_MONTH, ld.getDayOfMonth());
						long release = cal.getTime().getTime();
						Book b = new Book(-1, name, -1, "" + isbn, tAR5BookDescription.getText(), release, author, null);
						b.image = "";
						((IInventoryService) ServiceManager.getService("inventory")).addBook(userSession.token, userSession.lid, b);
					} catch(NumberFormatException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText("Invalid characters in number-only fields.");
						alert.showAndWait();
					} catch(RemoteException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText("Failed to add book to server.");
						alert.showAndWait();
					}
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

		operationsSection.getChildren().addAll(btn1AddBook, btn2Cancel);
		operationsSection.setSpacing(5);
		
		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\
		
		descriptionPanePlusOperations.add(descriptionSection, 0, 0);
		descriptionPanePlusOperations.add(operationsSection, 0, 1);

		picturePanePlusBookDetails.add(picturePane, 0, 0);
		picturePanePlusBookDetails.add(lbl1BookName, 0, 1);
		picturePanePlusBookDetails.add(tf1BookName, 0, 2);
		picturePanePlusBookDetails.add(lbl2BookAuthor, 0, 3);
		picturePanePlusBookDetails.add(tf2BookAuthor, 0, 4);
		picturePanePlusBookDetails.add(lbl4BookISBN, 0, 7);
		picturePanePlusBookDetails.add(tf4BookISBN, 0, 8);
		picturePanePlusBookDetails.add(lbl6Release, 0, 9);
		picturePanePlusBookDetails.add(datePicker, 0, 10);

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
