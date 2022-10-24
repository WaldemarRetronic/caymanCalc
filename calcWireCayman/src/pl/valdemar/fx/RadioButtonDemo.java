package pl.valdemar.fx;

/**
 * Przyciski opcji (nazywane też przyciskami radio) umożliwiają wybór jednego elementu z ich grupy.
 * Wyglądem przyciski opcji przypominają pola wyboru. Jednak pole wyboru ma kształt kwadratu, który jest zaznaczony lub
 * pusty, natomiast przyciski opcji mają postać kółka, które jest wypełnione (zaznaczenie) lub puste (brak zaznaczenia).
 * RadioButton jest podklasą klasy ToggleButton. Różnica między tymi klasami polega na tym, że RadioButton wyświetla kółko,
 * a ToggleButton wygląda podobnie jak przycisk
 *
 * Konstruktory:
 *  ToggleButton() - Tworzy pusty przycisk ToggleButton
 *  ToggleButton(text: String) - Tworzy przycisk ToggleButton z podanym tekstem
 *  ToggleButton(text: String, graphic: Node) - Tworzyprzycisk ToggleButton z podanym tekstem i grafiką
 *
 *  RadioButton() - Tworzy pusty przycisk RadioButton
 *  RadioButton(text: String) - Tworzy przycisk RadioButton z podanym tekstem
 *
 */

/**
 * Oto przykładowy wstępnie zaznaczony przycisk opcji z zielonym tekstem USA, grafiką i czarnym obramowaniem.
 * RadioButton rbUS = new RadioButton("USA");
 * rbUS.setGraphic(new ImageView("image/usIcon.gif"));
 * rbUS.setTextFill(Color.GREEN);
 * rbUS.setContentDisplay(ContentDisplay.LEFT);
 * rbUS.setStyle("-fx-border-color: black");
 * rbUS.setSelected(true);
 * rbUS.setPadding(new Insets(5, 5, 5, 5));
 *
 * Aby pogrupować przyciski radio, trzeba utworzyć instancję klasy ToggleGroup i ustawić wartość właściwości toggleGroup
 * przycisku, dołączając go do danej grupy:
 * ToggleGroup group = new ToggleGroup();
 * rbRed.setToggleGroup(group);
 * rbGreen.setToggleGroup(group);
 * rbBlue.setToggleGroup(group);
 * Ten kod tworzy grupę z przyciskami opcji rbRed, rbGreen i rbBlue. Można zaznaczyć tylko jeden z nich.
 * Bez grupy przyciski można zaznaczać niezależnie od siebie.
 * Zaznaczenie lub usunięcie zaznaczenia przycisku opcji skutkuje zdarzeniem ActionEvent. Aby sprawdzić, czy przycisk
 * jest zaznaczony, użyj metody isSelected().
 *
 */

import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Ten program można napisać na przynajmniej dwa sposoby. Pierwszy polega na zmodyfikowaniu wcześniejszej klasy
 * CheckBoxDemo i wstawieniu w niej kodu trzech przycisków opcji oraz przetwarzającego ich zdarzenia.
 * Drugie podejście to zdefiniowanie podklasy klasy CheckBoxDemo. Tutaj zastosowano drugie z tych rozwiązań.
 *
 * RadioButtonDemo rozszerza klasę CheckBoxDemo i przesłania metodę getPane() (wiersz 10.). Nowa wersja tej metody wywołuje
 * metodę getPane() z klasy CheckBoxDemo, co pozwala uzyskać panel BorderPane zawierający pola wyboru, przyciski i tekst.
 * Ten panel jest zwracany przez wywołanie super.getPane().
 *
 * Metoda start w tym programie opartym na JavaFX jest zdefiniowana w klasie ButtonDemo i dziedziczona w klasie CheckBoxDemo,
 * a następnie w RadioButtonDemo. Dlatego gdy uruchomisz klasę RadioButtonDemo, wywołana zostanie metoda start z klasy
 * ButtonDemo. Ponieważ metoda getPane() jest przesłonięta w klasie RadioButtonDemo, w nowym programie  w metodzie start()
 * z klasy ButtonDemo.java (wiersz 71 w klasie ButtonDemo.java) użyta zostanie metoda z klasy RadioButtonDemo.
 *
 */

public class RadioButtonDemo extends CheckBoxDemo {
    @Override
    protected BorderPane getPane() {
        BorderPane pane = super.getPane();

        VBox paneForRadioButtons = new VBox(20);
        paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));
        paneForRadioButtons.setStyle("-fx-border-width: 2px; -fx-border-color: green");

        RadioButton rbRed = new RadioButton("Czerwony");
        RadioButton rbGreen = new RadioButton("Zielony");
        RadioButton rbBlue = new RadioButton("Niebieski");
        paneForRadioButtons.getChildren().addAll(rbRed, rbGreen, rbBlue);
        pane.setLeft(paneForRadioButtons);

        ToggleGroup group = new ToggleGroup();
        rbRed.setToggleGroup(group);
        rbGreen.setToggleGroup(group);
        rbBlue.setToggleGroup(group);

        rbRed.setOnAction(e -> {
            if (rbRed.isSelected()) {
                text.setFill(Color.RED);
            }
        });

        rbGreen.setOnAction(e -> {
            if (rbGreen.isSelected()) {
                text.setFill(Color.GREEN);
            }
        });

        rbBlue.setOnAction(e -> {
            if (rbBlue.isSelected()) {
                text.setFill(Color.BLUE);
            }
        });

        return pane;
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
