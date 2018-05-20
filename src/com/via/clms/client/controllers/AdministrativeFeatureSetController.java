package com.via.clms.client.controllers;

import com.via.clms.client.controllers.containers.BookTable;
import com.via.clms.client.controllers.containers.LibraryTable;
import com.via.clms.client.controllers.containers.UserTable;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;
import com.via.clms.shared.Book;
import com.via.clms.shared.Library;
import com.via.clms.shared.User;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdministrativeFeatureSetController implements Controller {
	
	Window windowInstance;
	
	LibraryTable libraryTable;
	UserTable userTable;
	BookTable bookTable;

	private GridPane mainPane;
	private GridPane librariesLeftPane;
	private GridPane bookOperationsRightPane;
	private GridPane userOperationsMiddlePane;
	
	private ScrollPane libraryTableScrollPane;
	private ScrollPane userTableScrollPane;
	private ScrollPane bookTableScrollPane;
	
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
		
		libraryTableScrollPane = new ScrollPane();
		userTableScrollPane = new ScrollPane();
		bookTableScrollPane = new ScrollPane();
		
		tf1SearchLibraries = new TextField();
		tf2SearchUsersByCPR = new TextField();
		tf3SearchBooksByISBN = new TextField();
		
		lbl1CurrentLibraries = new Label("All Current Libraries:");
		lbl2LibraryOperations = new Label("Library Operations:");
		lbl3Users = new Label("All Current Users:");
		lbl4UserOperations = new Label("User Operations:");
		lbl5Books = new Label("All Current Books:");
		lbl6BookOperations = new Label("Book Operations:");
		
		libraryTable = new LibraryTable();
		userTable = new UserTable();
		bookTable = new BookTable();
		
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
		mainPane.setPadding(new Insets(20, 15, 20, 15));
		
		librariesLeftPane.setPadding(new Insets(0, 10, 0, 10));
		
		libraryTableScrollPane.setPrefHeight(320);
		userTableScrollPane.setPrefHeight(320);
		bookTableScrollPane.setPrefHeight(320);
		
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
					Library[] libraries = new Library[1];
					libraries[0] = new Library(0001, "CLMS", "Horsens"); 
					libraryTable.populate(libraries);
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
				} else if (tf2SearchUsersByCPR.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter valid search criteria!");
					alertFailiure.showAndWait();
				}
				else {
					User[] users = new User[1];
					users[0] = new User(0001); 
					userTable.populate(users);
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
					Book[] books = new Book[1];
					books[0] = new Book(1010, "CLMS Guide", 40, "999", "getDescription", 2018, "An author", "Horsens" );
					bookTable.populate(books);
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
		
		libraryTableScrollPane.setContent(libraryTable);
		libraryTableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		currentLibrariesSection.getChildren().addAll(lbl1CurrentLibraries, innerCurrentLibrariesSection, libraryTableScrollPane);
		currentLibrariesSection.setSpacing(5);
		
		innerLibraryOperationsSection.getChildren().addAll(btn2CreateLibrary, btn3ModifyLibrary);
		innerLibraryOperationsSection.setSpacing(5);
		
		libraryOperationsSection.getChildren().addAll(lbl2LibraryOperations, innerLibraryOperationsSection, btn4RemoveLibrary);
		libraryOperationsSection.setSpacing(5);
		
		
		innerCurrentUsersSection.getChildren().addAll(tf2SearchUsersByCPR, btn5SearchUsersByCPR);
		innerCurrentUsersSection.setSpacing(5);
		
		userTableScrollPane.setContent(userTable);
		userTableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		currentUsersSection.getChildren().addAll(lbl3Users, innerCurrentUsersSection, userTableScrollPane);
		currentUsersSection.setSpacing(5);
		
		innerUserOperationsSection.getChildren().addAll(btn6CreateUser, btn7ModifyUser);
		innerUserOperationsSection.setSpacing(5);
		
		userOperationsSection.getChildren().addAll(lbl4UserOperations, innerUserOperationsSection, btn8RemoveUser);
		userOperationsSection.setSpacing(5);
		
		innerCurrentBooksSection.getChildren().addAll(tf3SearchBooksByISBN, btn9SearchBooksByISBN);
		innerCurrentBooksSection.setSpacing(5);
		
		bookTableScrollPane.setContent(bookTable);
		bookTableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		currentBooksSection.getChildren().addAll(lbl5Books, innerCurrentBooksSection, bookTableScrollPane);
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

	public boolean tableValueGuardNoBooksLibrariesSection() {
		if (libraryTable.getChildren().size() == 0) {
			return false;
		}
		return true;
	}
	
	public boolean tableValueGuardNoBooksUsersSection() {
		if (userTable.getChildren().size() == 0) {
			return false;
		}
		return true;
	}
	
	public boolean tableValueGuardNoBooksBooksSection() {
		if (bookTable.getChildren().size() == 0) {
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