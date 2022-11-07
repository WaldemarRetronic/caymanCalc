package pl.valdemar.wire;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public class App extends Application {
    private Stage stage;
    private BorderPane root;
    private Scene scene;
    private ListView<Path> selectedFiles;
    private String filename;

    private FXMenu fxMenu;
    private FXToolBar fxToolBar;
    private FXTable fxTable;
    private FXStatusBar fxStatusBar;


    @Override
    public void start(Stage stage) throws IOException {
//         FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app-view.fxml"));
//         scene = new Scene(fxmlLoader.load(), 320, 240);
//         stage.setTitle("Hello!");
//         stage.setScene(scene);
//         stage.show();

        root = new BorderPane();

        fxMenu = new FXMenu();
        fxToolBar = new FXToolBar();

        VBox top = new VBox(fxMenu, fxToolBar);
        handleOpenBtn();
        handleAddBtn();
        handleRunBtn();
        handleSaveBtn();
        handleExitBtn();
        handleAboutItem();

        root.setTop(top);

        selectedFiles = new ListView<>();
        root.setLeft(selectedFiles);

        fxTable = new FXTable();
        root.setCenter(fxTable);

        fxStatusBar = new FXStatusBar();
        root.setBottom(fxStatusBar);

        scene = new Scene(root, 800, 500);
        scene.getStylesheets().add("stylesheet.css");
        stage.setTitle("Cayman Length Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void handleOpenBtn() {
        EventHandler<ActionEvent> event = actionEvent -> {
            List<Path> paths = DialogsUtils.openFileChooser(stage);
            if (paths != null) {
                selectedFiles.setItems(FXCollections.observableList(paths));
                fxTable.removeResult();
                fxMenu.getItemSave().setDisable(true);
                fxToolBar.getBtnSave().setDisable(true);

                fxMenu.getItemRun().setDisable(false);
                fxToolBar.getBtnRun().setDisable(false);
            }
        };

        fxMenu.getItemOpen().setOnAction(event);
        fxToolBar.getBtnOpen().setOnAction(event);
    }

    private void handleAddBtn() {
        EventHandler<ActionEvent> event = actionEvent -> {
            List<Path> paths = DialogsUtils.openFileChooser(stage);
            if (paths != null) {
                selectedFiles.getItems().addAll(FXCollections.observableList(paths));
                fxTable.removeResult();
                fxMenu.getItemSave().setDisable(true);
                fxToolBar.getBtnSave().setDisable(true);

                fxMenu.getItemRun().setDisable(false);
                fxToolBar.getBtnRun().setDisable(false);
            }
        };

        fxToolBar.getBtnAdd().setOnAction(event);
        fxMenu.getItemAdd().setOnAction(event);
    }

    private void handleRunBtn() {
        EventHandler<ActionEvent> event = eventAction -> {

            List<CompletableFuture<List<Wire>>> completableFutureList = new ArrayList<>();
            for (Path path : selectedFiles.getItems()) {
                completableFutureList.add(CompletableFuture.completedFuture(path)
                        .thenCompose(this::loadWires));
            }

            System.out.println("running thread: " + Thread.currentThread());
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                    .exceptionally(this::report)
                    .thenRun(() -> processResults(completableFutureList));
        };

        fxToolBar.getBtnRun().setOnAction(event);
        fxMenu.getItemRun().setOnAction(event);
    }


    private void handleSaveBtn() {
        EventHandler<ActionEvent> event = actionEvent -> {
            Dialog<String> filenameDialog = new FilenameDialog("result");
            Optional<String> result = filenameDialog.showAndWait();
            if (result.isPresent()) {
                try {
                    storeResult(result.get());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        };

        fxToolBar.getBtnSave().setOnAction(event);
        fxMenu.getItemSave().setOnAction(event);
    }

    private void handleExitBtn() {
        EventHandler<ActionEvent> event = actionEvent -> {
            Platform.exit();
        };

        fxToolBar.getBtnExit().setOnAction(event);
        fxMenu.getItemExit().setOnAction(event);
    }

    private void handleAboutItem() {
        fxMenu.getItemAbout().setOnAction(event -> {
            DialogsUtils.dialogAboutApplication();
        });
    }

    private void storeResult(String filename) throws IOException {
        Path path = Paths.get(filename);
        if (Files.exists(path)) {
            Optional<ButtonType> result = DialogsUtils.confirmationDialog("File already exists.\nDo You want to overwrite?");
            if (!(result.isPresent() && result.get() == ButtonType.OK)) {
                return;
            }
        }
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Result item : fxTable.getTable().getItems()) {
                bw.write(String.format("%s,%s",
                        item.getTypeCode().replace(",", "."),
                        item.getLength()));
                bw.newLine();
            }
        }
    }

    //----------------------Completable Future-----------------------------
    public CompletableFuture<List<Wire>> loadWires(Path path) {

        return CompletableFuture.supplyAsync(() -> {
            System.out.println("running thread -- " + Thread.currentThread());
            List<Wire> wireList = new ArrayList<>();
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String input;
                br.readLine();
                while ((input = br.readLine()) != null) {
                    String[] itemPieces = input.split("\t");
                    Wire wire = new Wire(itemPieces[9], itemPieces[10], Double.parseDouble(itemPieces[3]));
                    wireList.add(wire);
                    System.out.println("path " + path);
                }
                return wireList;
            } catch (IOException ex) {
                throw new RuntimeException("File: " + path + " is corrupted or doesn't exist");
            } catch (NumberFormatException ex) {
                throw new RuntimeException("File: " + path + " has wrong data format");
            }
        });
    }

    private void processResults(List<CompletableFuture<List<Wire>>> futures) {
        List<Wire> wireList = new ArrayList<>();
        futures
                .forEach(future -> future.thenAccept(wireList::addAll));
        System.out.println(wireList);

        Function<Wire, String> byColourTypeCode = Wire::getColourTypeCode;
        Function<Wire, String> byTypeCode = Wire::getTypeCode;

        Map<String, List<Double>> groupedByColourTypeCode = wireList.stream()
                .collect(Collectors.groupingBy(byColourTypeCode,
                        Collectors.mapping(Wire::getLength, Collectors.toList())));

        Map<String, List<Double>> groupedByTypeCode = wireList.stream()
                .collect(Collectors.groupingBy(byTypeCode,
                        Collectors.mapping(Wire::getLength, Collectors.toList())));

        writeToTable(groupedByTypeCode);
        writeToTable(groupedByColourTypeCode);
        fxMenu.getItemSave().setDisable(false);
        fxToolBar.getBtnSave().setDisable(false);
    }

    private void writeToTable(Map<String, List<Double>> groupedBy) {
        for (Map.Entry<String, List<Double>> item : groupedBy.entrySet()) {
            Double totalLength =
                    item.getValue()
                            .stream()
                            .reduce(0.0, Double::sum);
            Result result = new Result(item.getKey(), totalLength);
            fxTable.addResult(result);
        }
    }

    private Void report(Throwable throwable) {
        String[] message = throwable.getMessage().split(" ", 2);
        Platform.runLater(() -> DialogsUtils.errorDialog(message[1]));
        throw new RuntimeException("terminate"); // in case to be handled if the next exceptionally block will call under chain.
    }

}
