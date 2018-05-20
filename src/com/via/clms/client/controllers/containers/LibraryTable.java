package com.via.clms.client.controllers.containers;

import com.via.clms.shared.Library;

import javafx.scene.control.Label;

public class LibraryTable extends ClickableTable<Library> {

	@Override
	public Label[] makeLabels(Library dataElement) {
		Label[] libraryLabels = new Label[3];
		libraryLabels[0] = new Label(" " + dataElement.lid);
		libraryLabels[1] = new Label("" + dataElement.name);
		libraryLabels[2] = new Label(dataElement.location);
		return libraryLabels;
	}

	@Override
	public Label[] makeHeaderLabels() {
		// If it works, don't break it, improve it.
		String extraSpace = "   "
				+ "    "     ////////
				+ "    " 	///////
				+ "    "   //////
				+ "    "  /////
				+ "    " ////
				+ "    "///
				+ "  ";//
		Label[] libraryLabels = new Label[3];
		libraryLabels[0] = new Label("LID" + extraSpace);
		libraryLabels[1] = new Label("Name" + extraSpace);
		libraryLabels[2] = new Label("Location");
		return libraryLabels;
	}
}