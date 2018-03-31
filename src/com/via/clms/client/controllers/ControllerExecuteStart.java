package com.via.clms.client.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerExecuteStart extends Application {

	public static ControllerExecute debugger;
	public static String controllerName;

	public ControllerExecuteStart() {
		debugger = new ControllerExecute();
		controllerName = debugger.controller;

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		if (controllerName == "BookOverviewController") {
			try {
				BookOverviewController bookOverviewController = new BookOverviewController();
				primaryStage.setScene(new Scene(bookOverviewController.getComponent()));
				primaryStage.setTitle(bookOverviewController.getTitle());
				primaryStage.show();
			} catch (Exception e) {
				System.out.println("Error in creating controller object.");
				Runtime.getRuntime().exit(0);
			}
		}
		if (controllerName == "HomeController") {
			try {
				HomeController homeController = new HomeController();
				primaryStage.setScene(new Scene(homeController.getComponent()));
				primaryStage.setTitle(homeController.getTitle());
				primaryStage.show();
			} catch (Exception e) {
				System.out.println("Error in creating controller object.");
				Runtime.getRuntime().exit(0);
			}
		}
		if (controllerName == "LoginController") {
			try {
				LoginController loginController = new LoginController();
				primaryStage.setScene(new Scene(loginController.getComponent()));
				primaryStage.setTitle(loginController.getTitle());
				primaryStage.show();
			} catch (Exception e) {
				System.out.println("Error in creating controller object.");
				Runtime.getRuntime().exit(0);
			}
		}
		if (controllerName == "ProfileController") {
			try {
				ProfileController profileController = new ProfileController(392613);
				primaryStage.setScene(new Scene(profileController.getComponent()));
				primaryStage.setTitle(profileController.getTitle());
				primaryStage.show();
			} catch (Exception e) {
				System.out.println("Error in creating controller object.");
				Runtime.getRuntime().exit(0);
			}
		}
		if (controllerName == "SearchResultController") {
			try {
				SearchResultController searchResultController = new SearchResultController();
				primaryStage.setScene(new Scene(searchResultController.getComponent()));
				primaryStage.setTitle(searchResultController.getTitle());
				primaryStage.show();
			} catch (Exception e) {
				System.out.println("Error in creating controller object.");
				Runtime.getRuntime().exit(0);
			}
		}
		if (controllerName == "RentBookController") {
			try {
				RentBookController rentBookController = new RentBookController();
				primaryStage.setScene(new Scene(rentBookController.getComponent()));
				primaryStage.setTitle(rentBookController.getTitle());
				primaryStage.show();
			} catch (Exception e) {
				System.out.println("Error in creating controller object.");
				Runtime.getRuntime().exit(0);
			}
		}
		if (controllerName == "ReturnBookController") {
			try {
				ReturnBookController returnBookController = new ReturnBookController();
				primaryStage.setScene(new Scene(returnBookController.getComponent()));
				primaryStage.setTitle(returnBookController.getTitle());
				primaryStage.show();
			} catch (Exception e) {
				System.out.println("Error in creating controller object.");
				Runtime.getRuntime().exit(0);
			}
		}
//		if (controllerName == "RenewBookController") {
//			try {
//				RenewBookController renewBookController = new RenewBookController();
//				primaryStage.setScene(new Scene(renewBookController.getComponent()));
//				primaryStage.setTitle(renewBookController.getTitle());
//				primaryStage.show();
//			} catch (Exception e) {
//				System.out.println("Error in creating controller object.");
//				Runtime.getRuntime().exit(0);
//			}
//		}
//		if (controllerName == "LibrarianHomeController") {
//		try {
//			LibrarianHomeController librarianHomeController = new LibrarianHomeController();
//			primaryStage.setScene(new Scene(librarianHomeController.getComponent()));
//			primaryStage.setTitle(librarianHomeController.getTitle());
//			primaryStage.show();
//		} catch (Exception e) {
//			System.out.println("Error in creating controller object.");
//			Runtime.getRuntime().exit(0);
//		}
//	}	
		if (controllerName == "InventoryManagementController") {
		try {
			InventoryManagementController inventoryManagementController = new InventoryManagementController();
			primaryStage.setScene(new Scene(inventoryManagementController.getComponent()));
			primaryStage.setTitle(inventoryManagementController.getTitle());
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Error in creating controller object.");
			Runtime.getRuntime().exit(0);
		}
	}
//		if (controllerName == "ManageUsersController") {
//		try {
//			InventoryManagementController manageUsersController = new InventoryManagementController();
//			primaryStage.setScene(new Scene(manageUsersController.getComponent()));
//			primaryStage.setTitle(manageUsersController.getTitle());
//			primaryStage.show();
//		} catch (Exception e) {
//			System.out.println("Error in creating controller object.");
//			Runtime.getRuntime().exit(0);
//		}
//	}
//		if (controllerName == "SystemSettingsController") {
//		try {
//			SystemSettingsController systemSettingsController = new SystemSettingController();
//			primaryStage.setScene(new Scene(systemSettingsController.getComponent()));
//			primaryStage.setTitle(systemSettingsController.getTitle());
//			primaryStage.show();
//		} catch (Exception e) {
//			System.out.println("Error in creating controller object.");
//			Runtime.getRuntime().exit(0);
//		}

	}

	public static void main(String[] args) {

		Application.launch(controllerName);
		System.out.println("Session finished");

	}
}