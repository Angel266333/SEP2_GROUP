package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ViewBookDetailsController implements Controller {

	private GridPane mainPane;
	private GridPane picturePanePlusDescription;
	private GridPane descriptionPane;
	private GridPane footerSectionPane;
	private VBox pictureSection;
	private VBox quickDetailsSection;
	private VBox descriptionSection;
	
	private Label lbl1Preview;
	private Label lbl2Description;
	private Label lbl3BookDetails;
	
	public String bookName;
	public String bookAuthor;
	public String bookYear;
	public String bookAvailability;
	
	private Button btn1HomeSection;
	
	Rectangle r;
	
	public ViewBookDetailsController() {

	mainPane = new GridPane();
	picturePanePlusDescription = new GridPane();
	descriptionPane = new GridPane();
		
	r.setX(50);
	r.setY(50);
	r.setWidth(200);
	r.setHeight(100);
	r.setArcWidth(20);
	r.setArcHeight(20);
	
	}
	
	@Override
	public String getTitle() {
		return "Book details";
	}

	@Override
	public Parent getComponent() {
		
		//\\/\\/\\/\\/\\-=Pane Alignment=-//\\/\\/\\/\\/\\

		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20, 5, 20, 5));
		
		picturePanePlusDescription.setAlignment(Pos.CENTER_LEFT);
		
		descriptionPane.setAlignment(Pos.CENTER_RIGHT);
		
		footerSectionPane.setAlignment(Pos.BOTTOM_CENTER);
		
		picturePanePlusDescription.add(lbl1Preview, 0, 0);
		picturePanePlusDescription.add(r, 0, 1);
		picturePanePlusDescription.add(quickDetailsSection, 0, 2);
		
		mainPane.add(picturePanePlusDescription, 0, 1);
		
		return mainPane;
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