package com.via.clms.client.controllers;

import com.via.clms.client.controllers.containers.LibraryTable;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;
import com.via.clms.shared.Library;
import com.via.clms.shared.User;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdministrativeFeatureSetController implements Controller {
	
	private GridPane mainPane;
	private GridPane librariesLeftPane;
	private GridPane bookOperationsRightPane;
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
	
	public String libraryID;
	public String libraryName;
	public String libraryLocation;
	public String userID;
	public String userName;
	public String userCPR;
	public String userEmail;
	public String userRole;
	
	Window windowInstance;
	LibraryTable libraryTable;
	
	private TableView<Library> tbView1Libraries;
	private TableView<Users> tbView2LibraryUsers;
	private TableView<Books> tbView3LibraryBooks;
	
	private final ObservableList<Library> tableDataLibraries = updateTableLibraries();
	private final ObservableList<Users> tableDataUsers = updateTableUsers();
	private final ObservableList<Books> tableDataBooks = updateTableBooks();
	
	private TableColumn<Library, Integer> libraryIDCol1;
	private TableColumn<Library, String> libraryNameCol2;
	private TableColumn<Library, String> libraryLocationCol3;
	
	private TableColumn<Users, Integer> userIDCol4;
	private TableColumn<Users, Long> userCPRCol5;
	private TableColumn<Users, String> userNameCol6;
	private TableColumn<Users, String> userEmailCol7;
	private TableColumn<Users, String> userRoleCol8;
	
	private TableColumn<Books, Integer> bookIDCol9;
	private TableColumn<Books, Integer> bookISBNCol10;
	private TableColumn<Books, String> bookNameCol11;
	private TableColumn<Books, String> bookAuthorCol11;
	private TableColumn<Books, Integer> bookYearCol12;
	
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
	private Button btn13RemoveBook;
	private Button btn12BookToLibraryAssociation;
	
	public AdministrativeFeatureSetController() {
		
		mainPane = new GridPane();
		librariesLeftPane = new GridPane();
		bookOperationsRightPane = new GridPane();
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
		
		tbView1Libraries = new TableView<Library>();
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Parent getComponent() {
		
		// \\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\
		
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 15, 20, 15));
		
		librariesLeftPane.setPadding(new Insets(0, 10, 0, 10));
		
		libraryOperationsSection.setPadding(new Insets(10, 0, 0, 0));
		librariesLeftPane.setPadding(new Insets(0, 5, 0, 0));
		userOperationsSection.setPadding(new Insets(10, 0, 0, 0));
		userOperationsMiddlePane.setPadding(new Insets(0, 5, 0, 5));
		bookOperationsSection.setPadding(new Insets(10, 0, 0, 0));
		bookOperationsRightPane.setPadding(new Insets(0, 0, 0, 5));
		
		
		// \\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\

		tf1SearchLibraries.setPrefColumnCount(16);
		tf2SearchUsersByCPR.setPrefColumnCount(29);
		tf3SearchBooksByISBN.setPrefColumnCount(27);
		
		// \\/\\/\\/\\/\\-=Table Column Properties=-//\\/\\/\\/\\/\\
		
		libraryIDCol1 = new TableColumn<Library, Integer>("LbrID");
		libraryIDCol1.setPrefWidth(50);
		libraryIDCol1.setStyle("-fx-alignment: CENTER;");
		libraryIDCol1.setCellValueFactory(new PropertyValueFactory<Library, Integer>("libraryID"));
	
		libraryNameCol2 = new TableColumn<Library, String>("Name");
		libraryNameCol2.setPrefWidth(180);
		libraryNameCol2.setCellValueFactory(new PropertyValueFactory<Library, String>("libraryName"));
		
		libraryLocationCol3 = new TableColumn<Library, String>("Location");
		libraryLocationCol3.setPrefWidth(92);
		libraryLocationCol3.setStyle("-fx-alignment: CENTER;");
		libraryLocationCol3.setCellValueFactory(new PropertyValueFactory<Library, String>("libraryLocation"));
		
		userIDCol4 = new TableColumn<Users, Integer>("UsrID");
		userIDCol4.setPrefWidth(50);
		userIDCol4.setStyle("-fx-alignment: CENTER;");
		userIDCol4.setCellValueFactory(new PropertyValueFactory<Users, Integer>("userID"));
		
		userCPRCol5 = new TableColumn<Users, Long>("CPR");
		userCPRCol5.setPrefWidth(75);
		userCPRCol5.setStyle("-fx-alignment: CENTER;");
		userCPRCol5.setCellValueFactory(new PropertyValueFactory<Users, Long>("userCPR"));
		
		userNameCol6 = new TableColumn<Users, String>("Name");
		userNameCol6.setPrefWidth(130);
		userNameCol6.setCellValueFactory(new PropertyValueFactory<Users, String>("userName"));
		
		userEmailCol7 = new TableColumn<Users, String>("Email");
		userEmailCol7.setPrefWidth(130);
		userEmailCol7.setCellValueFactory(new PropertyValueFactory<Users, String>("userEmail"));
		
		userRoleCol8 = new TableColumn<Users, String>("Role");
		userRoleCol8.setPrefWidth(78);
		userRoleCol8.setStyle("-fx-alignment: CENTER;");

		userRoleCol8.setCellValueFactory(new PropertyValueFactory<Users, String>("userRole"));
		
		bookIDCol9 = new TableColumn<Books, Integer>("BkID");
		bookIDCol9.setPrefWidth(50);
		bookIDCol9.setStyle("-fx-alignment: CENTER;");
		bookIDCol9.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookID"));
		
		bookISBNCol10 = new TableColumn<Books, Integer>("ISBN");
		bookISBNCol10.setPrefWidth(93);
		bookISBNCol10.setStyle("-fx-alignment: CENTER;");
		bookISBNCol10.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookISBN"));
		
		bookNameCol11 = new TableColumn<Books, String>("Name");
		bookNameCol11.setPrefWidth(130);
		bookNameCol11.setCellValueFactory(new PropertyValueFactory<Books, String>("bookName"));
		
		bookAuthorCol11 = new TableColumn<Books, String>("Author");
		bookAuthorCol11.setPrefWidth(126);
		bookAuthorCol11.setCellValueFactory(new PropertyValueFactory<Books, String>("bookAuthor"));
		
		bookYearCol12 = new TableColumn<Books, Integer>("Year");
		bookYearCol12.setPrefWidth(50);
		bookYearCol12.setStyle("-fx-alignment: CENTER;");
		bookYearCol12.setCellValueFactory(new PropertyValueFactory<Books, Integer>("bookYear"));
		
		tbView1Libraries.setItems(tableDataLibraries);
		tbView1Libraries.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView1Libraries.getColumns().addAll(libraryIDCol1, libraryNameCol2, libraryLocationCol3);
		tbView1Libraries.setPrefHeight(280);
		tbView1Libraries.setPrefWidth(320);
		
		tbView2LibraryUsers.setItems(tableDataUsers);
		tbView2LibraryUsers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView2LibraryUsers.getColumns().addAll(userIDCol4, userCPRCol5, userNameCol6, userEmailCol7, userRoleCol8);
		tbView2LibraryUsers.setPrefHeight(280);
		tbView2LibraryUsers.setPrefWidth(465);
		
		tbView3LibraryBooks.setItems(tableDataBooks);
		tbView3LibraryBooks.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tbView3LibraryBooks.getColumns().addAll(bookIDCol9, bookISBNCol10, bookNameCol11, bookAuthorCol11, bookYearCol12);
		tbView3LibraryBooks.setPrefHeight(280);
		tbView3LibraryBooks.setPrefWidth(453);
		
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
		btn13RemoveBook = new Button("Remove book");
		btn12BookToLibraryAssociation = new Button("Associate book with library");
		
		btn4RemoveLibrary.setStyle("-fx-color: #FF9999");
		btn8RemoveUser.setStyle("-fx-color: #FF9999");
		btn13RemoveBook.setStyle("-fx-color: #FF9999");
		
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
					updateTableLibraries();
					System.out.println("Initialize search. Demo data added.");
				}
			}
		});
		
		btn2CreateLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CreateLibraryController crtlbrcntrl = new CreateLibraryController();
				Window w = new Window(crtlbrcntrl);
				w.open();
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
				Alert alertConformation = new Alert(AlertType.CONFIRMATION);
				alertConformation.setTitle("Confirm remove");
				alertConformation.setHeaderText("Remove selected library");
				alertConformation.setContentText("You are about to remove a library. Continue?");
				alertConformation.showAndWait();
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
				Alert alertConformation = new Alert(AlertType.CONFIRMATION);
				alertConformation.setTitle("Confirm remove");
				alertConformation.setHeaderText("Remove selected book");
				alertConformation.setContentText("You are about to remove a user. Continue?");
				alertConformation.showAndWait();
			} 
		}	
		});
		
		btn9SearchBooksByISBN.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				String tf3Output = tf3SearchBooksByISBN.getText();
				boolean atLeastOneAlpha = tf3Output.matches(".*[a-zA-Z]+.*");
				if (atLeastOneAlpha) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter only numbers!");
					alertFailiure.showAndWait();
				} else if (tf3SearchBooksByISBN.getText().isEmpty()) {
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
				EditBookDetailsController edtbkdtlscntrl = new EditBookDetailsController();
				Window w = new Window(edtbkdtlscntrl);
				w.open();
			} 
		}	
		});
		
		btn13RemoveBook.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooksBooksSection() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected book");
					alertFailiure.setContentText("Please select a book first!");
					alertFailiure.showAndWait();
			} else {
				Alert alertConformation = new Alert(AlertType.CONFIRMATION);
				alertConformation.setTitle("Confirm remove");
				alertConformation.setHeaderText("Remove selected book");
				alertConformation.setContentText("You are about to remove a book. Continue?");
				alertConformation.showAndWait();
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
		
		innerBookOperationsSection.getChildren().addAll(btn10AddBook, btn11ModifyBook, btn12BookToLibraryAssociation);
		innerBookOperationsSection.setSpacing(5);
		
		bookOperationsSection.getChildren().addAll(lbl6BookOperations, innerBookOperationsSection, btn13RemoveBook);
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
				
	}
	
	public ObservableList<Library> updateTableLibraries() {

		return FXCollections.observableArrayList(
				new Library(1, "Demo", "Demo"),
				new Library(2, "Demo", "Demo"));
	}
	
	public ObservableList<Books> updateTableBooks() {
		return FXCollections.observableArrayList(
				new Books(0001, "98337", "Demo", "Demo", 2018));

	}
	
