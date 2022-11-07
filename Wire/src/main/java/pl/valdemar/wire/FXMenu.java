package pl.valdemar.wire;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.FileInputStream;

public class FXMenu extends HBox {

    private MenuBar menuBar;
    private Menu fileMenu;
    private MenuItem itemOpen;
    private MenuItem itemAdd;
    private MenuItem itemSave;
    private MenuItem itemExit;
    private Menu runMenu;
    private MenuItem itemRun;
    private Menu helpMenu;
    private MenuItem itemAbout;

    public FXMenu() {
        buildUI();
    }

    private void buildUI() {
        menuBar = new MenuBar();

        fileMenu = new Menu("File");

        itemOpen = new MenuItem("Open");
        Image imageOpen = new Image("open_small.png");
        itemOpen.setGraphic(new ImageView(imageOpen));
        itemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        itemAdd = new MenuItem("Add");
        Image imageAdd = new Image("add_small.png");
        itemAdd.setGraphic(new ImageView(imageAdd));
        itemAdd.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));


        itemSave = new MenuItem("Save");
        Image imageSave = new Image("save_small.png");
        itemSave.setGraphic(new ImageView(imageSave));
        itemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        itemSave.setDisable(true);


        SeparatorMenuItem sep = new SeparatorMenuItem();

        itemExit = new MenuItem("Exit");
        Image imageExit = new Image("exit_small.png");
        itemExit.setGraphic(new ImageView(imageExit));
        itemExit.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));


        fileMenu.getItems().addAll(itemOpen, itemAdd, itemSave, sep, itemExit);

        runMenu = new Menu("Run");

        itemRun = new MenuItem("Run");
        Image imageRun = new Image("run_small.png");
        itemRun.setGraphic(new ImageView(imageRun));
        itemRun.setDisable(true);
        runMenu.getItems().add(itemRun);

        helpMenu = new Menu("Help");
        itemAbout = new MenuItem("About");
        helpMenu.getItems().add(itemAbout);

        menuBar.getMenus().addAll(fileMenu, runMenu, helpMenu);
        getChildren().add(menuBar);
        HBox.setHgrow(menuBar, Priority.ALWAYS);

    }

    public MenuItem getItemSave() {
        return itemSave;
    }

    public MenuItem getItemOpen() {
        return itemOpen;
    }

    public MenuItem getItemAbout() {
        return itemAbout;
    }

    public MenuItem getItemRun() {
        return itemRun;
    }

    public MenuItem getItemExit() {
        return itemExit;
    }

    public MenuItem getItemAdd() { return itemAdd; }
}
