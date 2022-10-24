package pl.valdemar.fx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Pole kombi, nazywane też listą wyboru lub listą rozwijaną, zawiera listę elementów, które można wybierać.
 * Pole kombi przydaje się do ograniczania zakresu dostępnych opcji i pomaga uniknąć niewygodnego sprawdzania poprawności
 * wprowadzanych danych. ComboBox jest klasą generyczną (podobnie jak ArrayList). Typ generyczny T określa typ elementów
 * przechowywanych w polu kombi. ComboBox dziedziczy z ComboBoxBase<T>
 * <p>
 * ComboBoxBase właściwości:
 * -value: ObjectProperty<T> - wartość zaznaczona w polu
 * -editable: BooleanProperty - określa, czy pole kombi umożliwia wpisywanie danych
 * -onAction: ObjectProperty<EventHandler<ActionEvent>> - określa obiekt obsługi zdarzeń ActionEvent
 * <p>
 * ComboBox<T> właściwości i konstruktory:
 * -items: ObjectProperty<ObservableList<T>> - elementy z okienka pola kombi
 * -visibleRowCount: IntegerProperty - maksymalna liczba widocznych wierszy elementów w okienku pola kombi
 * <p>
 * +ComboBox() - tworzy puste pole kombi
 * +ComboBox(items: ObservableList<T>) - tworzy pole kombi z określonymi elementami
 * <p>
 * <p>
 * Te instrukcje tworzą pole kombi z czterema elementami,
 * czerwonym tłem i wybranym pierwszym elementem:
 * <p>
 * ComboBox<String>cbo = new ComboBox<>();
 * cbo.getItems().addAll("Element 1", "Element 2", "Element 3", "Element 4");
 * cbo.setValue("Element 1");
 * <p>
 * Klasa ComboBox dziedziczy po klasie ComboBoxBase. ComboBox może zgłaszać zdarzenia ActionEvent. Każde zaznaczenie
 * elementu skutkuje zgłoszeniem tego zdarzenia. ObservableList jest podinterfejsem interfejsu java.util.List.
 * Dlatego wszystkie metody zdefiniowane w interfejsie List można stosować także w obiektach implementujących
 * interfejs ObservableList. JavaFX udostępnia jako udogodnienie statyczną metodę
 * FXCollections.observableArrayList(arrayOfElements), która tworzy listę typu ObservableList na podstawie tablicy elementów.
 */

public class ComboBoxDemo extends Application {
    // Declare an array of Strings for flag titles
    private String[] flagTitles = {"Canada", "China", "Denmark", "France", "Germany", "India",
            "Norway", "United Kingdom", "United States of America"};

    // Declare an ImageView array for the national flags of 9 countries
    private ImageView[] flagImage = {new ImageView("ca.gif"), new ImageView("china.gif"),
            new ImageView("denmark.gif"), new ImageView("fr.gif"),
            new ImageView("germany.gif"), new ImageView("india.gif"),
            new ImageView("norway.gif"), new ImageView("uk.gif"),
            new ImageView("us.gif")
    };

    // Declare array of Strings for flag description
    private String[] flagDescription = new String[9];

    // Declare and create a description pane
    private DescriptionPane descriptionPane = new DescriptionPane();

    // Create combo box for selecting countries
    private ComboBox<String> cbo = new ComboBox<>(); // flagTitles

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set text description
        flagDescription[0] = "The Canadian national flag ...";
        flagDescription[1] = "Description for China ...";
        flagDescription[2] = "Description for Denmark ...";
        flagDescription[3] = "Description for France ...";
        flagDescription[4] = "Description for Germany ...";
        flagDescription[5] = "Description for India ...";
        flagDescription[6] = "Description for Norway ...";
        flagDescription[7] = "Description for UK ...";
        flagDescription[8] = "Description for US ...";

        // Set the first country (Canada) for display
        setDisplay(0);

        // Add combo box and description pane to the border pane
        BorderPane pane = new BorderPane();

        BorderPane paneForComboBox = new BorderPane();
        paneForComboBox.setLeft(new Label("Select a country: "));
        paneForComboBox.setCenter(cbo);
        pane.setTop(paneForComboBox);
        cbo.setPrefWidth(400);
        cbo.setValue("Canada");

        ObservableList<String> items = FXCollections.observableArrayList(flagTitles);
        cbo.getItems().addAll(items); // Metoda getItems() zwraca listę z pola kombi, metoda addAll dodaje do listy wiele elementów
        pane.setCenter(descriptionPane);

        // Display the selected country
        cbo.setOnAction(e -> {
            setDisplay(items.indexOf(cbo.getValue()));
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 450, 170);
        primaryStage.setTitle("ComboBoxDemo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Set display information on the description pane */
    public void setDisplay(int index) {
        descriptionPane.setTitle(flagTitles[index]);
        descriptionPane.setImageView(flagImage[index]);
        descriptionPane.setDescription(flagDescription[index]);
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
