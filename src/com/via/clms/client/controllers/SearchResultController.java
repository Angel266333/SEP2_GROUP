package com.via.clms.client.controllers;

import java.util.Optional;

import com.via.clms.client.controllers.containers.ClickListener;
import com.via.clms.client.controllers.containers.SearchResultTable;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.DialogWindow;
import com.via.clms.client.views.Window;
import com.via.clms.shared.Book;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchResultController implements Controller {

	/*
	 * Credits for code example: http://code.makery.ch Example used:
	 * http://code.makery.ch/blog/javafx-dialogs-official/
	 * 
	 */
	
	Window windowInstance;
	SearchResultTable searchResultTable;

	private GridPane mainPane;
	private GridPane searchPane;
	private GridPane labelBookSearchFiltersPane;
	private GridPane bookListResultsPane;
	private GridPane bookSearchResultOptionsPane;
	private GridPane footerSectionPane;
	
	private ScrollPane searchResultsTableScrollPane;
	
	private VBox searchSection;
	private HBox innerSearchSection;
	private HBox labelBookFiltersLeftSection;
	private VBox bookSearchFiltersSection;
	private VBox bookListResultsSection;
	private HBox innerFooterUserActionsSection;

	public TextField tf1Search;

	private Label lbl1Search;
	private Label lbl2BookOptions;
	private Label lbl3SearchOptions;
	private Label lbl4SearchResults;

	public ComboBox<String> cb1BookGenre;
	public ComboBox<String> cb2BookYear;
	public ComboBox<String> cb3BookLanguage;
	public ComboBox<String> cb4BookLibraryLocation;

	private Button btn1Search;
	private Button btn2UpdatePreferences;
	private Button btn3ClearSearchResults;
	private Button btn4RentSelectedBooks;
	private Button btn5ViewBookDetails;
	private Button btn6HomeSection;
	private Button btn7MyProfile;

	private UserSession userSession;
	
	public SearchResultController(UserSession userSession) {
		this.userSession = userSession;
	}

	public String getTitle() {
		return "Results from book search";
	}

	public Parent getComponent() {

		mainPane = new GridPane();
		searchPane = new GridPane();
		labelBookSearchFiltersPane = new GridPane();
		bookSearchResultOptionsPane = new GridPane();
		bookListResultsPane = new GridPane();
		searchResultsTableScrollPane = new ScrollPane();
		footerSectionPane = new GridPane();
		
		searchResultsTableScrollPane = new ScrollPane();

		tf1Search = new TextField();

		lbl1Search = new Label("Search for books:");
		lbl1Search.setPadding(new Insets(0, 0, 5, 0));
		lbl2BookOptions = new Label("Book search filters:");
		lbl2BookOptions.setPadding(new Insets(0, 0, 5, 0));
		lbl3SearchOptions = new Label("Search options:");
		lbl3SearchOptions.setPadding(new Insets(5, 0, 0, 0));
		lbl4SearchResults = new Label("Search results:");
		lbl4SearchResults.setPadding(new Insets(0, 0, 5, 40));

		searchResultTable = new SearchResultTable();

		cb1BookGenre = new ComboBox<String>();
		cb1BookGenre.setPromptText("Book genre");
		cb1BookGenre.setPrefWidth(160);
		cb1BookGenre.getItems().addAll("Action", "Thriller", "Comedy", "Horror", "Sci-Fi", "Culture", "History",
				"Fantasy", "Mythology");

		cb2BookYear = new ComboBox<String>();
		cb2BookYear.setPromptText("Book year");
		cb2BookYear.setPrefWidth(160);
		cb2BookYear.getItems().addAll("2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009",
				"2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "Before 2000");

		cb3BookLanguage = new ComboBox<String>();
		cb3BookLanguage.setPromptText("Book language");
		cb3BookLanguage.setPrefWidth(160);
		cb3BookLanguage.getItems().addAll("English", "Danish", "Bulgarian", "German", "Dutch", "French", "Russian",
				"Polish");

		cb4BookLibraryLocation = new ComboBox<String>();
		cb4BookLibraryLocation.setPromptText("Book location");
		cb4BookLibraryLocation.setPrefWidth(160);
		cb4BookLibraryLocation.getItems().addAll("Horsens", "Aarhus", "Aalborg", "Copenhagen");

		searchSection = new VBox();
		innerSearchSection = new HBox();
		labelBookFiltersLeftSection = new HBox();
		bookSearchFiltersSection = new VBox();
		bookListResultsSection = new VBox();
		innerFooterUserActionsSection = new HBox();

		
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));
		
		labelBookSearchFiltersPane.setPadding(new Insets(5, 0, 5, 0));
	
		searchResultsTableScrollPane.setPrefWidth(370);
		searchResultsTableScrollPane.setPrefHeight(240);
		
		bookSearchResultOptionsPane.setAlignment(Pos.CENTER);
		bookSearchResultOptionsPane.setPadding(new Insets(5, 0, 0, 0));
		
		footerSectionPane.setPadding(new Insets(10, 5, 0, 0));

		// \\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\

		tf1Search.setPrefColumnCount(32);

		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1Search = new Button("Search");
		btn2UpdatePreferences = new Button("Update preferences");
		btn3ClearSearchResults = new Button("Reset search");
		btn4RentSelectedBooks = new Button("Rent selected books");
		btn5ViewBookDetails = new Button("View book details");
		btn6HomeSection = new Button("Home section");
		btn7MyProfile = new Button("My Profile");
		
		btn2UpdatePreferences.setStyle("-fx-color: #7EC0EE;");
		btn3ClearSearchResults.setStyle("-fx-color: #FF9999");

		// \\/\\/\\/\\/\\-=Event Handlers=-//\\/\\/\\/\\/\\
		
		searchResultTable.setListener(new ClickListener() {
			
			@Override
			public void doubleClick(int codeStateExecution) {
				codeStateExecution = 2;
				System.out.println("Test 2");
				};
			
			@Override
			public void click(int codeStateExecution) {
				System.out.println("Test 1");
			}
		});	
	
		btn1Search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tf1Search.getText().isEmpty() == false) {
					Book[] dataElements = new Book[1];
					dataElements[0] = new Book(44, "1", 2, "535", "ddd", 42, "35", "5353");
					searchResultTable.populate(dataElements);
				} else {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Search unsuccessful");
					alertFailiure.setContentText("Please enter a book title!");
					alertFailiure.showAndWait();
				}

			}
		});

		btn2UpdatePreferences.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (filterSelectionGuard()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No search preferences selected");
					alertFailiure.setContentText("Please select search preferences to filter results!");
					alertFailiure.showAndWait();
				} else {
					System.out.println("Data needs to be fed at this point.");
				}
			}
		});

		btn3ClearSearchResults.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				searchResultTable.clear();
			}
		});

		btn4RentSelectedBooks.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tableValueGuardNoBooks()) {
					Alert promptConfirmation = new Alert(AlertType.CONFIRMATION);
					promptConfirmation.setTitle("Book rent confirm");
					promptConfirmation.setHeaderText("Rent selected books");
					promptConfirmation.setContentText("You are about to rent the following books:\n"
							+ "getUserBookSelections()" + " \n" + "Continue?");
					if (isRentPossible()) {
						Optional<ButtonType> result = promptConfirmation.showAndWait();
						if (result.get() == ButtonType.OK) {
							// Has to perform an if-statement that checks if rent is possible and get book
							// time frame.
							Alert alertSuccess = new Alert(AlertType.INFORMATION);
							alertSuccess.setTitle("Information Dialog");
							alertSuccess.setHeaderText("Selected books have been successfully rented");
							alertSuccess.setContentText("You have now rented:\n" + "getUserBookSelections()");
							alertSuccess.showAndWait();
						} else {
							return;
						}

					} else {
						Alert alertFailiure = new Alert(AlertType.ERROR);
						alertFailiure.setTitle("Error Dialog");
						alertFailiure.setHeaderText("Selected book rental error");
						alertFailiure.setContentText("Could not rent selected books!");
						alertFailiure.showAndWait();
					}
				} else {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No selected books");
					alertFailiure.setContentText("Please select a book first!");
					alertFailiure.showAndWait();
				}

			}
		});

		btn5ViewBookDetails.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (tableValueGuardMultiBooks()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Selection failiure");
					alertFailiure.setContentText(
							"Cannot display information about multiple books!\nPlease select one book!");
					alertFailiure.showAndWait();
				} else if (tableValueGuardNoBooks() == false) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("Selection failiure");
					alertFailiure.setContentText("Please select a book!");
					alertFailiure.showAndWait();
				} else {
					ViewBookDetailsController bookDetails = new ViewBookDetailsController(userSession);
					Window w = new DialogWindow(bookDetails);
					w.open();
				}
			}

		});

		btn6HomeSection.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				windowInstance.close();
			}

		});

		btn7MyProfile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				ProfileController prc = new ProfileController(0);
				Window w = new DialogWindow(prc);
				w.open();

			}

		});
		
		// \\/\\/\\/\\/\\-=ScrollPane properties=-//\\/\\/\\/\\/\\

		searchResultsTableScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);		

		// \\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\

		innerSearchSection.getChildren().addAll(tf1Search, btn1Search);
		innerSearchSection.setSpacing(5);

		searchSection.getChildren().addAll(lbl1Search, innerSearchSection);

		labelBookFiltersLeftSection.getChildren().addAll(lbl2BookOptions, lbl4SearchResults);

		searchResultsTableScrollPane.setContent(searchResultTable);
		
		bookListResultsSection.getChildren().addAll(searchResultsTableScrollPane);

		bookSearchFiltersSection.getChildren().addAll(cb1BookGenre, cb2BookYear, cb3BookLanguage,
				cb4BookLibraryLocation, btn2UpdatePreferences,lbl3SearchOptions, btn3ClearSearchResults);
		btn2UpdatePreferences.setPrefSize(cb4BookLibraryLocation.getPrefWidth(),
				cb4BookLibraryLocation.getPrefHeight());
		btn3ClearSearchResults.setPrefSize(btn2UpdatePreferences.getPrefWidth(), btn2UpdatePreferences.getPrefHeight());
		bookSearchFiltersSection.setPadding(new Insets(0, 5, 0, 0));
		bookSearchFiltersSection.setSpacing(5);

		btn4RentSelectedBooks.setPrefSize(btn3ClearSearchResults.getPrefWidth(), btn3ClearSearchResults.getPrefHeight());
		innerFooterUserActionsSection.getChildren().addAll(btn4RentSelectedBooks, btn5ViewBookDetails, btn6HomeSection,
				btn7MyProfile);
		innerFooterUserActionsSection.setSpacing(5);

		// \\/\\/\\/\\/\\-=Compact Containers To Panes=-//\\/\\/\\/\\/\\

		searchPane.add(searchSection, 0, 0);

		labelBookSearchFiltersPane.add(labelBookFiltersLeftSection, 0, 0);

		bookListResultsPane.add(bookSearchFiltersSection, 0, 0);
		bookListResultsPane.add(bookListResultsSection, 1, 0);

		footerSectionPane.add(innerFooterUserActionsSection, 0, 0);

		// \\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\

		mainPane.add(searchPane, 0, 0);
		mainPane.add(labelBookSearchFiltersPane, 0, 1);
		mainPane.add(bookListResultsPane, 0, 2);
		mainPane.add(footerSectionPane, 0, 3);

		return mainPane;
	}

	// Getters for filter selection.
	public int getCb1BookGenreFilterSelection() {
		return cb1BookGenre.getSelectionModel().getSelectedIndex();
	}

	public int getCb2BookYear() {
		return cb2BookYear.getSelectionModel().getSelectedIndex();
	}

	public int getCb3BookLanguage() {
		return cb3BookLanguage.getSelectionModel().getSelectedIndex();
	}

	public int getCb4BookLibraryLocation() {
		return cb4BookLibraryLocation.getSelectionModel().getSelectedIndex();
	}

	public boolean isRentPossible() {
		// TODO - Future implementation where the system checks if the user is eligible for rent.
		return true;
	}

	public boolean filterSelectionGuard() {
		if (getCb1BookGenreFilterSelection() == -1 && getCb2BookYear() == -1 && getCb3BookLanguage() == -1
				&& getCb4BookLibraryLocation() == -1) {
			return true;
		}
		return false;
	}

	public boolean tableValueGuardMultiBooks() {
		if (searchResultTable.getChildren().size() > 1) {
			return true;
		}
		return false;
	}

	public boolean tableValueGuardNoBooks() {
		if (searchResultTable.getChildren().size() == 0) {
			return false;
		}
		return true;
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
		
	}

	@Override
	public void onWindowResume(Window win) {

	}

	@Override
	public void onWindowPause(Window win) {

	}
}