package pl.valdemar.wire;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class FilenameDialog extends Dialog<String> {

	private String filename;

	private TextField inputFilename;

	public FilenameDialog(String filename) {
		super();
		this.setTitle("Saving result");
		this.filename = filename;
		buildUI();
		setResultConverter();
	}

	private void buildUI() {
		Pane pane = createGridPane();
		getDialogPane().setContent(pane);
		getDialogPane().setHeaderText("Give file name:");
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		getDialogPane().expandableContentProperty().set(new Label("File's name shouldn't include extension.\n" +
				"Extension \".csv\" will be added automatically."));
		getDialogPane().setExpanded(true);

		Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
		button.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!validateDialog()) {
					event.consume();
				}
			}

			private boolean validateDialog() {
				if (inputFilename.getText().isEmpty()) {
					return false;
				}
				return true;
			}
		});
	}

	private void setResultConverter() {
		Callback<ButtonType, String> personResultConverter = new Callback<ButtonType, String>() {
			@Override
			public String call(ButtonType param) {
				if (param == ButtonType.OK) {
					return inputFilename.getText() + ".csv";
				} else {
					return null;
				}
			}
		};
		setResultConverter(personResultConverter);
	}

	public Pane createGridPane() {
		VBox content = new VBox(10);
		Label filenameLabel = new Label("File Name");
		this.inputFilename = new TextField(filename);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(5);
		grid.add(filenameLabel, 0, 0);
		grid.add(inputFilename, 1, 0);
		GridPane.setHgrow(this.inputFilename, Priority.ALWAYS);
		content.getChildren().add(grid);

		return content;
	}
}
