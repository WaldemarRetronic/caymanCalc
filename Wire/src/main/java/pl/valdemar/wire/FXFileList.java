package pl.valdemar.wire;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.nio.file.Path;

public class FXFileList extends VBox {

    private final ListView<Path> selectedFiles;
    private final StackPane header;
    private final Label label;

    private FXFileList() {
        label = new Label("List of files to be processed");
        label.getStyleClass().add("header-label");
        header = new StackPane(label);
        header.getStyleClass().add("header");
        selectedFiles = new ListView<>();
        getChildren().addAll(header, selectedFiles);
        setPadding(new Insets(10));
        this.setPrefWidth(500);
        VBox.setVgrow(selectedFiles, Priority.ALWAYS);
    }

    public static FXFileList createFXFileList() {
        return new FXFileList();
    }

    public ListView<Path> getSelectedFiles() {
        return selectedFiles;
    }
}
