package pl.valdemar.wire.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.valdemar.wire.*;
import pl.valdemar.wire.dialogs.DialogsUtils;
import pl.valdemar.wire.model.Result;
import pl.valdemar.wire.model.Wire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AppController extends Application {
    private Stage stage;

    private FXFileList fxFileList;
    private FXMenu fxMenu;
    private FXToolBar fxToolBar;
    private FXTable fxTable;
    private FXStatusBar fxStatusBar;


    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        BorderPane root = new BorderPane();

        fxMenu = FXMenu.createFXMenu();
        fxToolBar = FXToolBar.createFXToolBar();

        VBox top = new VBox(fxMenu, fxToolBar);
        handleOpenBtn();
        handleAddBtn();
        handleRunBtn();
        handleSaveBtn();
        handleExitBtn();
        handleAboutItem();

        root.setTop(top);

        fxFileList = FXFileList.createFXFileList();
        root.setLeft(fxFileList);

        fxTable = FXTable.createFXTable();
        root.setCenter(fxTable);

        fxStatusBar = FXStatusBar.createFXStatusBar();
        root.setBottom(fxStatusBar);

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add("stylesheet.css");
        stage.setTitle("Cayman Length Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //========== private handler methods==========

    private void handleOpenBtn() {
        EventHandler<ActionEvent> event = actionEvent -> {
            List<Path> paths = DialogsUtils.fileChooserOpen(stage);
            if (paths != null) {
                fxFileList.getSelectedFiles().setItems(FXCollections.observableList(paths));
                fxTable.removeResult();
                fxMenu.getItemSave().setDisable(true);
                fxToolBar.getBtnSave().setDisable(true);

                fxMenu.getItemRun().setDisable(false);
                fxToolBar.getBtnRun().setDisable(false);
                fxStatusBar.setText("Ready");
            }
        };

        fxMenu.getItemOpen().setOnAction(event);
        fxToolBar.getBtnOpen().setOnAction(event);
    }

    private void handleAddBtn() {
        EventHandler<ActionEvent> event = actionEvent -> {
            List<Path> paths = DialogsUtils.fileChooserOpen(stage);
            if (paths != null) {
                fxFileList.getSelectedFiles().getItems().addAll(FXCollections.observableList(paths));
                fxTable.removeResult();
                fxMenu.getItemSave().setDisable(true);
                fxToolBar.getBtnSave().setDisable(true);

                fxMenu.getItemRun().setDisable(false);
                fxToolBar.getBtnRun().setDisable(false);
                fxStatusBar.setText("Ready");
            }
        };

        fxToolBar.getBtnAdd().setOnAction(event);
        fxMenu.getItemAdd().setOnAction(event);
    }

    private void handleRunBtn() {
        EventHandler<ActionEvent> event = eventAction -> {

            List<CompletableFuture<List<Wire>>> completableFutureList = new ArrayList<>();
            for (Path path : fxFileList.getSelectedFiles().getItems()) {
                fxTable.removeResult();
                completableFutureList.add(CompletableFuture.completedFuture(path)
                        .thenCompose(this::loadWires));
            }

            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                    .exceptionally(this::reportErrors)
                    .thenRun(() -> processResults(completableFutureList));
        };

        fxToolBar.getBtnRun().setOnAction(event);
        fxMenu.getItemRun().setOnAction(event);
    }


    private void handleSaveBtn() {
        EventHandler<ActionEvent> event = actionEvent -> {
            Path path = DialogsUtils.fileChooserSave(stage);
            if (path != null) {
                try {
                    storeResult(path);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            fxStatusBar.setText("Saved");
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

    //======= private business methods ==========

    private void storeResult(Path path) throws IOException {

        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Result item : fxTable.getTable().getItems()) {
                bw.write(String.format("%s,%s",
                        item.getTypeCode().replace(",", "."),
                        item.getLength()));
                bw.newLine();
            }
        }
    }

    public CompletableFuture<List<Wire>> loadWires(Path path) {

        return CompletableFuture.supplyAsync(() -> {
            List<Wire> wireList = new ArrayList<>();
            Wire wire;
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String input;
                br.readLine(); // skipping first line
                while ((input = br.readLine()) != null) {
                    String[] itemPieces = input.split("\t");
                    wire = Wire.of(itemPieces[9], itemPieces[10], Double.parseDouble(itemPieces[3]));
                    wireList.add(wire);
                }
                return wireList;
            } catch (IOException ex) {
                throw new RuntimeException("File: " + path + " is corrupted or doesn't exist");
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("File: " + path + " has wrong data format");
            }
        });
    }

    private void processResults(List<CompletableFuture<List<Wire>>> futures) {
        List<Wire> wireList = new ArrayList<>();
        futures
                .forEach(future -> future.thenAccept(wireList::addAll));

        Function<Wire, String> byRawMaterial = Wire::getRawMaterial;
        Function<Wire, String> byProcessing = Wire::getProcessing;

        Map<String, List<Double>> groupedByRawMaterial = wireList.stream()
                .collect(Collectors.groupingBy(byRawMaterial,
                        Collectors.mapping(Wire::getLength, Collectors.toList())));

        Map<String, List<Double>> groupedByProcessing = wireList.stream()
                .collect(Collectors.groupingBy(byProcessing,
                        Collectors.mapping(Wire::getLength, Collectors.toList())));

        writeToTable(groupedByProcessing);
        writeToTable(groupedByRawMaterial);
        fxMenu.getItemSave().setDisable(false);
        fxToolBar.getBtnSave().setDisable(false);
        Platform.runLater(() -> fxStatusBar.setText("Processed -- Not Saved"));
    }

    private void writeToTable(Map<String, List<Double>> groupedBy) {
        for (Map.Entry<String, List<Double>> item : groupedBy.entrySet()) {
            Double totalLength =
                    item.getValue()
                            .stream()
                            .reduce(0.0, Double::sum);
            Result result = Result.of(item.getKey(), totalLength);
            fxTable.addResult(result);
        }
    }

    private Void reportErrors(Throwable throwable) {
        String[] message = throwable.getMessage().split(" ", 2);
        Platform.runLater(() -> DialogsUtils.errorDialog(message[1]));
        throw new RuntimeException("terminate"); // in case to be handled if the next exceptionally block will call under chain.
    }

}
