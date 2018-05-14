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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdministrativeFeatureSetController implements Controller {
	
	private Libraries libraryDataOutput;
	private Users usersDataOutput;
	
	private GridPane mainPane;
	private GridPane librariesLeftPane;
	private GridPane bookOperationsRightPane;
	
	private GridPane currentUsersTablePane;
	private GridPane userOperationsMiddlePane;
	private VBox currentLibrariesSection;
	private VBox libraryOperationsSection;
	private HBox innerLibraryOperationsSection;
	private VBox currentUsersSection;
	private VBox userOperationsSection;
	private HBox innerUserOperationsSection;
	private VBox currentBooksSection;
	private VBox bookOperationsSection;
	private HBox innerBookOperationsSection;
	
	public TextField tf1SearchLibraries;
	public TextField tf2SearchUsersByCPR;
	public TextField tf3SearchBooksByUID;
	
	private Label lbl1CurrentLibraries;
	private Label lbl2LibraryOperations;
	private Label lbl3Users;
	private Label lbl4UserOperations;
	private Label lbl5Books;
	private Label lbl6BookOperations;
	
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
	private TableView<Books> tbView3LibraryBooks;
	private final ObservableList<Libraries> tableDataLibraries = updateTableLibraries();
	private final ObservableList<Users> tableDataUsers = updateTableUsers();
	private final ObservableList<Books> tableDataBooks = updateTableBooks();
	
	private TableColumn<Libraries, Integer> libraryIDCol1;
	private TableColumn<Libraries, String> libraryNameCol2;
	private TableColumn<Libraries, String> libraryLocationCol3;
	private TableColumn<Users, Integer> userIDCol4;
	private TableColumn<Users, String> userNameCol5;
	private TableColumn<Users, String> userEmailCol6;
	private TableColumn<Users, String> userRoleCol7;
	private TableColumn<Books, Integer> bookIDCol8;
	private TableColumn<Books, String> bookNameCol9;
	private TableColumn<Books, String> bookAuthorCol10;
	private TableColumn<Books, String> bookYearCol11;
	
	private Button btn1CreateLibrary;
	private Button btn2ModifyLibrary;
	private Button btn3RemoveLibrary;
	private Button btn4CreateUser;
	private Button btn5ModifyUser;
	private Button btn6RemoveUser;
	private Button btn7AddBook;
	private Button btn8EditBook;
	private Button btn9RemoveBook;
	
	public AdministrativeFeatureSetController() {
		
		mainPane = new GridPane();
		librariesLeftPane = new GridPane();
		bookOperationsRightPane = new GridPane();
		currentUsersTablePane = new GridPane();
		userOperationsMiddlePane = new GridPane();
		
		tf1SearchLibraries = new TextField();
		tf2SearchUsersByCPR = new TextField();
		tf3SearchBooksByUID = new TextField();
		
		lbl1CurrentLibraries = new Label("All Current Libraries:");
		lbl2LibraryOperations = new Label("Library Operations:");
		lbl3Users = new Label("All Current Users:");
		lbl4UserOperations = new Label("User Operations:");
		lbl5Books = new Label("All Current Books:");
		lbl6BookOperations = new Label("Book Operations:");
		
		tbView1Libraries = new TableView<Libraries>();
		tbView2LibraryUsers = new TableView<Users>();
		tbView3LibraryBooks = new TableView<Books>();
		
		currentLibrariesSection = new VBox();
		libraryOperationsSection = new VBox();
		innerLibraryOperationsSection = new HBox();
		currentUsersSection = new VBox();
		userOperationsSection = new VBox();
		innerUserOperationsSection = new HBox();
		currentBooksSection = new VBox();
		bookOperationsSection = new VBox();
		innerBookOperationsSection = new HBox();
		
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
		
		librariesLeftPane.setAlignment(Pos.CENTER_LEFT);
		librariesLeftPane.setPadding(new Insets(0, 10, 0, 10));
		
		userOperationsMiddlePane.setAlignment(Pos.CENTER);
		userOperationsMiddlePane.setPadding(new Insets(0, 10, 0, 0));
		
		// \\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\

		tf1SearchLibraries.setPrefColumnCount(18);
		tf2SearchUsersByCPR.setPrefColumnCount(18);
		tf3SearchBooksByUID.setPrefColumnCount(18);
		
		// \\/\\/\\/\\/\\-=Table Column Properties=-//\\/\\/\\/\\/\\
		
		libraryIDCol1 = new TableColumn<Libraries, Integer>("LbrID");
		libraryIDCol1.setPrefWidth(50);
		libraryIDCol1.setCellValueFactory(new PropertyValueFactory<Libraries, Integer>("libraryID"));
	
		libraryNameCol2 = new TableColumn<Libraries, String>("Name");
		libraryNameCol2.setPrefWidth(125);
		libraryNameCol2.setCellValueFactory(new PropertyValueFactory<Libraries, String>("libraryName"));
		
		libraryLocationCol3 = new TableColumn<Libraries, String>("Location");
		libraryLocationCol3.setPrefWidth(70);
		libraryLocationCol3.setCellValueFactory(new PropertyValueFactory<Libraries, String>("libraryLocation"));
		
		userIDCol4 = new TableColumn<Users, Integer>("UsrID");
		userIDCol4.setPrefWidth(50);
		userIDCol4.setCellValueFactory(new PropertyValueFactory<Users, Integer>("userID"));
		
		userNameCol5 = new TableColumn<Users, String>("Name");
		userNameCol5.setPrefWidth(105);
		userNameCol5.setCellValueFactory(new PropertyValueFactory<Users, String>("userName"));
		
		userEmailCol6 = new TableColumn<Users, String>("Email");
		userEmailCol6.setPrefWidth(50);
		userEmailCol6.setCellValueFactory(new PropertyValueFactory<Users, String>("userEmail"));
		
		userRoleCol7 = new TableColumn<Users, String>("Role");
		userRoleCol7.setPrefWidth(50);
		userRoleCol7.setCellValueFactory(new PropertyValueFactory<Users, String>("userRole"));
		
		bookIDCol8 = new TableColumn<Books, Integer>("BkID");
		bookIDCol8.setPrefWidth(50);
		bookIDCol8.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookID"));
		
		bookNameCol9 = new TableColumn<Books, String>("Name");
		bookNameCol9.setPrefWidth(75);
		bookNameCol9.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
		
		bookAuthorCol10 = new TableColumn<Books, String>("Author");
		bookAuthorCol10.setPrefWidth(70);
		bookAuthorCol10.setCellValueFactory(new PropertyValueFactory<Books, String>("bookAuthor"));
		
		bookYearCol11 = new TableColumn<Books, String>("Year");
		bookYearCol11.setPrefWidth(50);
		bookYearCol11.setCellValueFactory(new PropertyValueFactory<Books, String>("bookYear"));
		
		
		tbView1Libraries.setItems(tableDataLibraries);
		tbView1Libraries.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView1Libraries.getColumns().addAll(libraryIDCol1, libraryNameCol2, libraryLocationCol3);
		tbView1Libraries.setPrefHeight(320);
		
		tbView2LibraryUsers.setItems(tableDataUsers);
		tbView2LibraryUsers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView2LibraryUsers.getColumns().addAll(userIDCol4, userNameCol5, userEmailCol6, userRoleCol7);
		tbView2LibraryUsers.setPrefHeight(320);
		
		tbView3LibraryBooks.setItems(tableDataBooks);
		tbView3LibraryBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView3LibraryBooks.getColumns().addAll(bookIDCol8, bookNameCol9, bookAuthorCol10, bookYearCol11);
		tbView3LibraryBooks.setPrefHeight(320);
		
		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1CreateLibrary = new Button("Create library");
		btn2ModifyLibrary = new Button("Modify library");
		btn3RemoveLibrary = new Button("Remove library");
		btn4CreateUser = new Button("Create user");
		btn5ModifyUser = new Button("Modify user");
		btn6RemoveUser = new Button("Remove user");
		btn7AddBook = new Button("Add book");
		btn8EditBook = new Button("Edit book details");
		btn9RemoveBook = new Button("Remove book");
		
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
		
		btn7AddBook.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btn8EditBook.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btn9RemoveBook.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		currentLibrariesSection.getChildren().addAll(lbl1CurrentLibraries, tbView1Libraries);
		currentLibrariesSection.setSpacing(5);
		
		libraryOperationsSection.getChildren().addAll(lbl2LibraryOperations, btn1CreateLibrary, btn2ModifyLibrary, btn3RemoveLibrary);
		libraryOperationsSection.setSpacing(5);
		
		currentUsersSection.getChildren().addAll(lbl3Users, tbView2LibraryUsers);
		currentUsersSection.setSpacing(5);
		
		userOperationsSection.getChildren().addAll(lbl4UserOperations, btn4CreateUser, btn5ModifyUser, btn6RemoveUser);
		userOperationsSection.setSpacing(5);
		
		currentBooksSection.getChildren().addAll(lbl5Books, tbView3LibraryBooks);
		currentBooksSection.setSpacing(5);
		
		bookOperationsSection.getChildren().addAll(lbl6BookOperations, btn7AddBook, btn8EditBook, btn9RemoveBook);
		bookOperationsSection.setSpacing(5);
		
		librariesLeftPane.add(currentLibrariesSection, 0, 0);
		librariesLeftPane.add(libraryOperationsSection, 0, 1);
		
		userOperationsMiddlePane.add(currentUsersSection, 0, 0);
		userOperationsMiddlePane.add(userOperationsSection, 0, 1);
		
		bookOperationsRightPane.add(currentBooksSection, 0, 0);
		bookOperationsRightPane.add(bookOperationsSection, 0, 1);
		
		mainPane.add(librariesLeftPane, 0, 0);
		mainPane.add(userOperationsMiddlePane, 1, 0);
		mainPane.add(bookOperationsRightPane, 2, 0);
		
		return mainPane;
		
	}

	public ObservableList<Users> updateTableUsers() {
		return null;
	}
	
	public ObservableList<Libraries> updateTableLibraries() {
		return null;
	}
	
	public ObservableList<Books> updateTableBooks() {
		return null;
	}
	
	public class Libraries {
		
	}
	
	public class Users {
		
	}
	
	public class Books {
		
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