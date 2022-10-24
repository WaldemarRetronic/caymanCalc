package pl.valdemar.fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Pole tekstowe umożliwia wprowadzanie lub wyświetlanie łańcuchów znaków.
 * TextField to podklasa klasy TextInputControl.
 *
 * Konstruktory:
 *  TextField() - Tworzy puste pole tekstowe
 *  TextField(text: String) - Tworzy pole tekstowe z określonym tekstem
 *
 * Jeśli pole tekstowe ma służyć do wpisywania haseł, użyj klasy PasswordField zamiast TextField. PasswordField rozszerza
 * klasę TextField i ukrywa wprowadzany tekst za pomocą gwiazdek: ******.
 */

public class TextFieldDemo extends RadioButtonDemo {
    @Override
    protected BorderPane getPane() {
        BorderPane pane = super.getPane();

        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5,5 ));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter a new message: "));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);
        pane.setTop(paneForTextField);

        // Gdy umieścisz kursor w polu tekstowym i wciśniesz klawisz Enter, pole zgłosi zdarzenie ActionEvent.
        tf.setOnAction(e -> {
            text.setText(tf.getText());
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
