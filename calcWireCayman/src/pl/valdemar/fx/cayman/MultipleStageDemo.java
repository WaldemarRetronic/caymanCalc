package pl.valdemar.fx.cayman;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MultipleStageDemo extends Application {
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Button("OK"), 200, 250);
        primaryStage.setTitle("MyJavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        Stage stage = new Stage();
        stage.setTitle("Drugie okno");
        stage.setScene(new Scene(new Button("Nowe okno"), 200, 250));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args); // Static method of class Application
    }
}
