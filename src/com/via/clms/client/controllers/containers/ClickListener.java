package com.via.clms.client.controllers.containers;

public interface ClickListener {
	/**
	 * When used in a ClickableTable, click() will be called with the index in the
	 * array used in populate(), which corresponds to the object presented at the clicked
	 * position in the table.
	 * @param i Index to be used for fetching relevant object from said array.
	 */
	void click(int i);

	/**
	 * Same as click(), but for double clicks.
	 * @param i
	 */
	void doubleClick(int i);
}
