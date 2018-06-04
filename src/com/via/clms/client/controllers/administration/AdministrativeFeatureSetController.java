package com.via.clms.client.controllers.administration;

import java.rmi.RemoteException;

import com.via.clms.Log;
import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.BookTable;
import com.via.clms.client.controllers.containers.ClickListener;
import com.via.clms.client.controllers.containers.LibraryTable;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.controllers.containers.UserTable;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;
import com.via.clms.proxy.IInventoryService;
import com.via.clms.proxy.ILibraryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.shared.Book;
import com.via.clms.shared.Library;
import com.via.clms.shared.User;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdministrativeFeatureSetController implements Controller {

	LibraryTable libraryTable;
	UserTable userTable;
	BookTable bookTable;
	Window windowInstance;

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

	private Button btn1SearchLibrariesByID;
	private Button btn2CreateLibrary;
	private Button btn3SearchUsersByCPR;
	private Button btn4CreateUser;
	private Button btn5SearchBooksByISBN;
	private Button btn6AddBook;

	public int inputSearchLibraryID;
	public int inputSearchUserCPR;
	public int inputSearchBookISBN;

	private UserSession userSession;

	public AdministrativeFeatureSetController(UserSession userSession) {
		this.userSession = userSession;
	}

	@Override
	public String getTitle() {
		return "CLMS Administration Panel";
	}

	@Override
	public Parent getComponent() {

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
		
		populateLibraryTableOnWindowLoad();
		populateUserTableOnWindowLoad();
		populateBookTableOnWindowLoad();

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

		inputSearchLibraryID = 0;

		// \\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\

		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(0, 15, 20, 50));

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

		tf1SearchLibraries.setPrefColumnCount(20);
		tf2SearchUsersByCPR.setPrefColumnCount(16);
		tf3SearchBooksByISBN.setPrefColumnCount(27);

		// \\/\\/\\/\\/\\-=Table Column Properties=-//\\/\\/\\/\\/\\

		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1SearchLibrariesByID = new Button("Search libraries by ID");
		btn2CreateLibrary = new Button("Create library");
		btn3SearchUsersByCPR = new Button("Search users by CPR");
		btn4CreateUser = new Button("Create user");
		btn5SearchBooksByISBN = new Button("Search books by ISBN");
		btn6AddBook = new Button("Add book");

		libraryTable.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				libraryTable.setListener(new ClickListener() {

					Library[] libraries = null;
					Library library = null;

					@Override
					public void doubleClick(int i) {
						ILibraryService service = (ILibraryService) ServiceManager.getService("library");
						try {
							libraries = service.getLibraries(0, Integer.MAX_VALUE);

							if (inputSearchLibraryID == 0) {
								try {
								library = libraries[i];
								} catch (ArrayIndexOutOfBoundsException e) {
									Alert alertFailiure = new Alert(AlertType.ERROR);
									alertFailiure.setTitle("Error Dialog");
									alertFailiure.setHeaderText("Library does not exist");
									alertFailiure.setContentText("Could not find library!");
									alertFailiure.showAndWait();
									return;
								}
							} else {
								setSearchIndex(i--);
								library = libraries[inputSearchLibraryID];
							}

						} catch (RemoteException e) {
							e.printStackTrace();
						}

						EditLibraryDetailsController edtLibraryDtlsController = new EditLibraryDetailsController(userSession);
						Window w = new Window(edtLibraryDtlsController);
						edtLibraryDtlsController.setLibraryName(library.name);
						edtLibraryDtlsController.setLibraryLocation(library.location);
						w.open();
					}

					@Override
					public void click(int i) {
						setSearchIndex(i);
					}
				});

			}
		});

		btn1SearchLibrariesByID.setOnAction(new EventHandler<ActionEvent>() {

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
				}
				else if (tf1SearchLibraries.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter valid search criteria!");
					alertFailiure.showAndWait();
				} else {
				ILibraryService service = (ILibraryService) ServiceManager.getService("library");
				try {
				inputSearchLibraryID = Integer.parseInt(tf1SearchLibraries.getText());
				} catch (NumberFormatException e) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search failiure");
					alertFailiure.setContentText("Please enter valid search criteria!");
					alertFailiure.showAndWait();
					return;
				}
				try {
					Library[] library = service.getLibraryByLID(inputSearchLibraryID);
					libraryTable.populate(library);

				} catch (RemoteException e) {

					e.printStackTrace();
				}
			}
			}
		});

		btn2CreateLibrary.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CreateLibraryController crtlbrcntrl = new CreateLibraryController(userSession);
				Window w = new Window(crtlbrcntrl);
				w.open();
			}
		});

		btn3SearchUsersByCPR.setOnAction(new EventHandler<ActionEvent>() {

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
				} else {
					User[] users = new User[1];
					users[0] = new User(0001);
					userTable.populate(users);
				}
			}
		});

		btn4CreateUser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CreateUserController crlbrcntrl = new CreateUserController(userSession);
				Window w = new Window(crlbrcntrl);
				w.open();
			}

		});

