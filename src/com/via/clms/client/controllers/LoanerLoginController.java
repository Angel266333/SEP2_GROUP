package com.via.clms.client.controllers;

import com.via.clms.client.ServiceManager;
import com.via.clms.client.controllers.containers.UserSession;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.ResultController;
import com.via.clms.client.views.Window;
import com.via.clms.proxy.IUserService;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.rmi.RemoteException;

public class LoanerLoginController extends ResultController<UserSession> {
	UserSession session;

	public LoanerLoginController(UserSession session) {
		this.session = session;
	}

	@Override
	public String getTitle() {
		return "Login";
	}

	@Override
	public Parent getComponent() {
		TextField userTf = new TextField();
		userTf.setPrefColumnCount(15);

		TextField passTf = new TextField();
		passTf.setPrefColumnCount(15);

		Label infoL = new Label("Login to use this feature.");
		Label userL = new Label("CPR");
		Label passL = new Label("Password");

		Button loginBtn = new Button("Login");
		Button canclBtn = new Button("Cancel");

		loginBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				IUserService service = (IUserService) ServiceManager.getService("user");
				long cpr;
				try {
					cpr = Long.parseLong(userTf.getText());
				} catch(NumberFormatException e) {
					showAlert("Invalid CPR");
					return;
				}
				String passwd = passTf.getText();
				try {
					byte[] token = service.getUserToken(cpr, passwd);
					if(token == null) {
						showAlert("Invalid login");
						return;
					}
					UserSession loanerSession = new UserSession(token, cpr, session.lid);
					setResult(loanerSession);
					getWindow().close();
				} catch(RemoteException e) {
					showAlert("Connection error");
				}
			}
		});

		canclBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setResult(null);
				getWindow().close();
			}
		});

		HBox buttons = new HBox(loginBtn, canclBtn);
		return new VBox(infoL, new Separator(Orientation.HORIZONTAL), userL, userTf, passL, passTf, buttons);
	}

	private void showAlert(String s) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(s);
		alert.showAndWait();
	}
}
