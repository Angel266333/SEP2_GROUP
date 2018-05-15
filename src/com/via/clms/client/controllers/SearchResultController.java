package com.via.clms.client.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.sun.glass.events.MouseEvent;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.DialogWindow;
import com.via.clms.client.views.Window;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchResultController implements Controller {

	/*
	 * Credits for code example: Oracle Corporation Example used:
	 * https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
	 * 
	 */

	/*
	 * Credits for code example: http://code.makery.ch Example used:
	 * http://code.makery.ch/blog/javafx-dialogs-official/
	 * 
	 */

	private SearchResultData dataOutput;

	private GridPane mainPane;
	private GridPane searchPane;
	private GridPane labelBookSearchFiltersPane;
	private GridPane bookListResultsPane;
	private GridPane bookSearchResultOptionsPane;
	private GridPane footerSectionPane;
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

	public String bookName;
	public String bookAuthor;
	public String bookYear;
	public String bookAvailability;

	Window windowInstance;

	private TableView<SearchResultData> tbView1BookResults;
	private final ObservableList<SearchResultData> tableData = updateTableBookResultsUI();

	private TableColumn<SearchResultData, String> bookNameCol1;
	private TableColumn<SearchResultData, String> bookAuthorNameCol2;
	private TableColumn<SearchResultData, String> bookYearCol3;
	private TableColumn<SearchResultData, String> bookAvailabilityCol4;

	private Button btn1Search;
	private Button btn2UpdatePreferences;
	private Button btn3ClearSearchResults;
	private Button btn4RentSelectedBooks;
	private Button btn5ViewBookDetails;
	private Button btn6HomeSection;
	private Button btn7MyProfile;

	public SearchResultController() {

		mainPane = new GridPane();
		searchPane = new GridPane();
		labelBookSearchFiltersPane = new GridPane();
		bookSearchResultOptionsPane = new GridPane();
		bookListResultsPane = new GridPane();
		footerSectionPane = new GridPane();

		tf1Search = new TextField();

		lbl1Search = new Label("Search for books:");
		lbl1Search.setPadding(new Insets(0, 0, 5, 0));
		lbl2BookOptions = new Label("Book search filters:");
		lbl2BookOptions.setPadding(new Insets(0, 0, 5, 0));
		lbl3SearchOptions = new Label("Search options:");
		lbl3SearchOptions.setPadding(new Insets(5, 0, 0, 0));
		lbl4SearchResults = new Label("Search results:");
		lbl4SearchResults.setPadding(new Insets(0, 0, 5, 40));

		// Placeholders. Actual Strings need to be retrieved from
		// database where user has, for example, specified that
		// there are 2 libraries with location "Vejle", "Malmo" etc.

		cb1BookGenre = new ComboBox<String>();
		cb1BookGenre.setPromptText("Book genre");
		cb1BookGenre.setPrefWidth(135);
		cb1BookGenre.getItems().addAll("Action", "Thriller", "Comedy", "Horror", "Sci-Fi", "Culture", "History",
				"Fantasy", "Mythology");

		cb2BookYear = new ComboBox<String>();
		cb2BookYear.setPromptText("Book year");
		cb2BookYear.setPrefWidth(135);
		cb2BookYear.getItems().addAll("2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009",
				"2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "Before 2000");

		cb3BookLanguage = new ComboBox<String>();
		cb3BookLanguage.setPromptText("Book language");
		cb3BookLanguage.setPrefWidth(135);
		cb3BookLanguage.getItems().addAll("English", "Danish", "Bulgarian", "German", "Dutch", "French", "Russian",
				"Polish");

		cb4BookLibraryLocation = new ComboBox<String>();
		cb4BookLibraryLocation.setPromptText("Book location");
		cb4BookLibraryLocation.setPrefWidth(135);
		cb4BookLibraryLocation.getItems().addAll("Horsens", "Aarhus", "Aalborg", "Copenhagen");

		tbView1BookResults = new TableView<SearchResultData>();

		searchSection = new VBox();
		innerSearchSection = new HBox();
		labelBookFiltersLeftSection = new HBox();
		bookSearchFiltersSection = new VBox();
		bookListResultsSection = new VBox();
		innerFooterUserActionsSection = new HBox();

	}

	public String getTitle() {
		return "Results from book search";
	}

	public Parent getComponent() {

		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));

		searchPane.setAlignment(Pos.TOP_CENTER);
		searchPane.setPadding(new Insets(0, 0, 5, 0));

		labelBookSearchFiltersPane.setAlignment(Pos.TOP_LEFT);
		labelBookSearchFiltersPane.setPadding(new Insets(5, 0, 0, 0));

		bookListResultsPane.setAlignment(Pos.CENTER);
		bookListResultsPane.setPadding(new Insets(0, 5, 0, 0));

		bookSearchResultOptionsPane.setAlignment(Pos.TOP_CENTER);
		bookSearchResultOptionsPane.setPadding(new Insets(5, 0, 0, 0));

		footerSectionPane.setAlignment(Pos.BOTTOM_CENTER);
		footerSectionPane.setPadding(new Insets(10, 5, 0, 0));

		// \\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\

		tf1Search.setPrefColumnCount(34);

		// \\/\\/\\/\\/\\-=Table Column Properties=-//\\/\\/\\/\\/\\

		bookNameCol1 = new TableColumn<SearchResultData, String>("Name");
		bookNameCol1.setPrefWidth(110);
		bookNameCol1.setCellValueFactory(new PropertyValueFactory<SearchResultData, String>("bookName"));

		bookAuthorNameCol2 = new TableColumn<SearchResultData, String>("Author");
		bookAuthorNameCol2.setPrefWidth(108);
		bookAuthorNameCol2.setCellValueFactory(new PropertyValueFactory<SearchResultData, String>("bookAuthor"));

		bookYearCol3 = new TableColumn<SearchResultData, String>("Year");
		bookYearCol3.setPrefWidth(40);
		bookYearCol3.setStyle("-fx-alignment: CENTER;");
		bookYearCol3.setCellValueFactory(new PropertyValueFactory<SearchResultData, String>("bookYear"));

		bookAvailabilityCol4 = new TableColumn<SearchResultData, String>("Available");
		bookAvailabilityCol4.setPrefWidth(65);
		bookAvailabilityCol4.setStyle("-fx-alignment: CENTER;");
		bookAvailabilityCol4
				.setCellValueFactory(new PropertyValueFactory<SearchResultData, String>("bookAvailability"));

		tbView1BookResults.setItems(tableData);
		tbView1BookResults.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tbView1BookResults.getColumns().addAll(bookNameCol1, bookAuthorNameCol2, bookYearCol3, bookAvailabilityCol4);

		// \\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\

		btn1Search = new Button("Search");
		btn2UpdatePreferences = new Button("Update preferences");
		btn3ClearSearchResults = new Button("Reset search");
		btn4RentSelectedBooks = new Button("Rent selected books");
		btn5ViewBookDetails = new Button("View book details");
		btn6HomeSection = new Button("Home section");
		btn7MyProfile = new Button("My Profile");
		// \\/\\/\\/\\/\\-=Event Handlers=-//\\/\\/\\/\\/\\

		tbView1BookResults.setRowFactory(tv -> {
			TableRow<SearchResultData> row = new TableRow<>();
			row.setOnMouseClicked(e -> {
				if (e.getClickCount() == 2 && (!row.isEmpty()) && tableValueGuardMultiBooks() == false) {
					System.out.println(tbView1BookResults.getSelectionModel().getSelectedItem().bookName);
					ViewBookDetailsController bookDetails = new ViewBookDetailsController();
					Window w = new DialogWindow(bookDetails);
					w.open();
				}
			});
			return row;
		});

		btn1Search.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (tf1Search.getText().isEmpty() == false) {
					updateTableBookResultsUI();
					// Test data insertion:
					tableData.add(new SearchResultData("A book search result", "Angel Petrov", "2018", "25"));
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
					tableData.add(new SearchResultData("A book update result", "Angel Petrov", "2018", "25"));
					System.out.println("Data needs to be fed at this point.");
				}
			}
		});

		btn3ClearSearchResults.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				for (int i = 0; i < tbView1BookResults.getItems().size(); i++) {
					tbView1BookResults.getItems().clear();

				}
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
							+ getUserBookSelections() + " \n" + "Continue?");
					if (isRentPossible()) {
						Optional<ButtonType> result = promptConfirmation.showAndWait();
						if (result.get() == ButtonType.OK) {
							// Has to perform an if-statement that checks if rent is possible and get book
							// time frame.
							Alert alertSuccess = new Alert(AlertType.INFORMATION);
							alertSuccess.setTitle("Information Dialog");
							alertSuccess.setHeaderText("Selected books have been successfully rented");
							alertSuccess.setContentText("You have now rented:\n" + getUserBookSelections());
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
					ViewBookDetailsController bookDetails = new ViewBookDetailsController();
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

		// \\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\

		innerSearchSection.getChildren().addAll(tf1Search, btn1Search);
		innerSearchSection.setSpacing(5);

		searchSection.getChildren().addAll(lbl1Search, innerSearchSection);

		labelBookFiltersLeftSection.getChildren().addAll(lbl2BookOptions, lbl4SearchResults);

		bookListResultsSection.getChildren().addAll(tbView1BookResults);

		bookSearchFiltersSection.getChildren().addAll(cb1BookGenre, cb2BookYear, cb3BookLanguage,
				cb4BookLibraryLocation, btn2UpdatePreferences,lbl3SearchOptions, btn3ClearSearchResults);
		btn2UpdatePreferences.setPrefSize(cb4BookLibraryLocation.getPrefWidth(),
				cb4BookLibraryLocation.getPrefHeight());
		btn2UpdatePreferences.setStyle("-fx-color: #7EC0EE;");
		btn3ClearSearchResults.setPrefSize(btn2UpdatePreferences.getPrefWidth(), btn2UpdatePreferences.getPrefHeight());
		btn3ClearSearchResults.setStyle("-fx-color: #FF9999");
		bookSearchFiltersSection.setPadding(new Insets(0, 5, 0, 0));
		bookSearchFiltersSection.setSpacing(5);

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
		// Method has to return a result based on user properties and book properties.
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
		ObservableList<SearchResultData> multiCells = tbView1BookResults.getSelectionModel().getSelectedItems();
		if (multiCells.size() > 1) {
			return true;
		}
		return false;
	}

	public boolean tableValueGuardNoBooks() {
		ObservableList<SearchResultData> noCells = tbView1BookResults.getSelectionModel().getSelectedItems();
		if (noCells.size() == 0) {
			return false;
		}
		return true;
	}

	public String getUserBookSelections() {
		ObservableList<SearchResultData> selectionCells = tbView1BookResults.getSelectionModel().getSelectedItems();
		ArrayList<String> selectionCellsBookName = new ArrayList<String>();
		for (int i = 0; i < selectionCells.size(); i++) {
			selectionCellsBookName.add(selectionCells.get(i).getBookName());
		}
		String receiveValues = selectionCellsBookName.toString();
		String removeInvalidStringBracketLeft = receiveValues.replace("[", "\n ");
		String seperateItemsToNewLines = removeInvalidStringBracketLeft.replace(",", ";\n");
		String removeInvalidStringBracketRight = seperateItemsToNewLines.replace("]", "\n");
		String finalOutput = removeInvalidStringBracketRight;
		return finalOutput;
	}

	public ObservableList<SearchResultData> updateTableBookResultsUI() {
		return FXCollections.observableArrayList(
				new SearchResultData("The Ugly Duckling", "Hans Christian Andersen", "1843", "3"),
				new SearchResultData("A Daughter of Thought", "Maryana Marrash", "1893", "2"),
				new SearchResultData("The Iron Candlestick", "Dimitar Talev", "1952", "1"));
	}

	public void populateTable(String dataFeed) {
		// Get results from database and display them to table.
	}

	public class SearchResultData {

		private final SimpleStringProperty bookName;
		private final SimpleStringProperty bookAuthor;
		private final SimpleStringProperty bookYear;
		private final SimpleStringProperty bookAvailability;

		public SearchResultData(String bookName, String bookAuthor, String bookYear, String bookAvailability) {
			this.bookName = new SimpleStringProperty(bookName);
			this.bookAuthor = new SimpleStringProperty(bookAuthor);
			this.bookYear = new SimpleStringProperty(bookYear);
			this.bookAvailability = new SimpleStringProperty(bookAvailability);
		}

		public String getBookName() {
			return bookName.get();
		}

		public void setBookName(String bkName) {
			bookName.set(bkName);
		}

		public String getBookAuthor() {
			return bookAuthor.get();
		}

		public void setBookAuthor(String bkAuthor) {
			bookAuthor.set(bkAuthor);
		}

		public String getBookYear() {
			return bookYear.get();
		}

		public void setBookYear(int bkYear) {
			String bookYearHolder = Integer.toString(bkYear);
			bookYear.set(bookYearHolder);
		}

		public String getBookAvailability() {
			return bookAvailability.get();
		}

		public void setBookAvailability(int bkAvailability) {
			String bookAvailabilityHolder = Integer.toString(bkAvailability);
			bookAvailability.set(bookAvailabilityHolder);
		}
	}

	@Override
	public void onWindowOpen(Window win) {
		windowInstance = win;
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