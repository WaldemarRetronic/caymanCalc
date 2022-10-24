package pl.valdemar.fx;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Widok listy to kontrolka, która działa podobnie jak pole kombi, ale umożliwia wybór jednej lub kilku wartości.
 * ListView jest klasą generyczną (podobnie jak ArrayList). Generyczny typ T określa typ elementów przechowywanych w widoku listy.
 *
 * javafx.scene.control.ListView<T>
 *   -items: ObjectProperty<ObservableList<T>> - elementy w widoku listy
 *   -orientation: BooleanProperty - informuje , czy elementy są wyświetlane w poziomie czy w pionie
 *   -selectionMode: ObjectProperty<MultipleSelectionModel<T>> - okresla sposób zaznaczania elementów. Służy też do pobierania
 *                   zaznaczonych elementów
 *
 *   +ListView() - tworzy pusty widok listy
 *   +ListView(items: ObservableList<T>) - tworzy widok listy z okreslonymi elementami
 *
 * Metoda getSelectionModel() zwraca obiekt typu SelectionModel, zawierający metody do pobierania trybu zaznaczania
 * elementów oraz pobierania zaznaczonych elementów i ich indeksów. Tryb zaznaczania to jedna z dwóch stałych:
 * SelectionMode.MULTIPLE i SelectionMode.SINGLE. Informują one, czy zaznaczyć można tylko jeden element, czy więcej.
 * Ustawienie domyślne to SelectionMode.SINGLE (czyli jeden element).
 *
 * Poniższe instrukcje tworzą widok listy z sześcioma elementami i możliwością zaznaczania wielu z nich:
 * ObservableList<String>items = FXCollections.observableArrayList("Element 1", "Element 2", "Element 3", "Element 4", "Element 5", "Element 6");
 * ListView<String>lv = new ListView<>(items);
 * lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 *
 * Właściwość selectionModel widoku listy ma właściwość selectedItemProperty (jest to instancja typu Observable).
 * W ObservablePropertyDemo.java opisane zostało, że do takiej właściwości można dodać odbiornik do obsługi zmian jej wartości:
 * lv.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
 *   public void invalidated(Observable ov) {
 *   System.out.println("Indeksy zaznaczonych elementów: " + lv.getSelectionModel().getSelectedIndices());
 *   System.out.println("Zaznaczone elementy: " + lv.getSelectionModel().getSelectedItems());
 *   }
 * });
 * Tę anonimową klasę wewnętrzną można uprościć za pomocą wyrażenia lambda:
 * lv.getSelectionModel().selectedItemProperty().addListener(ov ->{
 *   System.out.println("Indeksy zaznaczonych elementów: " + lv.getSelectionModel().getSelectedIndices());
 *   System.out.println("Zaznaczone elementy: " + lv.getSelectionModel().getSelectedItems());
 * });
 *
 */

public class ListViewDemo extends Application {
    // Declare an array of Strings for flag titles
    private String[] flagTitles = {"Canada", "China", "Denmark", "France", "Germany", "India",
            "Norway", "United Kingdom", "United States of America"};

    // Declare an ImageView array for the national flags of 9 countries
    private ImageView[] ImageViews = {new ImageView("ca.gif"), new ImageView("china.gif"),
            new ImageView("denmark.gif"), new ImageView("fr.gif"),
            new ImageView("germany.gif"), new ImageView("india.gif"),
            new ImageView("norway.gif"), new ImageView("uk.gif"),
            new ImageView("us.gif")
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        ListView<String> lv = new ListView<>(FXCollections.observableArrayList(flagTitles));
        lv.setPrefSize(400, 400);
        lv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Create a pane to hold image views
        FlowPane imagePane = new FlowPane(10, 10);
        BorderPane pane = new BorderPane();
        pane.setLeft(new ScrollPane(lv));
        pane.setCenter(imagePane);

        /**
         * Gdy użytkownik zaznacza państwa w widoku listy, uruchamiany jest obiekt obsługi zdarzeń tego widoku.
         * Ten obiekt pobiera indeksy zaznaczonych elementów i dodaje odpowiadające im widoki obrazów do panelu FlowPane.
         */
        lv.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                imagePane.getChildren().clear();
                for (Integer i: lv.getSelectionModel().getSelectedIndices()) {
                    System.out.println("test");
                    imagePane.getChildren().add(ImageViews[i]);
                }
            }
        });

        // Create a scene and place it int the stage
        Scene scene = new Scene(pane, 450, 170);
        primaryStage.setTitle("ListViewDemo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
