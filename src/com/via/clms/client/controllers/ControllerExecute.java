package com.via.clms.client.controllers;

import java.util.Scanner;

public class ControllerExecute {

	Scanner keyboard = new Scanner(System.in);

	public int selection;
	public String controller;

	public ControllerExecute() {

		System.out.println("Controller selection: ");
		System.out.println("0) Exit application");
		System.out.println("1) BookOverviewController");
		System.out.println("2) HomeController");
		System.out.println("3) LoginController");
		System.out.println("4) ProfileController");
		System.out.println("5) SearchResultController");
		System.out.println("6) RentBookController");
		System.out.println("7) ReturnBookController");
		System.out.println("8) RenewBookController [UNAVAILABLE]");
		System.out.println("9) LibrarianHomeController");
		System.out.println("10) InventoryManagementController");
		System.out.println("11) ManageUsersController [UNAVAILABLE]");
		System.out.println("12) SystemSettingsController [UNAVAILABLE]");
		selection = keyboard.nextInt();
		logicHandler();

	}

	public int logicHandler() {

		boolean invalidInput = false;
		while (!invalidInput) {
			if (selection == 0) {
				// Exit
				System.out.println("Application terminated.");
				Runtime.getRuntime().exit(0);
			}
			if (selection == 1) {
				System.out.println("BookOverviewController now running");
				controller = "BookOverviewController";
				invalidInput = true;
			}
			if (selection == 2) {
				System.out.println("HomeController now running");
				controller = "HomeController";
				invalidInput = true;
			}
			if (selection == 3) {
				System.out.println("LoginController now running");
				controller = "LoginController";
				invalidInput = true;
			}
			if (selection == 4) {
				System.out.println("ProfileController now running");
				controller = "ProfileController";
				invalidInput = true;
			}
			if (selection == 5) {
				System.out.println("SearchResultController now running");
				controller = "SearchResultController";
				invalidInput = true;
			}
			if (selection == 6) {
				System.out.println("RentBookController now running");
				controller = "RentBookController";
				invalidInput = true;
			}
			if (selection == 7) {
				System.out.println("ReturnBookController now running");
				controller = "ReturnBookController";
				invalidInput = true;
			}
			if (selection == 8) {
				System.out.println("RenewBookController now running");
				controller = "RenewBookController";
				invalidInput = true;
			}
			if (selection == 9) {
				System.out.println("LibrarianHomeController now running");
				controller = "LibrarianHomeController";
				invalidInput = true;
			}
			if (selection == 10) {
				System.out.println("InventoryManagementController now running");
				controller = "InventoryManagementController";
				invalidInput = true;
			}
			if (selection == 11) {
				System.out.println("ManageUsersController now running");
				controller = "ManageUsersController";
				invalidInput = true;
			}
			if (selection == 12) {
				System.out.println("SystemSettingsController now running");
				controller = "SystemSettingsController";
				invalidInput = true;
			}
			if (selection > 12 || selection < 1) {
				System.out.println("Error, enter valid choice!");
				selection = keyboard.nextInt();
		}

		}
		return selection;
	}
}