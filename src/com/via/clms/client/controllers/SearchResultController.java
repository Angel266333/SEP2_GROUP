package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchResultController implements Controller {	
	
	/*
	 * Credits for code example: Oracle Corporation.
	 * Example used:
	 * https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
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
	private HBox labelSearchResultsRightSection;
	private VBox bookSearchFiltersSection;
	private VBox bookListResultsSection;
	private HBox innerFooterUserActionsSection;

	private TextField tf1Search;
	
	private Label lbl1Search;
	private Label lbl2BookOptions;
	private Label lbl3SearchResults;

	// For future reference, this element can implement ArrayLists.
	public ComboBox cb1BookGenre;
	public ComboBox cb2BookYear;
	public ComboBox cb3BookLanguage;
	public ComboBox cb4BookLibraryLocation;
	
	public String bookName;
	public String bookAuthor;
	public String bookYear;
	public String bookAvailability;
	
	
	private TableView<SearchResultData> tbView1BookResults;
	private final ObservableList<SearchResultData> data = FXCollections.observableArrayList(
	new SearchResultData("The Ugly Duckling", "Hans Christian Andersen", "1843", "3"),
	new SearchResultData("A Daughter of Thought", "Maryana Marrash", "1893", "2"),
	new SearchResultData("The Iron Candlestick", "Dimitar Talev", "1952", "1"));
	
    private TableColumn<SearchResultData, String> bookNameCol1;
    private TableColumn<SearchResultData, String> bookAuthorNameCol2;
    private TableColumn<SearchResultData, String> bookYearCol3;
    private TableColumn<SearchResultData, String> bookAvailabilityCol4;
    
	private Button btn1Search;
	private Button btn2RentSelectedBooks;
	private Button btn3ViewBookDetails;
	private Button btn4MyProfile;
	private Button btn5HomeSection;	
	

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
		lbl3SearchResults = new Label("Search results:");
		lbl3SearchResults.setPadding(new Insets(0, 0, 5, 40));
		
		// Placeholders. Actual Strings need to be retrieved from
		// database where user has, for example, specified that
		// there are 2 libraries with location "Vejle", "Malmo" etc.
		
		cb1BookGenre = new ComboBox();
		cb1BookGenre.setPromptText("Book genre");
		cb1BookGenre.setPrefWidth(135);
	     cb1BookGenre.getItems().addAll(
	     "Action", "Thriller", "Comedy",
	     "Horror", "Sci-Fi", "Culture",
	     "Histoty", "Fantasy", "Mythology"
	     );
	     
	     cb2BookYear = new ComboBox();
	     cb2BookYear.setPromptText("Book year");
	     cb2BookYear.setPrefWidth(135);
	     cb2BookYear.getItems().addAll(
	    "2018", "2017", "2016", "2015", "2014",
	    "2013", "2012", "2011", "2010", "2009",
	    "2008", "2007", "2006", "2005", "2004",
	    "2003", "2002", "2001", "2000", "Before 2000"
	    );
	     
	     cb3BookLanguage = new ComboBox();
	     cb3BookLanguage.setPromptText("Book language");
	     cb3BookLanguage.setPrefWidth(135);
	     cb3BookLanguage.getItems().addAll(
	    "English", "Danish", "Bulgarian","German",
	    "Dutch", "French", "Russian", "Polish");
	     
	     cb4BookLibraryLocation = new ComboBox();
	     cb4BookLibraryLocation.setPromptText("Book location");
	     cb4BookLibraryLocation.setPrefWidth(135);
	     cb4BookLibraryLocation.getItems().addAll(
	    "Horsens", "Aarhus", "Aalborg", "Copenhagen"
	    );
		
		tbView1BookResults = new TableView<SearchResultData>();
		
		searchSection = new VBox();
		innerSearchSection = new HBox();
		labelBookFiltersLeftSection = new HBox();
		labelSearchResultsRightSection = new HBox();
		bookSearchFiltersSection = new VBox();
		bookListResultsSection = new VBox();
		innerFooterUserActionsSection = new HBox();
		
	}
	
	
	
	public void receiveSearchResults() {
		
		bookName = dataOutput.getBookName();
		bookAuthor = dataOutput.getBookAuthor();
		bookYear = dataOutput.getBookYear();
		bookAvailability = dataOutput.getBookAvailability();
		
	}
	
	
	public String getTitle() {
		return "Results from book search";
	}

	public Parent getComponent() {
		
		//\\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\
		
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
						
		//\\/\\/\\/\\/\\-=TextFields=-//\\/\\/\\/\\/\\
		
		tf1Search.setPrefColumnCount(34);
		
		//\\/\\/\\/\\/\\-=Table Column Properties=-//\\/\\/\\/\\/\\

		bookNameCol1 = new TableColumn("Name");
		bookNameCol1.setPrefWidth(110);
		bookNameCol1.setCellValueFactory(new PropertyValueFactory<SearchResultData, String>("bookName"));
		
		
		bookAuthorNameCol2 = new TableColumn("Author");
		bookAuthorNameCol2.setPrefWidth(110);
		bookAuthorNameCol2.setCellValueFactory(new PropertyValueFactory<SearchResultData, String>("bookAuthor"));
		
		bookYearCol3 = new TableColumn("Year");
		bookYearCol3.setPrefWidth(40);
		bookYearCol3.setStyle("-fx-alignment: CENTER;");
		bookYearCol3.setCellValueFactory(new PropertyValueFactory<SearchResultData, String>("bookYear"));
		
		bookAvailabilityCol4 = new TableColumn("Available");
		bookAvailabilityCol4.setPrefWidth(65);
		bookAvailabilityCol4.setStyle("-fx-alignment: CENTER;");
		bookAvailabilityCol4.setCellValueFactory(new PropertyValueFactory<SearchResultData, String>("bookAvailability"));
		
		
		tbView1BookResults.setItems(data);
		tbView1BookResults.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tbView1BookResults.getColumns().addAll(bookNameCol1, bookAuthorNameCol2, bookYearCol3, bookAvailabilityCol4);
		
		//\\/\\/\\/\\/\\-=Buttons=-//\\/\\/\\/\\/\\
		
		btn1Search = new Button("Search");
		btn2RentSelectedBooks = new Button("Rent selected books");
		btn3ViewBookDetails = new Button("View book details");
		btn4MyProfile = new Button("My Profile");
		btn5HomeSection = new Button("Home secton");
		
		//\\/\\/\\/\\/\\-=Objects To Box Containers=-//\\/\\/\\/\\/\\

		innerSearchSection.getChildren().addAll(tf1Search, btn1Search);
		innerSearchSection.setSpacing(5);
		
		searchSection.getChildren().addAll(lbl1Search, innerSearchSection);		
		
		labelBookFiltersLeftSection.getChildren().add(lbl2BookOptions);
		labelSearchResultsRightSection.getChildren().add(lbl3SearchResults);
				
		bookListResultsSection.getChildren().addAll(tbView1BookResults);

		bookSearchFiltersSection.getChildren().addAll(cb1BookGenre, cb2BookYear, cb3BookLanguage, cb4BookLibraryLocation);
		bookSearchFiltersSection.setPadding(new Insets(0, 5, 0, 0));
		bookSearchFiltersSection.setSpacing(5);

		innerFooterUserActionsSection.getChildren().addAll(btn2RentSelectedBooks, btn3ViewBookDetails, btn5HomeSection, btn4MyProfile);
		innerFooterUserActionsSection.setSpacing(5);
		
		//\\/\\/\\/\\/\\-=Compact Containers To Panes=-//\\/\\/\\/\\/\\

		searchPane.add(searchSection, 0, 0);
		
		labelBookSearchFiltersPane.add(labelBookFiltersLeftSection, 0, 0);
		labelBookSearchFiltersPane.add(labelSearchResultsRightSection, 1, 0);

		bookListResultsPane.add(bookSearchFiltersSection, 0, 0);
		bookListResultsPane.add(bookListResultsSection, 1, 0);

		footerSectionPane.add(innerFooterUserActionsSection, 0, 0);

		//\\/\\/\\/\\/\\-=Paneception=-//\\/\\/\\/\\/\\
		
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
		
	}

	@Override
	public void onWindowClose(Window win) {
		
	}

	@Override
	public void onWindowRefresh(Window win) {
		
	}
}