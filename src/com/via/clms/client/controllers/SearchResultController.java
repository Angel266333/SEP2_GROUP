package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchResultController implements Controller {
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
	private Label lbl3SortResultsCriterias;
	private Label lbl3SearchResults;

	// For future reference, this element can implement ArrayLists.
	private ComboBox cb1BookGenre = new ComboBox();
	private ComboBox cb2BookYearFilter = new ComboBox();
	private ComboBox cb3BookLanguage = new ComboBox();
	private ComboBox cb4BookLibraryLocation = new ComboBox();
	
	// For future reference, this element can implement ArrayLists.
	private TableView tbView1BookResults;
	
	
    TableColumn bookNameCol1;
    TableColumn bookAuthorNameCol2;
    TableColumn bookdateOfIssueCol3;
    TableColumn bookAvailabilityCol4;
    
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
		lbl3SearchResults.setPadding(new Insets(0, 0, 5, 27));
		
		// Placeholders. Actual Strings need to be retrieved from
		// database where user has, for example, specified that
		// there are 2 libraries with location "Vejle", "Malmo" etc.
		
		cb1BookGenre.setPromptText("Select genre");
	     cb1BookGenre.getItems().addAll(
	     "Action", "Thriller", "Comedy",
	     "Horror", "Sci-Fi", "Culture",
	     "Histoty", "Fantasy", "Mythology"
	     );
	     
	     cb2BookYearFilter.setPromptText("Select year");
	     cb2BookYearFilter.getItems().addAll(
	    "2018", "2017", "2016", "2015", "2014",
	    "2013", "2012", "2011", "2010", "2009",
	    "2008", "2007", "2006", "2005", "2004",
	    "2003", "2002", "2001", "2000", "Before 2000"
	    );
	     
	     cb3BookLanguage.setPromptText("Book language");
	     cb3BookLanguage.getItems().addAll(
	    "English", "Danish", "Bulgarian","German",
	    "Dutch", "French", "Russian", "Polish");
	     
	     cb4BookLibraryLocation.setPromptText("Book location");
	     cb4BookLibraryLocation.getItems().addAll(
	    "Horsens", "Aarhus", "Aalborg", "Copenhagen"
	    );
		
		tbView1BookResults = new TableView();
		
		searchSection = new VBox();
		innerSearchSection = new HBox();
		labelBookFiltersLeftSection = new HBox();
		labelSearchResultsRightSection = new HBox();
		bookSearchFiltersSection = new VBox();
		bookListResultsSection = new VBox();
		innerFooterUserActionsSection = new HBox();
		
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
		
		//\\/\\/\\/\\/\\-=Table Properties=-//\\/\\/\\/\\/\\

		bookNameCol1 = new TableColumn("Name");
		bookAuthorNameCol2 = new TableColumn("Author");
		bookdateOfIssueCol3 = new TableColumn("Date of issue");
		bookAvailabilityCol4 = new TableColumn("Availability");
		
		tbView1BookResults.getColumns().addAll(bookNameCol1, bookAuthorNameCol2, bookdateOfIssueCol3, bookAvailabilityCol4);
		
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

		bookSearchFiltersSection.getChildren().addAll(cb1BookGenre, cb2BookYearFilter, cb3BookLanguage, cb4BookLibraryLocation);
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