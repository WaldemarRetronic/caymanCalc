package pl.valdemar.fx;
/**
 * Klasa ScrollBar to kontrolka paska przewijania umożliwiająca wybór wartości z przedziału.
 * Na rysunku 16.21 pokazany jest pasek przewijania. Użytkownik zwykle zmienia wartości w pasku przewijania za pomocą myszy.
 * Użytkownik może na przykład przeciągnąć uchwyt, kliknąć pasek albo lewy lub prawy przycisk.
 *
 * javafx.scene.control.ScrollBar
 *   -blockIncrement: DoubleProperty - wartość zmiany po kliknięciu paska (domyślnie: 10)
 *   -max: DoubleProperty - maksymalna wartość reprezentowana przez dany pasek przewijania (domyślnie: 100)
 *   -min: DoubleProperty - minimalna Wartość reprezentowana przez dany pasek przewijania (domyślnie: 1)
 *   -unitIncrement: DoubleProperty - wartyość zmiany po wywołaniu metody increment() i decrement() (domyślnie: 1)
 *
 *   -value: DoubleProperty - aktualna wartość paska przewijania (domyślnie: 0)
 *   -visibleAmount: DoubleProperty - szerokość paska przewijania (domyślnie: 15)
 *   -orientation: ObjectProperty<Orientation> - określa układ paska przewijania (domyślnie: HORIZONTAL)
 *
 *   +ScrollBar() - tworzy domyślny poziomy pasek przewijania
 *   +increment() - zwiększa wartość paska przewijania o unitIncrement
 *   +decrement() - zmniejsza wartość paska przewijania o unitIncrement
 *
 * Długość paska jest równa max + visibleAmount. Gdy zaznaczona jest wartość maksymalna, lewa krawędź uchwytu jest równa max,
 * a prawa — max + visibleAmount.
 * Gdy użytkownik zmienia wartość paska przewijania, odbiornik otrzymuje powiadomienie o tym. Aby reagować na taką zmianę,
 * możesz zarejestrować odbiornik powiązany z właściwością valueProperty paska przewijania:
 * ScrollBar sb = new ScrollBar();
 * sb.valueProperty().addListener(ov −>{
 *   System.out.println("dawna wartość: " + oldVal);
 *   System.out.println("nowa wartość: " + newVal);
 * });
 *
 */

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ScrollBarDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Text text = new Text(20, 20, "JavaFX Programming");

        ScrollBar sbHorizontal = new ScrollBar();
        ScrollBar sbVertical= new ScrollBar();
        sbVertical.setOrientation(Orientation.VERTICAL);

        // Create a text in a pane
        Pane paneForText = new Pane();
        paneForText.getChildren().add(text);

        // Create a border pane to hold text and scroll bars
        BorderPane pane = new BorderPane();
        pane.setCenter(paneForText);
        pane.setBottom(sbHorizontal);
        pane.setRight(sbVertical);

        // Listener for horizontal scroll bar value change
        /**
         * rejestrowany jest odbiornik zdarzenia zmiany wartości właściwości value paska sbHorizontal.
         * Zmiana wartości tego paska powoduje powiadomienie odbiornika, który wywołuje wtedy obiekt obsługi zdarzeń,
         * aby ustawić nową wartość współrzędnej x dla tekstu, odpowiadającą aktualnej wartości z paska sbHorizontal
         */
        sbHorizontal.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                text.setX(sbHorizontal.getValue() * paneForText.getWidth() / sbHorizontal.getMax());
            }
        });

        // Listener for vertical scroll bar value change
        /**
         * rejestrowany jest odbiornik zdarzenia zmiany wartości właściwości value paska sbVertical.
         * Zmiana wartości tego paska powoduje powiadomienie odbiornika, który wywołuje wtedy obiekt obsługi zdarzeń,
         * aby ustawić nową wartość współrzędnej y dla tekstu, odpowiadającą aktualnej wartości z paska sbVertical
         */
        sbVertical.valueProperty().addListener(ov -> {
            text.setY(sbVertical.getValue() * paneForText.getHeight() / sbVertical.getMax());
        });


//        // Kod z wierszy 69. – 84. można zastąpić, używając właściwości wiążących:
//        text.xProperty().bind(sbHorizontal.valueProperty().multiply(paneForText.widthProperty()).divide(sbHorizontal.maxProperty()));
//        text.yProperty().bind(sbVertical.valueProperty().multiply(paneForText.heightProperty().divide(sbVertical.maxProperty())));

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 450, 170);
        primaryStage.setTitle("ScrollBarDemo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
