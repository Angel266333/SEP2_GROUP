package com.via.clms.client.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.via.clms.client.controllers.SearchResultController.SearchResultData;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	private HBox innerCurrentLibrariesSection;
	private VBox currentLibrariesSection;
	private VBox libraryOperationsSection;
	private HBox innerLibraryOperationsSection;
	private HBox innerCurrentUsersSection;
	private VBox currentUsersSection;
	private VBox userOperationsSection;
	private HBox innerUserOperationsSection;
	private HBox innerCurrentBooksSection;
	private VBox currentBooksSection;
	private VBox bookOperationsSection;
	private HBox innerBookOperationsSection;
	
	public TextField tf1SearchLibraries;
	public TextField tf2SearchUsersByCPR;
	public TextField tf3SearchBooksByISBN;
	
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
	
	//! Watch the data type
	
	private TableColumn<Libraries, String> libraryIDCol1;
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
	
	
	
	private Button btn1SearchLibrariesByName;
	private Button btn2CreateLibrary;
	private Button btn3ModifyLibrary;
	private Button btn4RemoveLibrary;
	private Button btn5SearchUsersByCPR;
	private Button btn6CreateUser;
	private Button btn7ModifyUser;
	private Button btn8RemoveUser;
	private Button btn9SearchBooksByISBN;
	private Button btn10AddBook;
	private Button btn11ModifyBook;
	private Button btn12RemoveBook;
	
	public AdministrativeFeatureSetController() {
		
		mainPane = new GridPane();
		librariesLeftPane = new GridPane();
		bookOperationsRightPane = new GridPane();
		currentUsersTablePane = new GridPane();
		userOperationsMiddlePane = new GridPane();
		
		tf1SearchLibraries = new TextField();
		tf2SearchUsersByCPR = new TextField();
		tf3SearchBooksByISBN = new TextField();
		
		lbl1CurrentLibraries = new Label("All Current Libraries:");
		lbl2LibraryOperations = new Label("Library Operations:");
		lbl3Users = new Label("All Current Users:");
		lbl4UserOperations = new Label("User Operations:");
		lbl5Books = new Label("All Current Books:");
		lbl6BookOperations = new Label("Book Operations:");
		
		tbView1Libraries = new TableView<Libraries>();
		tbView2LibraryUsers = new TableView<Users>();
		tbView3LibraryBooks = new TableView<Books>();
		
		innerCurrentLibrariesSection = new HBox();
		currentLibrariesSection = new VBox();
		libraryOperationsSection = new VBox();
		innerLibraryOperationsSection = new HBox();
		innerCurrentUsersSection = new HBox();
		currentUsersSection = new VBox();
		userOperationsSection = new VBox();
		innerUserOperationsSection = new HBox();
		innerCurrentBooksSection = new HBox();
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
		
		librariesLeftPane.setPadding(new Insets(0, 10, 0, 10));
		
		libraryOperationsSection.setPadding(new Insets(10, 0, 0, 0));
		librariesLeftPane.setPadding(new Insets(0, 5, 0, 0));
		userOperationsSection.setPadding(new Insets(10, 0, 0, 0));
		userOperationsMiddlePane.setPadding(new Insets(0, 5, 0, 5));
		bookOperationsSection.setPadding(new Insets(10, 0, 0, 0));
		bookOperationsRightPane.setPadding(new Insets(0, 0, 0, 5));
		
		
		// \\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\

		tf1SearchLibraries.setPrefColumnCount(11);
		tf2SearchUsersByCPR.setPrefColumnCount(11);
		tf3SearchBooksByISBN.setPrefColumnCount(11);
		
		// \\/\\/\\/\\/\\-=Table Column Properties=-//\\/\\/\\/\\/\\
		
		libraryIDCol1 = new TableColumn<Libraries, String>("LbrID");
		libraryIDCol1.setPrefWidth(50);
		libraryIDCol1.setCellValueFactory(new PropertyValueFactory<Libraries, String>("libraryID"));
	
		libraryNameCol2 = new TableColumn<Libraries, String>("Name");
		libraryNameCol2.setPrefWidth(130);
		libraryNameCol2.setCellValueFactory(new PropertyValueFactory<Libraries, String>("libraryName"));
		
		libraryLocationCol3 = new TableColumn<Libraries, String>("Location");
		libraryLocationCol3.setPrefWidth(87);
		libraryLocationCol3.setCellValueFactory(new PropertyValueFactory<Libraries, String>("libraryLocation"));
		
		userIDCol4 = new TableColumn<Users, Integer>("UsrID");
		userIDCol4.setPrefWidth(50);
		userIDCol4.setCellValueFactory(new PropertyValueFactory<Users, Integer>("userID"));
		
		userNameCol5 = new TableColumn<Users, String>("Name");
		userNameCol5.setPrefWidth(108);
		userNameCol5.setCellValueFactory(new PropertyValueFactory<Users, String>("userName"));
		
		userEmailCol6 = new TableColumn<Users, String>("Email");
		userEmailCol6.setPrefWidth(55);
		userEmailCol6.setCellValueFactory(new PropertyValueFactory<Users, String>("userEmail"));
		
		userRoleCol7 = new TableColumn<Users, String>("Role");
		userRoleCol7.setPrefWidth(50);
		userRoleCol7.setCellValueFactory(new PropertyValueFactory<Users, String>("userRole"));
		
		bookIDCol8 = new TableColumn<Books, Integer>("BkID");
		bookIDCol8.setPrefWidth(50);
		bookIDCol8.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookID"));
		
		bookNameCol9 = new TableColumn<Books, String>("Name");
		bookNameCol9.setPrefWidth(95);
		bookNameCol9.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
		
		bookAuthorCol10 = new TableColumn<Books, String>("Author");
		bookAuthorCol10.setPrefWidth(89);
		bookAuthorCol10.setCellValueFactory(new PropertyValueFactory<Books, String>("bookAuthor"));
		
		bookYearCol11 = new TableColumn<Books, String>("Year");
		bookYearCol11.setPrefWidth(37);
		bookYearCol11.setCellValueFactory(new PropertyValueFactory<Books, String>("bookYear"));
		
		tbView1Libraries.setItems(tableDataLibraries);
		tbView1Libraries.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView1Libraries.getColumns().addAll(libraryIDCol1, libraryNameCol2, libraryLocationCol3);
		tbView1Libraries.setPrefHeight(280);
		
		tbView2LibraryUsers.setItems(tableDataUsers);
		tbView2LibraryUsers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView2LibraryUsers.getColumns().addAll(userIDCol4, userNameCol5, userEmailCol6, userRoleCol7);
		tbView2LibraryUsers.setPrefHeight(280);
		
		tbView3LibraryBooks.setItems(tableDataBooks);
		tbView3LibraryBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView3LibraryBooks.getColumns().addAll(bookIDCol8, bookNameCol9, bookAuthorCol10, bookYearCol11);
		tbView3LibraryBooks.setPrefHeight(280);
		
		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1SearchLibrariesByName = new Button("Search libraries by ID");
		btn2CreateLibrary = new Button("Create library");
		btn3ModifyLibrary = new Button("Modify library");
		btn4RemoveLibrary = new Button("Remove library");
		btn5SearchUsersByCPR = new Button("Search users by CPR");
		btn6CreateUser = new Button("Create user");
		btn7ModifyUser = new Button("Modify user");
		btn8RemoveUser = new Button("Remove user");
		btn9SearchBooksByISBN = new Button("Search books by ISBN");
		btn10AddBook = new Button("Add book");
		btn11ModifyBook = new Button("Edit book details");
		btn12RemoveBook = new Button("Remove book");
		
		btn1SearchLibrariesByName.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				String tf1Output = tf1SearchLibraries.getText();
				boolean atLeastOneAlpha = tf1Output.matches(".*[a-zA-Z]+.*");
				if (atLeastOneAlpha) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter only numbers!");
					alertFailiure.showAndWait();
				} else if (tf1SearchLibraries.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter valid search criteria!");
					alertFailiure.showAndWait();
				}
				else {
					System.out.println("Initialize search. Demo data added.");
					updateTableLibraries();
				}
			}
		});
		
		btn2CreateLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooksLibrariesSection() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected library");
					alertFailiure.setContentText("Please select a library first!");
					alertFailiure.showAndWait();
				} else {
				CreateLibraryController crtlbrcntrl = new CreateLibraryController();
				Window w = new Window(crtlbrcntrl);
				w.open();
			}
			}
		});
		
		btn3ModifyLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooksLibrariesSection() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected library");
					alertFailiure.setContentText("Please select a library first!");
					alertFailiure.showAndWait();
			} else {
				// WIP
			} 
		}	
		});
		
		btn4RemoveLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooksLibrariesSection() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected library");
					alertFailiure.setContentText("Please select a library first!");
					alertFailiure.showAndWait();
			} else {
				// WIP
			} 
		}	
		});
		
		btn5SearchUsersByCPR.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				String tf2Output = tf2SearchUsersByCPR.getText();
				boolean atLeastOneAlpha = tf2Output.matches(".*[a-zA-Z]+.*");
				if (atLeastOneAlpha) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter only numbers!");
					alertFailiure.showAndWait();
				} else if (tf1SearchLibraries.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter valid search criteria!");
					alertFailiure.showAndWait();
				}
				else {
					System.out.println("Initialize search");
					updateTableUsers();
					// Initialize search
				}
			}
		});
		
		btn6CreateUser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CreateUserController crlbrcntrl = new CreateUserController();
				Window w = new Window(crlbrcntrl);
				w.open();
			}
			
		});
		
		btn7ModifyUser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooksUsersSection() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected user");
					alertFailiure.setContentText("Please select a user first!");
					alertFailiure.showAndWait();
			} else {
				// WIP
			} 
		}	
		});
		btn8RemoveUser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooksUsersSection() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected user");
					alertFailiure.setContentText("Please select a user first!");
					alertFailiure.showAndWait();
			} else {
				// WIP
			} 
		}	
		});
		
		btn9SearchBooksByISBN.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				String tf2Output = tf2SearchUsersByCPR.getText();
				boolean atLeastOneAlpha = tf2Output.matches(".*[a-zA-Z]+.*");
				if (atLeastOneAlpha) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter only numbers!");
					alertFailiure.showAndWait();
				} else if (tf1SearchLibraries.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter valid search criteria!");
					alertFailiure.showAndWait();
				}
				else {
					System.out.println("Initialize search");
					updateTableBooks();
					// Initialize search
				}
			}
		});
		
		
		btn10AddBook.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				AddBookController addbkcntrl = new AddBookController();
				Window w = new Window(addbkcntrl);
				w.open();
			}
		});
		
		btn11ModifyBook.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooksBooksSection() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected book");
					alertFailiure.setContentText("Please select a book first!");
					alertFailiure.showAndWait();
			} else {
				// WIP
			} 
		}	
		});
		
		btn12RemoveBook.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooksBooksSection() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected book");
					alertFailiure.setContentText("Please select a book first!");
					alertFailiure.showAndWait();
			} else {
				// WIP
			} 
		}	
		});
		
		innerCurrentLibrariesSection.getChildren().addAll(tf1SearchLibraries, btn1SearchLibrariesByName);
		innerCurrentLibrariesSection.setSpacing(5);
		
		currentLibrariesSection.getChildren().addAll(lbl1CurrentLibraries, innerCurrentLibrariesSection, tbView1Libraries);
		currentLibrariesSection.setSpacing(5);
		
		innerLibraryOperationsSection.getChildren().addAll(btn2CreateLibrary, btn3ModifyLibrary);
		innerLibraryOperationsSection.setSpacing(5);
		
		libraryOperationsSection.getChildren().addAll(lbl2LibraryOperations, innerLibraryOperationsSection, btn4RemoveLibrary);
		libraryOperationsSection.setSpacing(5);
		
		innerCurrentUsersSection.getChildren().addAll(tf2SearchUsersByCPR, btn5SearchUsersByCPR);
		innerCurrentUsersSection.setSpacing(5);
		
		currentUsersSection.getChildren().addAll(lbl3Users, innerCurrentUsersSection, tbView2LibraryUsers);
		currentUsersSection.setSpacing(5);
		
		innerUserOperationsSection.getChildren().addAll(btn6CreateUser, btn7ModifyUser);
		innerUserOperationsSection.setSpacing(5);
		
		userOperationsSection.getChildren().addAll(lbl4UserOperations, innerUserOperationsSection, btn8RemoveUser);
		userOperationsSection.setSpacing(5);
		
		innerCurrentBooksSection.getChildren().addAll(tf3SearchBooksByISBN, btn9SearchBooksByISBN);
		innerCurrentBooksSection.setSpacing(5);
		
		currentBooksSection.getChildren().addAll(lbl5Books, innerCurrentBooksSection, tbView3LibraryBooks);
		currentBooksSection.setSpacing(5);
		
		innerBookOperationsSection.getChildren().addAll(btn10AddBook, btn11ModifyBook);
		innerBookOperationsSection.setSpacing(5);
		
		bookOperationsSection.getChildren().addAll(lbl6BookOperations, innerBookOperationsSection, btn12RemoveBook);
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
		return FXCollections.observableArrayList(
				new Users());
	}
	
	public ObservableList<Libraries> updateTableLibraries() {
		return FXCollections.observableArrayList(
				new Libraries("1", "1", "1"));
	}
	
	public ObservableList<Books> updateTableBooks() {
		return FXCollections.observableArrayList(
				new Books());
	}
	
	public class Libraries {
		
		private final SimpleStringProperty libraryID;
		private final SimpleStringProperty libraryName;
		private final SimpleStringProperty libraryLocation;


		public Libraries(String libraryID, String libraryName, String libraryLocation) {
			this.libraryID = new SimpleStringProperty(libraryID);
			this.libraryName = new SimpleStringProperty(libraryName);
			this.libraryLocation = new SimpleStringProperty(libraryLocation);
			
			// WIP - Not working properly
		}
		
	}
	
	public class Users {
		// WIP
	}
	
	public class Books {
		// WIP
	}

	public boolean tableValueGuardNoBooksLibrariesSection() {
		ObservableList<Libraries> noCells = tbView1Libraries.getSelectionModel().getSelectedItems();
		if (noCells.size() == 0) {
			return false;
		}
		return true;
	}
	
	public boolean tableValueGuardNoBooksUsersSection() {
		ObservableList<Users> noCells = tbView2LibraryUsers.getSelectionModel().getSelectedItems();
		if (noCells.size() == 0) {
			return false;
		}
		return true;
	}
	
	public boolean tableValueGuardNoBooksBooksSection() {
		ObservableList<Books> noCells = tbView3LibraryBooks.getSelectionModel().getSelectedItems();
		if (noCells.size() == 0) {
			return false;
		}
		return true;
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