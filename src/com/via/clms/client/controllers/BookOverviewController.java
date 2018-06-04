package com.via.clms.client.controllers;

import com.via.clms.client.views.Controller;
import com.via.clms.client.views.Window;

import com.via.clms.shared.Book;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;

/**
 * 
 */
public class BookOverviewController implements Controller {
	Book book;
	/**
	 * 
	 */
	public BookOverviewController(Book book) {
		this.book = book;
	}

	/**
	 * 
	 */
	@Override
	public String getTitle() {
		return book.title;
	}

	/**
	 * 
	 */
	@Override
	public Parent getComponent() {
		GridPane mainPane = new GridPane();

		Insets insets = new Insets(0, 5, 0, 2);
		Label lTitle = new Label("Title:");
		lTitle.setStyle("-fx-font-weight: bold");
		lTitle.setPadding(insets);
		Label lAuthor = new Label("Author:");
		lAuthor.setStyle("-fx-font-weight: bold");
		lAuthor.setPadding(insets);
		Label lIsbn = new Label("ISBN:");
		lIsbn.setStyle("-fx-font-weight: bold");
		lIsbn.setPadding(insets);
		Label lRelease = new Label("Release:");
		lRelease.setStyle("-fx-font-weight: bold");
		lRelease.setPadding(insets);

		mainPane.addRow(0, lTitle, new Label(limitString(book.title)));
		mainPane.add(new Separator(Orientation.HORIZONTAL), 0, 1, 2, 1);
		mainPane.addRow(2, lAuthor, new Label(limitString(book.author)));
		mainPane.add(new Separator(Orientation.HORIZONTAL), 0, 3, 2, 1);
		mainPane.addRow(4, lIsbn, new Label(book.ISBN));
		mainPane.add(new Separator(Orientation.HORIZONTAL), 0, 5, 2, 1);
		mainPane.addRow(6, lRelease, new Label(formatDate(book.release)));

		TextArea descArea = new TextArea();
		descArea.setPrefRowCount(15);
		descArea.setEditable(false);
		descArea.setText(book.description);

		mainPane.add(descArea, 0, 7, 2, 1);
		return mainPane;
	}


	/**
	 * 
	 */
	@Override
	public void onWindowOpen(Window win) {

	}

	/**
	 * 
	 */
	@Override
	public void onWindowClose(Window win) {

	}

	/**
	 * 
	 */
	@Override
	public void onWindowRefresh(Window win) {

	}

	@Override
	public void onWindowResume(Window win) {

	}

	@Override
	public void onWindowPause(Window win) {

	}

	private String formatDate(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Date.from(Instant.ofEpochSecond(time)));
		return "" + cal.get(Calendar.YEAR);
	}

	private static String limitString(String s) {
		if(s.length() > 40) {
			char[] c = s.toCharArray();
			char[] n = Arrays.copyOf(c, 37);
			return String.copyValueOf(n) + "...";
		}
		return s;
	}
}
