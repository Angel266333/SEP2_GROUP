package com.via.clms.client.controllers;
import com.via.clms.Log;
import com.via.clms.client.ServiceManager;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.rmi.RemoteException;

public class ReturnBookController implements Controller {
	private GridPane mainPane;
	
	UserSession session;
	LoanerLoginController loanerLogin;
	User currentUser;
	UserSession loanerSession = null;

	public ReturnBookController(UserSession session) {
		this.session = session;
		loanerLogin = new LoanerLoginController(session);
		loanerLogin.setResultListener(new ResultListener<UserSession>() {
			@Override
			public void onReturnResult(UserSession result) {
				try {
					if(result == null) {
						return;
					}
					currentUser = ((IUserService) ServiceManager.getService("user")).getUserByCPR(session.token, result.cpr);
					loanerSession = result;
				} catch(RemoteException e) {
					showAlert("Login failed");
				}
			}
		});
	}

	public String getTitle() {
		return "Return a book";
	}

	public Parent getComponent() {
		Window window = new Window(loanerLogin);
		window.open();

		mainPane = new GridPane();

		String labelStyle = "-fx-font-weight: bold";
		Label bookIDLabel = new Label("ISBN  ");

		TextField bookIDText = new TextField();

		bookIDLabel.setStyle(labelStyle);

		Button returnButton = new Button("  Return the book  ");

		returnButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(loanerSession == null) {
					showAlert("Not logged in.");
					return;
				}
				if (bookIDText.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No book ID entered");
					alertFailiure.setContentText("Please enter a book id to return");
					alertFailiure.showAndWait();

				}
				else {
					try {
						Integer.parseInt(bookIDText.getText());
						IInventoryService service = (IInventoryService) ServiceManager.getService("inventory");
						Book b = service.getBookByISBN(session.token, session.lid, bookIDText.getText());
						service.removeRental(loanerSession.token, b.bid, session.lid, currentUser.uid);
						bookIDText.clear();
					} catch (Exception e) {
						Alert alertFailiure = new Alert(AlertType.ERROR);
						alertFailiure.setTitle("Error Dialog");
						alertFailiure.setHeaderText("Invalid return");
						alertFailiure.setContentText("Could not return the book! General error.");
						alertFailiure.show();

						Log.error(e);
					}
				}
			}
		});

		mainPane.add(bookIDLabel, 0, 0);
		mainPane.add(bookIDText, 0, 1);

		mainPane.add(returnButton, 1, 1);

		return mainPane;
	}

	private void showAlert(String s) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(s);
		alert.showAndWait();
	}

	@Override
	public void onWindowOpen(Window win) {

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
