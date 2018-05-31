package com.via.clms.client.controllers;
import com.via.clms.Log;
import com.via.clms.client.ServiceManager;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;
import com.via.clms.server.services.InventoryService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class ReturnBookController implements Controller {
	private GridPane mainPane;

	public ReturnBookController() {

		mainPane = new GridPane();

		String labelStyle = "-fx-font-weight: bold";
		Label bookIDLabel = new Label("Book ID  ");

		TextField bookIDText = new TextField();

		bookIDLabel.setStyle(labelStyle);

		Button returnButton = new Button("  Return the book  ");
		
		returnButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (bookIDText.getText().isEmpty()) {
					Alert alertFailiure = new Alert(AlertType.ERROR);
					alertFailiure.setTitle("Error Dialog");
					alertFailiure.setHeaderText("No book ID entered");
					alertFailiure.setContentText("Please enter a book id to return");
					alertFailiure.showAndWait();
					
				}  else {
					

					try {

						int bookId = Integer.parseInt(bookIDText.getText());
						InventoryService service = (InventoryService) ServiceManager.getService("inventory");

						service.removeRental(bookId);
						
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
	}

	public String getTitle() {
		return "Return a book";
	}

	public Parent getComponent() {
		
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