//		btn5RemoveUser.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent arg0) {
//				if (inputSearchUserCPR == 0) {
//					Alert alertFailiure = new Alert(AlertType.ERROR);
//					alertFailiure.setTitle("Error Dialog");
//					alertFailiure.setHeaderText("No selected user");
//					alertFailiure.setContentText("Please select a user first!");
//					alertFailiure.showAndWait();
//				} else {
//					Alert alertConformation = new Alert(AlertType.CONFIRMATION);
//					alertConformation.setTitle("Confirm remove");
//					alertConformation.setHeaderText("Remove selected book");
//					alertConformation.setContentText("You are about to remove a user. Continue?");
//					alertConformation.showAndWait();
//				}
//			}
//		});

		btn5SearchBooksByISBN.setOnAction(new EventHandler<ActionEvent>() {

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
				} else {
					try {
						Book book = ((IInventoryService) ServiceManager.getService("inventory")).getBookByISBN(userSession.token, userSession.lid, tf3Output);
						bookTable.populate(new Book[] {book});
					} catch(Exception e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText("Failed to get book from server.");
						alert.showAndWait();
					}
				}
			}
		});

		btn6AddBook.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CreateBookController addbkcntrl = new CreateBookController(userSession);
				Window w = new Window(addbkcntrl);
				w.open();
			}
		});

//		btn8RemoveBook.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent arg0) {
//				if (inputSearchBookISBN == 0) {
//					Alert alertFailiure = new Alert(AlertType.ERROR);
//					alertFailiure.setTitle("Error Dialog");
//					alertFailiure.setHeaderText("No selected book");
//					alertFailiure.setContentText("Please select a book first!");
//					alertFailiure.showAndWait();
//				} else {
//					Alert alertConformation = new Alert(AlertType.CONFIRMATION);
//					alertConformation.setTitle("Confirm remove");
//					alertConformation.setHeaderText("Remove selected book");
//					alertConformation.setContentText("You are about to remove a book. Continue?");
//					alertConformation.showAndWait();
//				}
//			}
//		});

		innerCurrentLibrariesSection.getChildren().addAll(tf1SearchLibraries, btn1SearchLibrariesByID);
		innerCurrentLibrariesSection.setSpacing(5);

		libraryTableScrollPane.setContent(libraryTable);
		libraryTableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		currentLibrariesSection.getChildren().addAll(lbl1CurrentLibraries, innerCurrentLibrariesSection,
				libraryTableScrollPane);
		currentLibrariesSection.setSpacing(5);

		innerLibraryOperationsSection.getChildren().addAll(btn2CreateLibrary);
		innerLibraryOperationsSection.setSpacing(5);

		libraryOperationsSection.getChildren().addAll(lbl2LibraryOperations, innerLibraryOperationsSection);
		libraryOperationsSection.setSpacing(5);

		innerCurrentUsersSection.getChildren().addAll(tf2SearchUsersByCPR, btn3SearchUsersByCPR);
		innerCurrentUsersSection.setSpacing(5);

		userTableScrollPane.setContent(userTable);
		userTableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		currentUsersSection.getChildren().addAll(lbl3Users, innerCurrentUsersSection, userTableScrollPane);
		currentUsersSection.setSpacing(5);

		innerUserOperationsSection.getChildren().addAll(btn4CreateUser);
		innerUserOperationsSection.setSpacing(5);

		userOperationsSection.getChildren().addAll(lbl4UserOperations, innerUserOperationsSection);
		userOperationsSection.setSpacing(5);

		innerCurrentBooksSection.getChildren().addAll(tf3SearchBooksByISBN, btn5SearchBooksByISBN);
		innerCurrentBooksSection.setSpacing(5);

		bookTableScrollPane.setContent(bookTable);
		bookTableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		currentBooksSection.getChildren().addAll(lbl5Books, innerCurrentBooksSection, bookTableScrollPane);
		currentBooksSection.setSpacing(5);

		innerBookOperationsSection.getChildren().addAll(btn6AddBook);
		innerBookOperationsSection.setSpacing(5);

		bookOperationsSection.getChildren().addAll(lbl6BookOperations, innerBookOperationsSection);
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

	public void populateLibraryTableOnWindowLoad() {
		ILibraryService service = (ILibraryService) ServiceManager.getService("library");
		try {
			Library[] library = service.getLibraries(0, Integer.MAX_VALUE);
			libraryTable.populate(library);
		} catch (RemoteException e) {
			Log.error(e);
		}
	}
	
	public void setSearchIndex(int i) {
		inputSearchLibraryID = i;
	}
	
	public int getSearchIndex() {
		return ++inputSearchLibraryID;
	}
	
	public void populateUserTableOnWindowLoad() {
		//TODO
	}
		
		
	public void populateBookTableOnWindowLoad() {
		//TODO
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
