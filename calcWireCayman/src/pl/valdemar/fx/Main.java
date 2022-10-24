package pl.valdemar.fx;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;

public class Main extends Application {

    public Main() {
        System.out.println("Wywołanie konstruktora klasy Main");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Wywołanie metody start");
    }

    public static void main(String[] args) {
        DoubleProperty d1 = new SimpleDoubleProperty(1);
        DoubleProperty d2 = new SimpleDoubleProperty(2);
        d1.bind(d2);
        System.out.println("d1 is " + d1.getValue() + " and d2 is " + d2.getValue());
        d2.setValue(70.2);
        System.out.println("d1 is " + d1.getValue() + " and d2 is " + d2.getValue());
        System.out.println("Uruchamianie aplikacji");
        Application.launch(args);
    }
}
