package com.via.clms.client.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.via.clms.client.ServiceManager;
import com.via.clms.client.views.ResultController;
import com.via.clms.client.views.Window;
import com.via.clms.proxy.IUserService;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController extends ResultController<byte[]> {

	Window window;
	
	private GridPane mainPane;
	private GridPane picturePane;
	private GridPane loginPane;
	private GridPane footerOperationsPane;
	
	private HBox pictureSection;
	private VBox loginSection;
	private HBox footerOperationsSection;
	
	private TextField tf1UserName;
	
	private PasswordField pwd1UserPassword;
	
	private String labelStyle;
	
	private Label lbl1UserName;
	private Label lbl2UserPassword;
	
	public ComboBox<String> cb1Libraries;
	
	private Button btn1Login;
	private Button btn2Help;
	
	private int lid;
		
		public LoginController(int libraryid) {
			lid = libraryid;
		}
	

	@Override
	public String getTitle() {
		return "CLMS login";
	}

	@Override
	public Parent getComponent() {
		
		mainPane = new GridPane();
		picturePane = new GridPane();
		loginPane = new GridPane();
		footerOperationsPane = new GridPane();
		
		tf1UserName = new TextField();
		
		pwd1UserPassword = new PasswordField();
		
		labelStyle = "-fx-font-weight: bold";
		
		lbl1UserName = new Label("User Name:");
		lbl1UserName.setPadding(new Insets(5, 0, 0, 0));
		lbl1UserName.setStyle(labelStyle);
		lbl2UserPassword = new Label("Password:");
		lbl2UserPassword.setPadding(new Insets(5, 0, 0, 0));
		lbl2UserPassword.setStyle(labelStyle);
		
		cb1Libraries = new ComboBox<String>();
		cb1Libraries.setPromptText("Select library");
		cb1Libraries.setPrefWidth(150);
		cb1Libraries.getItems().addAll(setLibraries());
		
		pictureSection = new HBox();
		loginSection = new VBox();
		footerOperationsSection = new HBox();
		
		mainPane.setAlignment(Pos.CENTER_LEFT);
		mainPane.setPadding(new Insets(10, 5, 20, 5));
		
		loginPane.setAlignment(Pos.CENTER_LEFT);
		loginPane.setPadding(new Insets(0, 0, 10, 0));
		
		footerOperationsSection.setPadding(new Insets(0, 0, 0, 8));
		
		// \\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\
		
		tf1UserName.setPrefColumnCount(20);
		
		// \\/\\/\\/\\/\\-=PasswordFields=-//\\/\\/\\/\\/\\
		
		pwd1UserPassword.setPrefColumnCount(20);
		
		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1Login = new Button("Login");
		btn2Help = new Button("", new ImageView(getHelpImage()));
		
		// \\/\\/\\/\\/\\-=Event Handlers=-//\\/\\/\\/\\/\\

		btn1Login.setOnAction(new EventHandler<ActionEvent>() {
			
		@Override
		public void handle(ActionEvent arg0) {

			if (tf1UserName.getText().isEmpty() || pwd1UserPassword.getText().isEmpty()) {
				Alert alertFailiure = new Alert(AlertType.ERROR);
				alertFailiure.setTitle("Error Dialog");
				alertFailiure.setHeaderText("Login information fields empty");
				alertFailiure.setContentText("Please fill in all fields!");
				alertFailiure.showAndWait();
			} else if (cb1Libraries.getSelectionModel().getSelectedIndex() == -1) {
				Alert alertFailiure = new Alert(AlertType.ERROR);
				alertFailiure.setTitle("Error Dialog");
				alertFailiure.setHeaderText("Library not selected");
				alertFailiure.setContentText("Please select a library to log onto!");
				alertFailiure.showAndWait();
			}
				else {
			// Get the cpr and password from the input fields
			String cpr = tf1UserName.getText();
			String passwd = pwd1UserPassword.getText();
			// Get the user service
			IUserService service = (IUserService) ServiceManager.getService("user");
			
			try {
				// Get the users token, if the information is valid
				byte[] token = service.getUserToken(Long.parseLong(cpr), passwd);
				
				// If the information is valid, pass the token to the caller
				if (token != null) {
					if (service.checkPermissions(token, lid, IUserService.ROLE_LOGIN)) {
						setResult(token);
						getWindow().close();
					}
					else {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Permission denied");
					alertFailiure.setContentText("You do not have the given permissions to log in!");
					alertFailiure.show();
				}
				}
			} catch (Exception e) {
			Alert alertFailiure = new Alert(AlertType.ERROR);
			alertFailiure.setTitle("Error Dialog");
			alertFailiure.setHeaderText("Invalid login");
			alertFailiure.setContentText("Could not log in! General error.");
			alertFailiure.show();
		}
				}	
		}
		});
		
		btn2Help.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				final File f = new File(ViewBookDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				String filePath = f.toString();
				String removeInvalidTargetPath = "bin";
				String synchronizedPath = filePath.replace(removeInvalidTargetPath, "src");
				String outputPath = synchronizedPath + File.separator + "com" + File.separator + "via"
						+ File.separator + "clms" + File.separator + "client" + File.separator + "help" + File.separator
						+ "DemoGuide.pdf";
				if (Desktop.isDesktopSupported()) {
				    try {
				        File myFile = new File(outputPath);
				        Desktop.getDesktop().open(myFile);
				    } catch (IOException e) {
				    	Alert alertFailiure = new Alert(AlertType.ERROR);
						alertFailiure.setTitle("Error Dialog");
						alertFailiure.setHeaderText("Load resource failiure");
						alertFailiure.setContentText("Could not load selected resource!");
						alertFailiure.showAndWait();
						e.printStackTrace();
				    }
				}
				}

		});
		
		// \\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\

		pictureSection.getChildren().add(getCLMSLogoImage());
