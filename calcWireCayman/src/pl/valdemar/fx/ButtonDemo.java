package pl.valdemar.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Button (przycisk) to kontrolka, której kliknięcie generuje zdarzenie ActionEvent.
 * JavaFX udostępnia zwykłe przyciski, włączniki, pola wyboru i przyciski radio. Wspólne mechanizmy tych przycisków są
 * zdefiniowane w klasach ButtonBase i Labeled.
 * Klasa ButtonBase rozszerza klasę Labeled i definiuje wspólne elementy wszystkich przycisków
 * Klasa Labeled definiuje właściwości wspólne dla etykiet i przycisków. Przycisk przypomina etykietę, ale ma zdefiniowaną
 * w klasie ButtonBase właściwość onAction, która ustawia dla przycisku obiekt obsługi zdarzeń.
 *
 *
 * Obiekt typu Button można utworzyć za pomocą jednego z trzech konstruktorów:
 *  Button() - pusty przycisk
 *  Button(text: String) - przycisk z podanym tekstem
 *  Button(text: String, graphic: Node) - przycisk z podanym tekstem i grafiką
 *
 */

public class ButtonDemo extends Application {
    /**
     * The protected keyword is an access modifier used for attributes, methods and constructors,
     * making them accessible in the same package and subclasses.
     */
    /**
     * metoda getPane() celowo zwraca panel (wiersz 15.). Ta metoda będzie przesłaniana w podklasach w dalszych przykładach,
     * aby dodawać węzły do panelu. Zmienna text też jest zadeklarowana jako chroniona, aby była dostępna w podklasach
     */
    protected Text text = new Text(50, 50, "JavaFX Programming");

    protected BorderPane getPane() {
        HBox paneForButtons = new HBox(20);
        Button btLeft = new Button("Left", new ImageView("left.gif"));
        Button btRight = new Button("Right", new ImageView("right.gif"));
        paneForButtons.getChildren().addAll(btLeft, btRight);
        paneForButtons.setAlignment(Pos.CENTER);
        paneForButtons.setStyle("-fx-border-color: green");

        BorderPane pane = new BorderPane();
        pane.setBottom(paneForButtons);

        Pane paneForText = new Pane();
        paneForText.getChildren().add(text);
        pane.setCenter(paneForText);

        btLeft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                text.setX(text.getX() - 10);
            }
        });
        btRight.setOnAction(e -> text.setX(text.getX() + 10));
        return pane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a scene and place it in the stage
        Scene scene = new Scene(getPane(), 450, 200);
        primaryStage.setTitle("ButtonDemo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
