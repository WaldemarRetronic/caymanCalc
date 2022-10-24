package pl.valdemar.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Klasa CheckBox umożliwia użytkownikowi dokonanie wyboru.
 * Klasa CheckBox (podobnie jak Button) dziedziczy wszystkie właściwości takie jak onAction, text, graphic, alignment, g
 * raphicTextGap, textFill i contentDisplay po klasach ButtonBase i Labeled. Ponadto udostępnia właściwość selected
 * informującą, czy pole jest zaznaczone.
 * Oto wstępnie zaznaczone pole wyboru z zielonym tekstem USA, grafiką i czarnym obramowaniem.
 *   CheckBox chkUS = new CheckBox("USA");
 *   chkUS.setGraphic(new ImageView("image/usIcon.gif"));
 *   chkUS.setTextFill(Color.GREEN);
 *   chkUS.setContentDisplay(ContentDisplay.LEFT);
 *   chkUS.setStyle("-fx-border-color: black");
 *   chkUS.setSelected(true);
 *   chkUS.setPadding(new Insets(5, 5, 5, 5));
 */

/**
 * Metoda start tego programu opartego na JavaFX jest zdefiniowana w klasie ButtonDemo i dziedziczona w klasie CheckBoxDemo.
 * Dlatego gdy uruchomisz klasę CheckBoxDemo, wywołana zostanie metoda start z klasy ButtonDemo. Metoda getPane() jest
 * przesłonięta w klasie CheckBoxDemo, użyta zostanie teraz wersja z klasy CheckBoxDemo.
 * TEN MECHANIZM DZIAŁANIA DEMONSTRUJE KLASA TEST
 */

public class CheckBoxDemo extends ButtonDemo {
    @Override // Override the getPane() method in the super class
    protected BorderPane getPane() {
        BorderPane pane = super.getPane();


        /**
         * Font font1 = new Font("SansSerif", 16);
         * Font font2 = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 12);
         */
        Font fontBoldItalic = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20);
        Font fontBold = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 20);
        Font fontItalic = Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.ITALIC, 20);
        Font fontNormal = Font.font("Times new Roman", FontWeight.NORMAL, FontPosture.REGULAR, 20);

        text.setFont(fontNormal);
        VBox paneForCheckBoxes = new VBox(20);
        paneForCheckBoxes.setPadding(new Insets(5, 5, 5, 5));
        paneForCheckBoxes.setStyle("-fx-border-color: green");
        CheckBox chkBold = new CheckBox("Bold");
        CheckBox chkItalic = new CheckBox("Italic");
        paneForCheckBoxes.getChildren().addAll(chkBold, chkItalic);
        pane.setRight(paneForCheckBoxes);

        EventHandler<ActionEvent> handler = e -> {
            if (chkBold.isSelected() && chkItalic.isSelected()) {
                text.setFont(fontBoldItalic); // Both check boxes checked
            }
            else if (chkBold.isSelected()) {
                text.setFont(fontBold); // The Bold check box checked
            }
            else {
                text.setFont(fontNormal);
            }
        };

        chkBold.setOnAction(handler);
        chkItalic.setOnAction(handler);

        return pane; // Return a new pane
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
