package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ViewBookDetailsController implements Controller {

	private GridPane mainPane;
	private GridPane picturePane;
	private GridPane quickDetailsPane;
	private GridPane descriptionPane;
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
	
	Rectangle r;
	
	public ViewBookDetailsController() {

	mainPane = new GridPane();
	picturePane = new GridPane();
	quickDetailsPane = new GridPane();
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
		// TODO Auto-generated method stub
		return null;
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