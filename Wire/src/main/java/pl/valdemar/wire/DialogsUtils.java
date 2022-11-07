package pl.valdemar.wire;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class DialogsUtils {

    public static List<Path> openFileChooser(Stage stage) {
        FileChooser.ExtensionFilter ex = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();

        fileChooser.setTitle("Open Cayman Files");
        fileChooser.setInitialDirectory(new File("/home/waldemar/Projects/Cayman/HARDTOPP"));
        fileChooser.getExtensionFilters().addAll(ex);

        System.out.println("Multi Open File");
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        if (files == null) return null;
        return files.stream()
                .map(File::toPath)
                .collect(Collectors.toList());
    }

     public static void errorDialog(String error) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error!");
         alert.setHeaderText("Something went wrong");
         alert.setContentText(error);
         alert.showAndWait();
     }

     public static Optional<ButtonType>  confirmationDialog(String message) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("Please Confirm");
         alert.setContentText(message);

         Optional<ButtonType> result = alert.showAndWait();
         return result;
     }

    public static void dialogAboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle("About Application");
        informationAlert.setHeaderText("Wire - Version 1.0");
        informationAlert.setContentText("Application calculates length of wires.\n" +
                "Input: Text files created from CAYMAN software.\n" +
                "CAYMAN is the software solution for Schleuniger's automatic cut & strip machines.");
        informationAlert.showAndWait();
    }

}
