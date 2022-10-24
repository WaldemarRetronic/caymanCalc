package pl.valdemar.fx.cayman;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

	@Override
	public void start(Stage stage) {
		FileChooserDemo mainView = new FileChooserDemo(stage);
		mainView.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
