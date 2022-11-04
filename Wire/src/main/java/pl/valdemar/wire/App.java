package pl.valdemar.wire;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private String filename = "";

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

    private List<Path> openFileChooser() {
        FileChooser.ExtensionFilter ex = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();

        fileChooser.setTitle("Open Cayman Files");
        fileChooser.setInitialDirectory(new File("/home/waldemar/Projects/Cayman/HARDTOPP"));
        fileChooser.getExtensionFilters().addAll(ex);

        System.out.println("Multi Open File");
        List<File> files = fileChooser.showOpenMultipleDialog(stage);
        return files.stream()
                .map(file -> file.toPath())
                .collect(Collectors.toList());
    }

    private void handleOpenBtn() {
        fxToolBar.getBtnOpen().setOnAction(event -> {
            selectedFiles.setItems(FXCollections.observableList(openFileChooser()));
        });
    }

    private void handleAddBtn() {
        fxToolBar.getBtnAdd().setOnAction(event -> {
            selectedFiles.getItems().addAll(FXCollections.observableList(openFileChooser()));
        });
    }

    private void handleRunBtn() {
        fxToolBar.getBtnRun().setOnAction(event -> {
            List<CompletableFuture<List<Wire>>> completableFutureList = new ArrayList<>();
            for (Path path : selectedFiles.getItems()) {
                completableFutureList.add(CompletableFuture.completedFuture(path)
                        .thenComposeAsync(this::loadWires));
            }

            // getting values from cfs
            CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                    .thenRun(() -> processResults(completableFutureList));
        });
    }


    private void handleSaveBtn() {
        fxToolBar.getBtnSave().setOnAction(event -> {
            System.out.println("Clicked save button");
            TextInputDialog dialog = new TextInputDialog("caymanResult.csv");
            dialog.setTitle("Saving result");
            dialog.setHeaderText("Give file name:");
            dialog.initOwner(stage);
            dialog.getDialogPane().lookupButton(ButtonType.OK);
            Platform.runLater(() -> {
                Optional<String> response = dialog.showAndWait();
                filename = response.orElseGet(dialog::getDefaultValue);
                if (filename.isEmpty()) return;
                try {
                    System.out.println("storing");
                    storeResult();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    private void handleExitBtn() {
        fxToolBar.getBtnExit().setOnAction(event -> {
            Platform.exit();
        });
    }

    private void storeResult() throws IOException {
        Path path = Paths.get(filename);
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
            List<Wire> wireList = new ArrayList<>();
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String input;
                br.readLine();
                while ((input = br.readLine()) != null) {
                    String[] itemPieces = input.split("\t");
                    Wire wire = new Wire(itemPieces[9], itemPieces[10], Double.parseDouble(itemPieces[3]));
                    wireList.add(wire);
                }
                return wireList;
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        });
    }

    private void processResults(List<CompletableFuture<List<Wire>>> futures) {
        List<Wire> wireList = new ArrayList<>();
        futures.stream()
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

        fxTable.removeResult();
        writeToTable(groupedByTypeCode);
        writeToTable(groupedByColourTypeCode);
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
}
