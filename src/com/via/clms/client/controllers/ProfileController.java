package com.via.clms.client.controllers;

import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.ClickListener;
import com.via.clms.client.controllers.containers.RentalsTable;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.ResultListener;
import com.via.clms.client.views.Window;

import com.via.clms.proxy.IInventoryService;
import com.via.clms.proxy.IUserService;
import com.via.clms.server.services.Service;
import com.via.clms.shared.Book;
import com.via.clms.shared.BookRental;
import com.via.clms.shared.User;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.rmi.RemoteException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProfileController implements Controller {
	private Window window = null;
	private GridPane mainPane;
	private ScrollPane rentalPane;
	private RentalsTable rentalsTable;
	private Button emailUpdateButton;
	private Button changePasswordButton;
	private TextField emailTextField;
	private Label nameTextLabel;
	private Label cprTextLabel;

	private UserSession session;
	private User currentUser;
	private UserSession loanerSession;
	private LoanerLoginController loanerLogin;
	private ArrayList<Book> loadedBooks = new ArrayList<>();

	public ProfileController(UserSession session) {
		this.session = session;
		loanerLogin = new LoanerLoginController(session);
		loanerLogin.setResultListener(new ResultListener<UserSession>() {
			@Override
			public void onReturnResult(UserSession result) {
				if(result == null) {
					return;
				}
				try {
					currentUser = ((IUserService) ServiceManager.getService("user")).getUserByCPR(session.token, result.cpr);
					loanerSession = result;
					loadProfile();
				} catch(RemoteException e) {
					alertError("Login failed.");
				}
			}
		});
	}

	@Override
	public String getTitle() {
		return "User profile";
	}

	@Override
	public Parent getComponent() {
		Window window = new Window(loanerLogin);
		window.open();

		mainPane = new GridPane();
		rentalPane = new ScrollPane();
		rentalsTable = new RentalsTable();
		rentalsTable.setListener(new ClickListener() {
			@Override
			public void click(int i) {

			}

			@Override
			public void doubleClick(int i) {
				window.launchController(new BookOverviewController(loadedBooks.get(i)));
			}
		});
		rentalPane.setContent(rentalsTable);

		//Labels
		String labelStyle = "-fx-font-weight: bold";
		Label nameLabel = new Label("Name");
		nameLabel.setStyle(labelStyle);
		nameTextLabel = new Label();
		Label cprLabel = new Label("CPR");
		cprLabel.setStyle(labelStyle);
		cprTextLabel = new Label();
		Label emailLabel = new Label("E-mail");
		emailLabel.setStyle(labelStyle);

		//TextFields
		emailTextField = new TextField();
		emailTextField.setPrefColumnCount(20);
		TextField currentPassTextField = new PasswordField();
		currentPassTextField.setPrefColumnCount(20);
		TextField newPassTextField = new PasswordField();
		newPassTextField.setPrefColumnCount(20);
		TextField newPassRepeatTextField = new PasswordField();
		newPassRepeatTextField.setPrefColumnCount(20);

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
		mainPane.addRow(5, new Label("Current password"), currentPassTextField);
		mainPane.addRow(6, new Label("New password"), newPassTextField);
		mainPane.addRow(7, new Label("Repeat"), newPassRepeatTextField);
		mainPane.add(changePasswordButton, 1, 8);

		emailUpdateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String oldEmail = currentUser.email;
				currentUser.email = emailTextField.getText();
				try {
					if(!((IUserService) ServiceManager.getService("user")).updateUser(session.token, currentUser)) {
						currentUser.email = oldEmail;
						alertError("Unable to set email");
					}
				} catch(RemoteException e) {
					currentUser.email = oldEmail;
					alertError("Connection error");
				}
			}
		});

		changePasswordButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					String newPass = newPassTextField.getText();
					String repeat = newPassRepeatTextField.getText();
					String currentPass = currentPassTextField.getText();
					if(!newPass.equals(repeat)) {
						alertError("Entered passwords do not match");
						return;
					}
					byte[] newToken = ((IUserService) ServiceManager.getService("user")).updateUserPasswd(session.token, currentUser.uid, currentPass, newPass);
					if(newToken != null) {
						session = new UserSession(newToken, session.cpr, session.lid);
					}
					else {
						alertError("Unable to set password.\nCheck that you entered your password correctly.");
					}
				} catch(RemoteException e) {
					alertError("Connection error");
				}
			}
		});

		return mainPane;
	}

	@Override
	public void onWindowOpen(Window window) {
		this.window = window;
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

	private void loadProfile() {
		emailTextField.setText(currentUser.email);
		nameTextLabel.setText(currentUser.name);
		cprTextLabel.setText("" + currentUser.cpr);
		rentalsTable.populate(getRentals());
	}

	private String[][] getRentals() {
		loadedBooks.clear();
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
		Book book = ((IInventoryService) ServiceManager.getService("inventory")).getBookByBID(session.token, b.bid);
		loadedBooks.add(book);
		s[0] = book.title;
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

	private void alertError(String s) {
		Alert alert = new Alert(Alert.AlertType.NONE, s, ButtonType.OK);
		alert.setTitle("Error");
		alert.showAndWait();
	}
}
