package pl.valdemar.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Etykieta to obszar do wyświetlania krótkiego tekstu, węzła lub obu tych elementów. Często służy doopisu innych kontrolek
 * (zwykle pól tekstowych).
 * Etykiety i przyciski mają wiele cech wspólnych. Te cechy są zdefiniowane w klasie Labeled.
 * W klasie Labeled zdefiniowane są wspólne właściwości klas Label, Button, CheckBox i RadioButton
 * Obiekt typu Label można utworzyć za pomocą jednego z trzech konstruktorów:
 *  Label() - pusta etykieta
 *  Label(text: String) - etykieta z podanym tekstem
 *  Label(text: String, graphic: Node) - etykieta z podanym tekstem i grafiką
 * Do właściwości graphic można przypisać dowolny węzeł (na przykład kształt, grafikę lub kontrolkę).
 */

public class LabelWithGraphic extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ImageView us = new ImageView(new Image("us.gif"));
        Label lb1 = new Label("US\n50 States", us);
        lb1. setStyle("-fx-border-color: green; -fx-border-width: 2");
        /**
         * public enum ContentDisplay
         * extends java.lang.Enum<ContentDisplay>
         *
         * The position to place the content within a Label.
         *
         * BOTTOM
         * Content will be placed at the bottom of the Label.
         * CENTER
         * Content will be placed at the center of the Label.
         * GRAPHIC_ONLY
         * Only the content will be displayed.
         * LEFT
         * Content will be placed at the left of the Label.
         * RIGHT
         * Content will be placed at the right of the Label.
         * TEXT_ONLY
         * Only the label's text will be displayed.
         * TOP
         * Content will be placed at the top of the Label.
         */
        lb1.setContentDisplay(ContentDisplay.BOTTOM);
        lb1.setTextFill(Color.RED);

        Label lb2 = new Label("Circle", new Circle(50, 50, 25));
        lb2.setContentDisplay(ContentDisplay.TOP);
        lb2.setTextFill(Color.ORANGE);

        Label lb3 = new Label("Rectangle", new Rectangle(10, 10, 50, 25));
        lb3.setContentDisplay(ContentDisplay.RIGHT);

        Label lb4 = new Label("Ellipse", new Ellipse(50, 50, 50, 25));
        lb4.setContentDisplay(ContentDisplay.LEFT);

        Ellipse ellipse = new Ellipse(50, 50, 50, 25);
        ellipse.setStroke(Color.GREEN);
        ellipse.setFill(Color.WHITE);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(ellipse,new Label("JavaFX"));
        Label lb5 = new Label("A pane inside a label", stackPane); // w etykiecie można umieścić dowolny węzeł.
        lb5.setContentDisplay(ContentDisplay.BOTTOM);

        HBox pane = new HBox(20); // odstępy między węzłami: 20
        pane.getChildren().addAll(lb1, lb2, lb3, lb4, lb5);

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 450, 150);
        primaryStage.setTitle("LabelWithGraphic"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene int hte stage
        primaryStage.show(); // Display the stage
    }
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
