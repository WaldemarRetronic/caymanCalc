package pl.valdemar.wire.dialogs;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.stage.FileChooser.ExtensionFilter;

public class DialogsUtils {

    private static final ExtensionFilter extOpen = new ExtensionFilter("Text Files", "*.txt");
    private static final ExtensionFilter extSave = new ExtensionFilter("CSV Files", "*.csv");

    private static final File initialDir = new File("/home/waldemar/Projects/Cayman/HARDTOPP");

    private static FileChooser getFileChooser(String title, ExtensionFilter ext) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(initialDir);
        fileChooser.getExtensionFilters().add(ext);
        return fileChooser;
    }

    public static List<Path> fileChooserOpen(Stage stage) {
        List<File> files = getFileChooser("Open Cayman Files", extOpen).showOpenMultipleDialog(stage);
        if (files == null) return null;
        return files.stream()
                .map(File::toPath)
                .collect(Collectors.toList());
    }

    public static Path fileChooserSave(Stage stage) {
        File file = getFileChooser("Save File", extSave).showSaveDialog(stage);
        if (file == null) return null;
        return file.toPath();
    }

     public static void errorDialog(String error) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error!");
         alert.setHeaderText("Something went wrong");
         alert.setContentText(error);
         alert.showAndWait();
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
