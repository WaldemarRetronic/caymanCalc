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

public class FXMenu extends HBox {

    private Menu fileMenu;
    private MenuItem itemOpen;
    private MenuItem itemAdd;
    private MenuItem itemSave;
    private MenuItem itemExit;
    private Menu runMenu;
    private MenuItem itemRun;
    private Menu helpMenu;
    private MenuItem itemAbout;

    private FXMenu() {
        MenuBar menuBar = new MenuBar();
        createFileMenu();
        createRunMenu();
        createHelpMenu();
        menuBar.getMenus().addAll(fileMenu, runMenu, helpMenu);
        getChildren().add(menuBar);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
    }

    public static FXMenu createFXMenu() {
        return new FXMenu();
    }

    private void createHelpMenu() {
        helpMenu = new Menu("Help");
        itemAbout = new MenuItem("About");
        helpMenu.getItems().add(itemAbout);
    }

    private void createRunMenu() {
        runMenu = new Menu("Run");

        itemRun = new MenuItem("Run");
        Image imageRun = new Image("run_small.png");
        itemRun.setGraphic(new ImageView(imageRun));
        itemRun.setDisable(true);
        runMenu.getItems().add(itemRun);
    }

    private void createFileMenu() {
        fileMenu = new Menu("File");

        itemOpen = createMenuItem("Open", "open_small.png", KeyCode.O);
        itemAdd = createMenuItem("Add", "add_small.png", KeyCode.A);
        itemSave = createMenuItem("Save", "save_small.png", KeyCode.S);
        itemSave.setDisable(true);

        SeparatorMenuItem sep = new SeparatorMenuItem();

        itemExit = createMenuItem("Exit", "exit_small.png", KeyCode.X);

        fileMenu.getItems().addAll(itemOpen, itemAdd, itemSave, sep, itemExit);
    }

    private MenuItem createMenuItem(String Open, String s, KeyCode o) {
        MenuItem item = new MenuItem(Open);
        Image imageOpen = new Image(s);
        item.setGraphic(new ImageView(imageOpen));
        item.setAccelerator(new KeyCodeCombination(o, KeyCombination.CONTROL_DOWN));
        return item;
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
