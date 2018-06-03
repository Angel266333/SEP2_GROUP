package com.via.clms.client.controllers.administration;

import java.io.File;
import java.rmi.RemoteException;

import com.via.clms.Log;
import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;
import com.via.clms.proxy.ILibraryService;
import com.via.clms.shared.Library;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditLibraryDetailsController implements Controller {

	private GridPane mainPane;
	private GridPane picturePane;
	private GridPane picturePanePlusLibraryDetails;
	private VBox innerPictureSectionLibraryDetails;
	private HBox innerPictureSectionButtonActionsSection;
	
	private Window window;

	private TextField tf1LibraryName;
	private TextField tf2LibraryLocation;

	private Label lbl1LibraryName;
	private Label lbl2LibraryLocation;

	private Button btn1EditLibrary;
	private Button btn2Cancel;
	private Button btn3DeleteLibrary;
	
	private UserSession userSession;
	private AdministrativeFeatureSetController admCntrl;

	public EditLibraryDetailsController(UserSession userSession) {
		this.userSession = userSession;
	}

	@Override
	public String getTitle() {
		return "Library tools";
	}

	@Override
	public Parent getComponent() {

		lbl1LibraryName = new Label("Library name:");
		lbl2LibraryLocation = new Label("Library location:");

		mainPane = new GridPane();
		picturePane = new GridPane();
		picturePanePlusLibraryDetails = new GridPane();
		innerPictureSectionLibraryDetails = new VBox();
		innerPictureSectionButtonActionsSection = new HBox();

		final File f = new File(
				EditLibraryDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
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

		btn1EditLibrary = new Button("Edit library");
		btn2Cancel = new Button("Cancel");
		btn3DeleteLibrary = new Button("Delete this library");
		
		btn3DeleteLibrary.setStyle("-fx-background-color: #FF6060");

		btn1EditLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tf1LibraryName.getText().isEmpty() || tf2LibraryLocation.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No information was filled in");
					alertFailiure.setContentText("Please fill in all fields!");
					alertFailiure.showAndWait();
				} else {
					try {
						ILibraryService service = (ILibraryService) ServiceManager.getService("library");
						Library[] library = service.getLibraryByLID(admCntrl.getSearchIndex());
						if (tf1LibraryName.getText() == library[0].name) {
							Alert alertFailiure = new Alert(AlertType.ERROR);
							alertFailiure.setTitle("Error Dialog");
							alertFailiure.setHeaderText("Library already exists");
							alertFailiure.setContentText("Please enter a valid library name!");
							alertFailiure.showAndWait();
						}
						else {
						service.createLibrary(userSession.token, tf1LibraryName.getText(),
								tf2LibraryLocation.getText());
						Alert alertInformation = new Alert(AlertType.INFORMATION);
						alertInformation.setTitle("Success");
						alertInformation.setHeaderText("Library created");
						alertInformation.setContentText("New library successfully created!");
						alertInformation.showAndWait();
						}
					} catch (RuntimeException | RemoteException e) {
						Log.error(e);
					}
				}
			}
		});

		btn2Cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				window.close();
			}
		});
		
		btn3DeleteLibrary.setOnAction(new EventHandler<ActionEvent>() {

			ILibraryService service = (ILibraryService) ServiceManager.getService("library");
			@Override
			public void handle(ActionEvent arg0) {
					Alert alertConformation = new Alert(AlertType.CONFIRMATION);
					alertConformation.setTitle("Confirm remove");
					alertConformation.setHeaderText("Remove selected library");
					alertConformation.setContentText("You are about to remove a library. Continue?");
					alertConformation.showAndWait();
					try {
						Library[] library = service.getLibraryByName(tf1LibraryName.getText());
						int returnedResult = library[0].lid;
						boolean result = service.deleteLibrary(userSession.token, returnedResult);
						if (result == true) {
							Alert alertInformation = new Alert(AlertType.INFORMATION);
							alertInformation.setTitle("Successfully deleted");
							alertInformation.setHeaderText("Removed library");
							alertInformation.setContentText("Library successfully removed!");
							alertInformation.showAndWait();
						}
						btn1EditLibrary.setDisable(true);
						btn3DeleteLibrary.setDisable(true);
					} catch (RemoteException e) {
						Alert alertFailiure = new Alert(AlertType.ERROR);
						alertFailiure.setTitle("Error Dialog");
						alertFailiure.setHeaderText("Could not delete library");
						alertFailiure.setContentText("¯\\_(=))_/¯");
						alertFailiure.showAndWait();
					}
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

		innerPictureSectionLibraryDetails.getChildren().addAll(lbl1LibraryName, tf1LibraryName, lbl2LibraryLocation,
				tf2LibraryLocation);
		innerPictureSectionLibraryDetails.setSpacing(5);
		
		innerPictureSectionButtonActionsSection.getChildren().addAll(btn1EditLibrary, btn2Cancel, btn3DeleteLibrary);
		innerPictureSectionButtonActionsSection.setSpacing(5);

		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\

		picturePanePlusLibraryDetails.add(picturePane, 0, 0);
		picturePanePlusLibraryDetails.add(innerPictureSectionLibraryDetails, 0, 1);
		picturePanePlusLibraryDetails.add(innerPictureSectionButtonActionsSection, 0, 2);

		mainPane.add(picturePanePlusLibraryDetails, 0, 0);

		return mainPane;
	}
	
	public void setLibraryName(String name) {
		tf1LibraryName = new TextField(name);
	}
	
	public void setLibraryLocation(String location) {
		tf2LibraryLocation = new TextField(location);
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