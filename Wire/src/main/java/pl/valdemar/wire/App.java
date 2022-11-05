package pl.valdemar.wire;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
        if (files == null) return null;
        return files.stream()
                .map(File::toPath)
                .collect(Collectors.toList());
    }

    private void handleOpenBtn() {
        fxToolBar.getBtnOpen().setOnAction(event -> {
            List<Path> paths = openFileChooser();
            if (paths != null) {
                selectedFiles.setItems(FXCollections.observableList(paths));
            }
        });
    }

    private void handleAddBtn() {
        fxToolBar.getBtnAdd().setOnAction(event -> {
            List<Path> paths = openFileChooser();
            if (paths != null) {
                selectedFiles.getItems().addAll(FXCollections.observableList(paths));
            }
        });
    }

    private void handleRunBtn() {
        fxToolBar.getBtnRun().setOnAction(event -> {

            List<CompletableFuture<List<Wire>>> completableFutureList = new ArrayList<>();
            for (Path path : selectedFiles.getItems()) {
                completableFutureList.add(CompletableFuture.completedFuture(path)
                        .thenCompose(this::loadWires));
            }

            System.out.println("running thread: " + Thread.currentThread());
            // sleep(10000);
            // getting values from cfs
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                    .exceptionally(this::report)
                    .thenRun(() -> processResults(completableFutureList));
            //System.out.println(voidCompletableFuture.isDone());
        });
    }


    private void handleSaveBtn() {
        fxToolBar.getBtnSave().setOnAction(event -> {
            System.out.println("Clicked save button");
            Dialog<String> filenameDialog = new FilenameDialog("result");
            Optional<String> result = filenameDialog.showAndWait();
            if (result.isPresent()) {
                System.out.println("Storing result");
                try {
                    storeResult(result.get());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void handleExitBtn() {
        fxToolBar.getBtnExit().setOnAction(event -> {
            Platform.exit();
        });
    }

    private void storeResult(String filename) throws IOException {
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
                throw new ApplicationException("File: " + path + " is corrupted or doesn't exist");
            } catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
                throw new ApplicationException("File: " + path + " has wrong data format");
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

    private Void report(Throwable throwable) {
        System.out.println("ERROR: " + throwable.getMessage());
        System.out.println(Thread.currentThread().getName());
//        Alert alert = new Alert(AlertType.CONFIRMATION);
        System.out.println(Thread.currentThread().getName());
        Platform.runLater(() -> {
            System.out.println(Thread.currentThread());
            Alert errorAlert = new Alert(AlertType.WARNING);
            errorAlert.setTitle("Error!");
            errorAlert.setHeaderText("Something went wrong");
            System.out.println("before text area");

            TextArea textArea = new TextArea(throwable.getMessage());
            errorAlert.getDialogPane().setContent(textArea);
            System.out.println("before show and wait");
            errorAlert.showAndWait();
        });
//
//        alert.setTitle("Please Confirm");
//        alert.setHeaderText("Please consider Subscribing");
//        alert.setContentText("Please Subscribe so that you will be notified when I release new videos");
//        alert.initOwner(stage);
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            System.out.println("OK Button Clicked");
//        }
//
//        System.out.println("In errorDialog");
//        Alert errorAlert = new Alert(AlertType.WARNING);
//        errorAlert.setTitle("Error!");
//        errorAlert.setHeaderText("Something went wrong");
//        System.out.println("before text area");
//
//        TextArea textArea = new TextArea(throwable.getMessage());
//        errorAlert.getDialogPane().setContent(textArea);
//        System.out.println("before show and wait");
//        errorAlert.showAndWait();


       // DialogsUtils.errorDialog(throwable.getMessage());
        System.out.println("After error dialog");
        throw new RuntimeException("terminate"); // in case to be handled if the next exceptionally block will call under chain.
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