;		
		loginSection.setAlignment(Pos.CENTER_LEFT);
		loginSection.getChildren().addAll(lbl1UserName, tf1UserName, lbl2UserPassword, pwd1UserPassword);
		loginSection.setSpacing(5);
		
		footerOperationsSection.setAlignment(Pos.CENTER);
		footerOperationsSection.getChildren().addAll(btn1Login, cb1Libraries, btn2Help);
		footerOperationsSection.setSpacing(18);
		
		// \\/\\/\\/\\/\\-=Compact Containers To Panes=-//\\/\\/\\/\\/\\

		picturePane.add(pictureSection, 0, 0);
		
		loginPane.add(loginSection, 0, 0);
		
		picturePane.add(getCLMSLogoImage(), 0, 0);
		
		footerOperationsPane.add(footerOperationsSection, 0, 0);
		
		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\

		mainPane.add(picturePane, 0, 0);
		mainPane.add(loginPane, 0, 1);
		mainPane.add(footerOperationsPane, 0, 2);
		
		return mainPane;
	}

	public ImageView getCLMSLogoImage() {
		final File f = new File(ViewBookDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String removeInvalidTargetPath = "bin";
		String synchronizedPath = filePath.replace(removeInvalidTargetPath, "src");
		String outputPath = "file:" + synchronizedPath + File.separator + "com" + File.separator + "via"
				+ File.separator + "clms" + File.separator + "client" + File.separator + "graphics" + File.separator
				+ "clms_logo.png";
		Image imageDirCLMSImg = new Image(outputPath);
		ImageView imageCLMS = new ImageView(imageDirCLMSImg);
		return imageCLMS;		
	}
	
	public Image getHelpImage() {
		final File f = new File(ViewBookDetailsController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String filePath = f.toString();
		String removeInvalidTargetPath = "bin";
		String synchronizedPath = filePath.replace(removeInvalidTargetPath, "src");
		String outputPath = "file:" + synchronizedPath + File.separator + "com" + File.separator + "via"
			+ File.separator + "clms" + File.separator + "client" + File.separator + "graphics" + File.separator
			+ "help.png";
		Image imageDirHelpImg = new Image(outputPath);
		return imageDirHelpImg;
	}
	

	public String[] setLibraries() {
		
		// TODO - Demo - get list from database.
		String[] demoList = new String[4];
		demoList[0] = new String("Horsens");
		demoList[1] = new String("Aarhus");
		demoList[2] = new String("Aalborg");
		demoList[3] = new String("Copenhagen");
		return demoList;
		
	}
	
	public int getLibrarySelectedIndex() {
		return cb1Libraries.getSelectionModel().getSelectedIndex();
	}
	
	
	@Override
	public void onWindowOpen(Window win) {
		window = win;
	}

	@Override
	public void onWindowClose(Window win) {
		window = win;
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
