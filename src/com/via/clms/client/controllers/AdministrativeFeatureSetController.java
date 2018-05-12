package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AdministrativeFeatureSetController implements Controller {
	
	private Libraries libraryDataOutput;
	private Users usersDataOutput;
	
	private GridPane mainPane;
	private GridPane currentLibrariesTable;
	private GridPane libraryOperations;
	private GridPane currentUsersTable;
	private GridPane userOperations;
	private VBox currentLibrariesSection;
	private VBox libraryOperationsSection;
	private VBox currentUsersSection;
	private VBox userOperationsSection;
	
	public TextField tf1SearchLibraries;
	public TextField tf2SearchUsersByCPR;
	
	private Label lbl1CurrentLibraries;
	private Label lbl2LibraryOperations;
	private Label lbl3LibraryUsers;
	private Label lbl4LibraryUserOperations;
	
	public int libraryID;
	public String libraryName;
	public String libraryLocation;
	public int userID;
	public String userName;
	public int userCPR;
	public String userEmail;
	public String userRole;
	
	Window windowInstance;
	
	private TableView<Libraries> tbView1Libraries;
	private TableView<Users> tbView2LibraryUsers;
	private final ObservableList<Libraries> tableDataLibraries = updateTableLibraries();
	private final ObservableList<Users> tableDataUsers = updateTableUsers();
	
	private TableColumn<Libraries, Integer> libraryIDCol1;
	private TableColumn<Libraries, String> libraryNameCol2;
	private TableColumn<Libraries, String> libraryLocationCol3;
	private TableColumn<Users, Integer> userIDCol4;
	private TableColumn<Users, String> userNameCol5;
	private TableColumn<Users, String> userEmailCol6;
	private TableColumn<Users, String> userRoleCol7;
	
	private Button btn1CreateLibrary;
	private Button btn2ModifyLibrary;
	private Button btn3RemoveLibrary;
	private Button btn4CreateUser;
	private Button btn5ModifyUser;
	private Button btn6RemoveUser;
	
	public AdministrativeFeatureSetController() {
		
		mainPane = new GridPane();
		currentLibrariesTable = new GridPane();
		libraryOperations = new GridPane();
		currentUsersTable = new GridPane();
		userOperations = new GridPane();
		
		tf1SearchLibraries = new TextField();
		tf2SearchUsersByCPR = new TextField();
		
		lbl1CurrentLibraries = new Label("Current Libraries:");
		lbl2LibraryOperations = new Label("Library Operations:");
		lbl3LibraryUsers = new Label("Current Users:");
		lbl4LibraryUserOperations = new Label("User Operations:");
		
		tbView1Libraries = new TableView<Libraries>();
		tbView2LibraryUsers = new TableView<Users>();
		
		currentLibrariesSection = new VBox();
		libraryOperationsSection = new VBox();
		currentUsersSection = new VBox();
		userOperationsSection = new VBox();
		
	}
	
	@Override
	public String getTitle() {
		return "CLMS Administration Panel";
	}
	
	@Override
	public Parent getComponent() {
		
		// \\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\
		
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));
		
		// \\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\

		tf1SearchLibraries.setPrefColumnCount(18);
		tf2SearchUsersByCPR.setPrefColumnCount(18);
		
		// \\/\\/\\/\\/\\-=Table Column Properties=-//\\/\\/\\/\\/\\
		
		libraryIDCol1 = new TableColumn<Libraries, Integer>("LID");
		libraryIDCol1.setPrefWidth(50);
		libraryIDCol1.setCellValueFactory(new PropertyValueFactory<Libraries, Integer>("libraryID"));
	
		libraryNameCol2 = new TableColumn<Libraries, String>("Name");
		libraryNameCol2.setPrefWidth(50);
		libraryNameCol2.setCellValueFactory(new PropertyValueFactory<Libraries, String>("libraryName"));
		
		libraryLocationCol3 = new TableColumn<Libraries, String>("Location");
		libraryLocationCol3.setPrefWidth(50);
		libraryLocationCol3.setCellValueFactory(new PropertyValueFactory<Libraries, String>("libraryLocation"));
		
		userIDCol4 = new TableColumn<Users, Integer>("UID");
		userIDCol4.setPrefWidth(50);
		userIDCol4.setCellValueFactory(new PropertyValueFactory<Users, Integer>("userID"));
		
		userNameCol5 = new TableColumn<Users, String>("Name");
		userNameCol5.setPrefWidth(50);
		userNameCol5.setCellValueFactory(new PropertyValueFactory<Users, String>("userName"));
		
		userEmailCol6 = new TableColumn<Users, String>("Email");
		userEmailCol6.setPrefWidth(50);
		userEmailCol6.setCellValueFactory(new PropertyValueFactory<Users, String>("userEmail"));
		
		userRoleCol7 = new TableColumn<Users, String>("Role");
		userRoleCol7.setPrefWidth(50);
		userRoleCol7.setCellValueFactory(new PropertyValueFactory<Users, String>("userRole"));
		
		tbView1Libraries.setItems(tableDataLibraries);
		tbView1Libraries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tbView1Libraries.getColumns().addAll(libraryIDCol1, libraryNameCol2, libraryLocationCol3);
		
		tbView2LibraryUsers.setItems(tableDataUsers);
		tbView2LibraryUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tbView2LibraryUsers.getColumns().addAll(userNameCol5, userEmailCol6, userRoleCol7);
		
		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1CreateLibrary = new Button("Create library");
		btn2ModifyLibrary = new Button("Modify library");
		btn3RemoveLibrary = new Button("Remove library");
		btn4CreateUser = new Button("Create user");
		btn5ModifyUser = new Button("Modify user");
		btn6RemoveUser = new Button("Remove user");
		
		btn1CreateLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		btn2ModifyLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		btn3RemoveLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn4CreateUser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		btn5ModifyUser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		btn6RemoveUser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		return mainPane;
		
	}

	public ObservableList<Users> updateTableUsers() {
		return null;
	}
	
	public ObservableList<Libraries> updateTableLibraries() {
		return null;
	}
	
	public class Libraries {
		
	}
	
	public class Users {
		
	}
	
	@Override
	public void onWindowOpen(Window win) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWindowClose(Window win) {
		// TODO Auto-generated method stub
		
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
