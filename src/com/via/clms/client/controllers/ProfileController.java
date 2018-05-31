package com.via.clms.client.controllers;

import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.RentalsTable;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import com.via.clms.proxy.IInventoryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.shared.Book;
import com.via.clms.shared.BookRental;
import com.via.clms.shared.User;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.rmi.RemoteException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProfileController implements Controller {
	private GridPane mainPane;
	private ScrollPane rentalPane;
	private RentalsTable rentalsTable;
	Button emailUpdateButton;
	Button changePasswordButton;

	UserSession session;
	User currentUser;

	public ProfileController(UserSession session) {
		this.session = session;
		try {
			currentUser = ((IUserService) ServiceManager.getService("user")).getUserByCPR(session.token, session.cpr);
		} catch (RemoteException e) {
			currentUser = null;
			e.printStackTrace();
		}
	}

	@Override
	public String getTitle() {
		return "User profile";
	}

	@Override
	public Parent getComponent() {
		mainPane = new GridPane();
		if(currentUser == null) {
			mainPane.addRow(0, new Label("Error loading profile"));
			return mainPane;
		}
		rentalPane = new ScrollPane();
		rentalsTable = new RentalsTable();
		rentalPane.setContent(rentalsTable);
		rentalsTable.populate(getRentals());

		//Labels
		String labelStyle = "-fx-font-weight: bold";
		Label nameLabel = new Label("Name");
		nameLabel.setStyle(labelStyle);
		Label nameTextLabel = new Label(currentUser.name);
		Label cprLabel = new Label("CPR");
		cprLabel.setStyle(labelStyle);
		Label cprTextLabel = new Label("" + session.cpr);
		Label emailLabel = new Label("E-mail");
		emailLabel.setStyle(labelStyle);

		//TextFields
		TextField emailTextField = new TextField();
		emailTextField.setPrefColumnCount(20);
		emailTextField.setText(currentUser.email);

		//Buttons
		emailUpdateButton = new Button("Update");
		changePasswordButton = new Button("Change password");

		mainPane.add(nameLabel, 0, 0);
		mainPane.add(cprLabel, 1, 0);
		mainPane.add(nameTextLabel, 0, 1);
		mainPane.add(cprTextLabel, 1, 1);
		mainPane.add(emailLabel, 0, 2);
		mainPane.add(emailTextField, 0, 3);
		mainPane.add(emailUpdateButton, 1, 3);
		mainPane.add(rentalPane, 0, 4, 2, 1);
		mainPane.add(changePasswordButton, 0, 5);

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

	private String[][] getRentals() {
		try {
			ArrayList<String[]> resultArray = new ArrayList<>();
			BookRental[] r = ((IInventoryService) ServiceManager.getService("inventory")).getRentalsByUID(session.token, session.lid, currentUser.uid);

			for(BookRental b : r) {
				resultArray.add(formatRental(b));
			}

			String[][] strings = new String[resultArray.size()][];
			resultArray.toArray(strings);
			return strings;
		} catch(Exception e) {
			e.printStackTrace();
			return new String[0][];
		}
	}

	private String[] formatRental(BookRental b) throws RemoteException {
		String[] s = new String[3];
		s[0] = ((IInventoryService) ServiceManager.getService("inventory")).getBookByBID(session.token, b.bid).title;
		s[1] = formatDate(b.timeoffset + b.timelength);
		if(Instant.now().getEpochSecond() > b.timeoffset + b.timelength) {
			s[2] = "Yes";
		}
		else {
			s[2] = "No";
		}
		return s;
	}

	private String formatDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Date.from(Instant.ofEpochSecond(time)));
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT_FORMAT, Locale.ENGLISH);
		int year = cal.get(Calendar.YEAR);
		return "" + day + "-" + month + "-" + year;
	}
}
