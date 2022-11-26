package pl.valdemar.wire;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class FXToolBar extends HBox {

	private Button btnOpen;
	private Button btnAdd;
	private Button btnRun;
	private Button btnSave;
	private Button btnExit;
	private final ContentDisplay CONTENT_DISPLAY = ContentDisplay.GRAPHIC_ONLY;

	private FXToolBar() {
		buildUI();
	}

	public static FXToolBar createFXToolBar() {
		return new FXToolBar();
	}

	private void buildUI() {
		btnOpen = createButton("Open", "open.png");
		btnOpen.setContentDisplay(CONTENT_DISPLAY);

		btnAdd = createButton("Add", "add.png");
		btnAdd.setContentDisplay(CONTENT_DISPLAY);

		btnRun = createButton("Run", "run.png");
		btnRun.setContentDisplay(CONTENT_DISPLAY);
		btnRun.setDisable(true);

		btnSave = createButton("Save", "save.png");
		btnSave.setContentDisplay(CONTENT_DISPLAY);
		btnSave.setDisable(true);

		btnExit = createButton("Exit", "exit.png");
		btnExit.setContentDisplay(CONTENT_DISPLAY);

		ToolBar toolBar = new ToolBar(btnOpen, btnAdd, btnRun, btnSave, btnExit);
		getChildren().add(toolBar);
		HBox.setHgrow(toolBar, Priority.ALWAYS);
	}

	public Button createButton(String text, String imageFile) {
		Image image = new Image(imageFile);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(32);
		imageView.setFitWidth(32);
		return new Button(text, imageView);
	}

	public Button getBtnOpen() {
		return btnOpen;
	}

	public Button getBtnSave() {
		return btnSave;
	}

	public Button getBtnExit() {
		return btnExit;
	}

	public Button getBtnAdd() {
		return btnAdd;
	}

	public Button getBtnRun() {
		return btnRun;
	}
}
