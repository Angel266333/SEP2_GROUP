package com.via.clms.client.controllers;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class ManageUsersController implements Controller {
	BorderPane mainPane;
	TextField uidTF;
	TextField nameTF;
	TextField cprTF;
	TextField emailTF;
	TextField rolesTF;
	Button submitButton;
	TextField listBarTF;
	Button listBarSearch;
	UsersListPane ulPane;

	@Override
	public String getTitle() {
		return "Manage Users";
	}

	@Override
	public Parent getComponent() {
		mainPane = new BorderPane();
		GridPane leftPane = new GridPane();
		uidTF = new TextField();
		uidTF.setEditable(false);
		uidTF.setPrefColumnCount(20);
		nameTF = new TextField();
		nameTF.setPrefColumnCount(20);
		cprTF = new TextField();
		cprTF.setPrefColumnCount(20);
		emailTF = new TextField();
		emailTF.setPrefColumnCount(20);
		rolesTF = new TextField();
		rolesTF.setPrefColumnCount(20);
		submitButton = new Button("Submit");
		leftPane.add(new Label("User ID"), 0, 0);
		leftPane.add(uidTF, 0, 1);
		leftPane.add(new Label("User name"), 0, 2);
		leftPane.add(nameTF, 0, 3);
		leftPane.add(new Label("CPR"), 0, 4);
		leftPane.add(cprTF, 0, 5);
		leftPane.add(new Label("Email"), 0, 6);
		leftPane.add(emailTF, 0, 7);
		leftPane.add(new Label("Roles"), 0, 8);
		leftPane.add(rolesTF, 0, 9);
		leftPane.add(submitButton, 0, 10);
		mainPane.setLeft(leftPane);

		User[] testUsers = {new User(1001, "John Hansen", 1212801111L, "john@john.dk", 0), new User(1002, "Arne Larsen", 1010872323L, "arne@arne.dk", 0)};
		ArrayList<User> alu = new ArrayList<>();
		alu.addAll(Arrays.asList(testUsers));
		alu.addAll(Arrays.asList(testUsers));
		alu.addAll(Arrays.asList(testUsers));
		testUsers = Arrays.copyOf(alu.toArray(), alu.size(), User[].class);
		ScrollPane scrollPane = new ScrollPane();
		ulPane = new UsersListPane();
		ulPane.populate(testUsers);
		scrollPane.setContent(ulPane);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		scrollPane.autosize();
		scrollPane.setPrefViewportHeight(400);

		listBarTF = new TextField();
		listBarTF.setPrefColumnCount(20);
		listBarSearch = new Button("Search");
		listBarSearch.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				search(listBarTF.getText());
			}
		});
		HBox listBar = new HBox(listBarTF, listBarSearch);
		VBox rightBox = new VBox(listBar, scrollPane);
		mainPane.setRight(rightBox);

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

	private class UsersListPane extends GridPane {
		int firstEmpty = 0;

		private class ClickHandler implements EventHandler<MouseEvent> {
			int uid;

			public ClickHandler(int uid) {
				this.uid = uid;
			}

			@Override
			public void handle(MouseEvent event) {
				updateLeft(uid);
			}
		}

		public void clearList() {
			super.getChildren().clear();
			firstEmpty = 0;
		}

		private String cprPretty(long cpr) {
			char[] cprArray = ("" + cpr).toCharArray();
			StringBuffer sb = new StringBuffer();
			sb.append(Arrays.copyOfRange(cprArray, 0, 4));
			sb.append(' ');
			sb.append(Arrays.copyOfRange(cprArray, 4, 6));
			sb.append(" - ");
			sb.append(Arrays.copyOfRange(cprArray, 6, 10));
			return sb.toString();
		}

		public void populate(User[] users) {
			clearList();
			for(User u : users) {
				ClickHandler ch = new ClickHandler(u.uid);
				Label a = new Label("" + u.uid);
				Label b = new Label(cprPretty(u.cpr));
				Label c = new Label(u.name);
				for(Label l : new Label[]{a, b, c}) {
					l.addEventFilter(MouseEvent.MOUSE_CLICKED, ch);
					l.setStyle("-fx-padding: 5px 20px 5px 0px");
				}
				super.addRow(firstEmpty, a, b, c);
				firstEmpty++;
				super.add(new Separator(Orientation.HORIZONTAL), 0, firstEmpty, 5, 1);
				firstEmpty++;
			}
		}
	}

	private void updateLeft(int uid) {
		uidTF.setText("" + uid);
	}

	private void search(String term) {
		User[] searchResult = null;
		if(term.isEmpty()) {
			return;
		}
		if(term.matches("[0-9]+")) {
			if(term.length() < 10) {
				//UserService search by uid.
				System.out.println("by uid");
			}
			else {
				//UserService search by cpr.
				System.out.println("by cpr");
			}
		}
		else {
			//UserService search by name.
			System.out.println("by name");
		}
		//ulPane.populate(searchResult);
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
