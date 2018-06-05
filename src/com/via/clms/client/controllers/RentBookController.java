package com.via.clms.client.controllers;

import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.ResultListener;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public class RentBookController implements Controller {
	UserSession session;
	User currentUser = null;
	TextArea outputArea;
	UserSession loanerSession = null;
	LoginController loanerLogin;

	public RentBookController(UserSession session) {
		this.session = session;
		loanerLogin = new LoginController(session.lid);
		loanerLogin.setResultListener(new ResultListener<UserSession>() {
			@Override
			public void onReturnResult(UserSession result) {
				loanerSession = result;

				try {
					currentUser = ((IUserService) ServiceManager.getService("user")).getUserByCPR(session.token, loanerSession.cpr);
				} catch(RemoteException e) {
					showAlert("Login Failed");
				}
			}
		});
	}

	@Override
	public String getTitle() {
		return "Rent Book";
	}

	@Override
	public Parent getComponent() {
		Window lw = new Window(loanerLogin);
		lw.open();

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
			int i = service.addRental(loanerSession.token, session.lid, book.bid, currentUser.uid);
			if(i < 1) {
				outputArea.appendText("Book could not be rented.\nContact staff\n");
				return;
			}
			outputArea.appendText("You rented: \n" + book.title + "\nAuthor: " + book.author + "\nISBN: " + book.ISBN + "\nReturn before: " + getReturnDate() + "\n");
		} catch(RemoteException e) {
			outputArea.appendText("Connection error.\n");
		}
	}

	private String getReturnDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		LocalDate ld = LocalDate.of(year, month + 1, day).plusDays(30);
		return "" + ld.getDayOfMonth() + ". " + ld.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + ld.getYear();
	}

	private void showAlert(String s) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(s);
		alert.showAndWait();
	}
}
