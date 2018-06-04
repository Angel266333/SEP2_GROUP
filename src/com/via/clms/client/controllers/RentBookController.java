package com.via.clms.client.controllers;

import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import com.via.clms.proxy.IInventoryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.shared.Book;
import com.via.clms.shared.User;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.rmi.RemoteException;
import java.sql.Date;
import java.time.Instant;
import java.util.Calendar;
import java.util.Locale;

public class RentBookController implements Controller {
	UserSession session;
	User currentUser;
	TextArea outputArea;

	public RentBookController(UserSession session) {
		this.session = session;
	}

	@Override
	public String getTitle() {
		return "Rent Book";
	}

	@Override
	public Parent getComponent() {
		try {
			currentUser = ((IUserService) ServiceManager.getService("user")).getUserByCPR(session.token, session.cpr);
		} catch(RemoteException e) {
			return new Label("Failed to load user");
		}
		GridPane mainPane = new GridPane();

		TextField isbnField = new TextField();
		isbnField.setPrefColumnCount(20);

		Button rentButton = new Button("Rent");

		outputArea = new TextArea();
		outputArea.setPrefRowCount(10);
		outputArea.setPrefColumnCount(20);
		outputArea.setEditable(false);

		mainPane.addRow(0, new Label("ISBN"), isbnField, rentButton);
		mainPane.add(new Separator(Orientation.HORIZONTAL), 0, 1, 3, 1);
		mainPane.add(outputArea, 0, 2, 3, 1);

		rentButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				rentBook(isbnField.getText());
			}
		});

		return mainPane;
	}

	@Override
	public void onWindowOpen(Window window) {

	}

	@Override
	public void onWindowClose(Window window) {

	}

	@Override
	public void onWindowRefresh(Window window) {

	}

	@Override
	public void onWindowResume(Window win) {
		
	}

	@Override
	public void onWindowPause(Window win) {
		
	}

	private void rentBook(String isbn) {
		try {
			IInventoryService service = (IInventoryService) ServiceManager.getService("inventory");
			Book book = service.getBookByISBN(session.token, session.lid, isbn);
			if(book == null) {
				outputArea.appendText("Failed to find book\n");
				return;
			}
			int i = service.addRental(session.token, session.lid, book.bid, currentUser.uid);
			if(i < 1) {
				outputArea.appendText("Book could not be rented.\nContact staff");
				return;
			}
			outputArea.appendText("You rented: \n" + book.title + "\nAuthor: " + book.author + "\nISBN: " + book.ISBN + "\nReturn before: " + getReturnDate());
		} catch(RemoteException e) {
			outputArea.appendText("Connection error.\n");
		}
	}

	private String getReturnDate() {
		Calendar cal = Calendar.getInstance();
		long now = Instant.now().getEpochSecond();
		cal.setTime(Date.from(Instant.ofEpochSecond(now + 2678400L)));//30 days rental counting the day of rental
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.ENGLISH);
		int year = cal.get(Calendar.YEAR);
		return "" + day + "-" + "-" + month + "-" + year;
	}
}
