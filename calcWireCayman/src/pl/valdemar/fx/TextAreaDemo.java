package pl.valdemar.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Klasa TextArea umożliwia wprowadzanie wielu wierszy tekstu.
 * Jeśli chcesz umożliwić wprowadzenie wielu wierszy tekstu, możesz utworzyć kilka obiektów typu TextField.
 * Jednak lepszym rozwiązaniem jest użycie klasy TextArea, która pozwala wpisać wielowierszowy tekst.
 *
 * TextArea dziedziczy z klasy TextInputControl. Klasa TextInputControl ma pola:
 *  -text: StringProperty - tekstowa zawartość komórki
 *  -editable: BooleanProperty
 *
 * TextArea ma pola:
 *  -prefColumnCount: IntegerProperty - określa preferowaną liczbę kolumn
 *  -prefRowColumn: IntegerProperty - określa preferowaną liczbę wierszy
 *
 *  Konstruktor:
 *      +TextArea() - tworzy pusty obszar tekstowy
 *      +TextArea(text: String) - tworzy obszar tekstowy z określonym tekstem
 *
 *
 * TextArea taNote = new TextArea("To obszar tekstowy");
 * taNote.setPrefColumnCount(20);
 * taNote.setPrefRowCount(5);
 *
 * Klasa TextArea umożliwia przewijanie tekstu, ale często przydatne jest utworzenie panelu ScrollPane, aby zawierał
 * obiekt typu TextArea i obsługiwał jego przewijanie:
 * // Tworzenie panelu ScrollPane do przechowywania obszaru tekstowego
 *
 * ScrollPane scrollPane = new ScrollPane(taNote);
 *
 * Wskazówka
 * W panelu ScrollPane można umieścić dowolny węzeł. Ten panel automatycznie obsługuje przewijanie w pionie i poziomie,
 * jeśli węzeł jest zbyt duży, aby zmieścił się w widocznym obszarze.
 *
 * Klasa TextAreaDemo używa klasy DescriptioPane do wyświetlenia obrazu, podpisu i opisu flagi narodowej
 *
 *
 *
 **/
public class TextAreaDemo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Declare and create a desription pane
        DescriptionPane descriptionPane = new DescriptionPane();

        // Set title, text and image in the description pane
        descriptionPane.setTitle("Canada");
        String description = "The Canadian national flag ...";
        descriptionPane.setImageView(new ImageView("ca.gif"));
        descriptionPane.setDescription(description);

        // Create a scene and place it in the stage
        Scene scene = new Scene(descriptionPane, 450, 200);
        primaryStage.setTitle("TextAreaDemo");
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
