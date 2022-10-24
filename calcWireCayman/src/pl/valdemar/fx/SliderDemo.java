package pl.valdemar.fx;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Slider (suwak) przypomina pasek przewijania, ale ma więcej właściwości i może przyjmować różną postać.
 * Klasa Slider umożliwia graficzne zaznaczenie wartości przez przesunięcie uchwytu w wyznaczonym przedziale.
 * Suwak może obejmować główne i pomocnicze kreski podziałki. Liczba jednostek między kreskami podziałki jest zapisana
 * we właściwościach majorTickUnit i minorTickUnit. Suwaki można wyświetlać w poziomie i pionie, z kreskami podziałki
 * i bez nich oraz z etykietami i bez nich.
 * <p>
 * KONSTRUKTORY I WŁAŚCIWOŚCI KLASY SLIDER
 * -blockIncrement: DoubleProperty - wartość zmiany po kliknięciu paska (domyślnie 10)
 * -max: DoubleProperty - maksymalna wartość reprezentowana przez dany suwak (domyślnie 100)
 * -min: DoubleProperty - minimalna wartość reprezentowana przez dany suwak (domyślnie 0)
 * -value: DoubleProperty - aktualna wartość suwaka (domyślnie 0)
 * -orientation: ObjectProperty<Orientation> - określa układ suwaka (domyślnie: HORIZONTAL)
 * -majorTickUnit: DoubleProperty - odległość między głównymi kreskami podziałki
 * -minorTickCount: IntegerProperty - liczba pomocniczych kresek podziałki między kreskami głównymi
 * -showTickLabels: BooleanProperty - określa, czy wyświetlane są etykiety [rzy kreskach podziałki
 * -showTickMarks: BooleanProperty - określa, czy wyświetlane są kreski podziałki
 *
 * +Slider() - tworzy domyślny suwak
 * +Slider(min: double, max: double, value: double) - tworzy suwak z podanymi wartościami minimalną, maksymalną i aktualną.
 *
 * Klasa Slider działa podobnie jak klasa ScrollBar, ale daje więcej możliwości. W tym przykładzie pokazano, że do
 * suwaka możesz dodać etykiety, główne kreski podziałki i jej pomocnicze kreski.
 *
 * W kodzie rejestrowane są odbiorniki reagujące na zmianę wartości właściwości value suwaków slHorizontal
 * (wiersze 73. – 78.) i sbVertical (wiersze 80. – 82.). Te odbiorniki są powiadamiane o zmianie wartości w suwakach i
 * uruchamiają obiekt obsługi zdarzeń, który ustawia nową lokalizację tekstu.
 * Zauważ, że ponieważ wartość suwaka pionowego maleje od góry do dołu, wartość współrzędnej y tekstu trzeba odpowiednio zmodyfikować.
 * Kod z wierszy 73. – 82. można zastąpić za pomocą właściwości wiążącej:
 * text.xProperty().bind(slHorizontal.valueProperty().multiply(paneForText.widthProperty()).divide(slHorizontal.maxProperty()));
 * text.yProperty().bind((slVertical.maxProperty().subtract(slVertical.valueProperty()).multiply(paneForText.heightProperty().divide(slVertical.maxProperty()))));
 *
 */
public class SliderDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Text text = new Text(20, 20, "JavaFX Programming");

        Slider slHorizontal = new Slider();
        slHorizontal.setShowTickLabels(true);
        slHorizontal.setShowTickMarks(true);

        Slider slVertical = new Slider();
        slVertical.setOrientation(Orientation.VERTICAL);
        slVertical.setShowTickLabels(true);
        slVertical.setShowTickMarks(true);
        slVertical.setValue(100);

        // Create a text in a pane
        Pane paneForText = new Pane();
        paneForText.getChildren().add(text);

        // Create a border pane to hold text and scroll bars
        BorderPane pane = new BorderPane();
        pane.setCenter(paneForText);
        pane.setBottom(slHorizontal);
        pane.setRight(slVertical);

        slHorizontal.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                text.setX(slHorizontal.getValue() * paneForText.getWidth() / slHorizontal.getMax());
            }
        });

        slVertical.valueProperty().addListener(ov -> {
            text.setY((slVertical.getMax() - slVertical.getValue()) * paneForText.getHeight() / slVertical.getMax());
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 450, 170);
        primaryStage.setTitle("SliderDemo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
