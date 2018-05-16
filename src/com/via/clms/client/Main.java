package com.via.clms.client;

import com.via.clms.client.controllers.BaseController;
import com.via.clms.client.controllers.HomeController;
import com.via.clms.client.controllers.LoginController;
import com.via.clms.client.views.Controller;
import com.via.clms.client.views.DialogWindow;
import com.via.clms.client.views.ResultController;
import com.via.clms.client.views.ResultListener;
import com.via.clms.client.views.Window;

/**
 * 
 */
public class Main {

	public static void main(String[] args) {

		// This should be selected somehow
		int lid = 0;
		ResultController<byte[]> controller = new LoginController(lid);

		controller.setResultListener(new ResultHandler());

		Window window = new DialogWindow(controller);

		window.open();

	}

	private static class ResultHandler implements ResultListener<byte[]> {

		@Override
		public void onReturnResult(byte[] result) {

			if (result == null) {
				return;
			}

			Controller home = new HomeController();
			Controller controller = new BaseController(home);
			
			Window window = new Window(controller);
			window.open();

		}

	}
}