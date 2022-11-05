package pl.valdemar.wire;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by ZacznijProgramowac.
 * https://www.youtube.com/zacznijprogramowac
 * http://zacznijprogramowac.net/
 */
public class DialogsUtils {

    public static void errorDialog(String error) {
        System.out.println("In errorDialog");
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        System.out.println("po");
        errorAlert.setTitle("Error!");
        errorAlert.setHeaderText("Something went wrong");
        System.out.println("before text area");

        TextArea textArea = new TextArea(error);
        errorAlert.getDialogPane().setContent(textArea);
        System.out.println("before show and wait");
        errorAlert.showAndWait();
    }

}