//	public class Library {
//		
//		private int libraryID;
//		private String libraryName;
//		private String libraryLocation;
//
//
//		public Library(int libraryID, String libraryName, String libraryLocation) {
//			this.libraryID = libraryID;
//			this.libraryName = libraryName;
//			this.libraryLocation = libraryLocation;
//			
//		}
//		public int getLibraryID() {
//			return this.libraryID;
//		}
//		
//		public void setLibraryID(int libraryID) {
//			this.libraryID = libraryID;
//		}
//		
//		public String getLibraryName() {
//			return this.libraryName;
//		}
//		
//		public void setLibraryName(String libraryName) {
//			this.libraryName = libraryName;
//		}
//		
//		public String getLibraryLocation() {
//			return this.libraryLocation;
//		}
//		
//		public void setLibraryLocation(String libraryLocation) {
//			this.libraryLocation = libraryLocation;
//		}
//	}
	
	public class Users {

		private final SimpleIntegerProperty userID;
		private final SimpleLongProperty userCPR;
		private final SimpleStringProperty userName;
		private final SimpleStringProperty userEmail;
		private final SimpleStringProperty userRole;
		
		public Users(int userID, long userCPR, String userName, String userEmail, String userRole) {
			this.userID = new SimpleIntegerProperty(userID);
			this.userCPR = new SimpleLongProperty(userCPR);
			this.userName = new SimpleStringProperty(userName);
			this.userEmail = new SimpleStringProperty(userEmail);
			this.userRole = new SimpleStringProperty(userRole);
			
		}
		
		public int getUserID() {
			return userID.get();
		}
		
		public void setUserID(int userID) {
			this.userID.set(userID);	
		}
		
		public long getUserCPR() {
			return userCPR.get(); 
		}
		
		public void setUserCPR(long userCPR) {
			this.userCPR.set(userCPR);
		}
		
		public String getUserName() {
			return this.userName.get();
		}
		
		public void setUserName(String userName) {
			this.userName.set(userName);
		}
		
		public String getUserEmail() {
			return this.userEmail.get();
		}
		
		public void setUserEmail(String userEmail) {
			this.userEmail.set(userEmail);
		}
		
		public String getUserRole() {
			return this.userRole.get();
		}
		
		public void setUserRole(String userRole) {
			this.userRole.set(userRole);
		}
	}
	
	public class Books {
		
		private int bookID;
		private String bookISBN;
		private String bookName;
		private String bookAuthor;
		private int bookYear;
		
		public Books(int bookID, String bookISBN, String bookName, String bookAuthor, int bookYear) {
			this.bookID = bookID;
			this.bookISBN = bookISBN;
			this.bookName = bookName;
			this.bookAuthor = bookAuthor;
			this.bookYear = bookYear;
			
		}
		
		public int getBookID() {
			return this.bookID;	
		}
		
		public void setBookID(int bookID) {
			this.bookID = bookID;
		}
		
		public String getBookISBN() {
			return this.bookISBN;
		}
		
		public void setBookISBN(String bookISBN) {
			this.bookISBN = bookISBN;
		}
		
		public String getBookName() {
			return this.bookName;
		}
		
		public void setBookName(String bookName) {
			this.bookName = bookName;
		}
		
		public String getBookAuthor() {
			return this.bookAuthor;
		}
		
		public void setBookAuthor(String bookAuthor) {
			this.bookAuthor = bookAuthor;
		}
		
		public int getBookYear() {
			return this.bookYear;
		}
		
		public void setBookYear(int bookYear) {
			this.bookYear = bookYear;
		}
	}

	public boolean tableValueGuardNoBooksLibrariesSection() {
		ObservableList<Library> noCells = tbView1Libraries.getSelectionModel().getSelectedItems();
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