package pl.valdemar.wire;

import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FXTable extends VBox {

	private TableView<Result> table;
	private TableColumn<Result, String> typeCode;
	private TableColumn<Result, Double> length;

	public FXTable() {
		table = new TableView<Result>();
		typeCode = new TableColumn<Result, String>("Type Code");
		typeCode.setCellValueFactory(new PropertyValueFactory<Result, String>("typeCode"));
		length = new TableColumn<Result, Double>("Length");
		length.setCellValueFactory(new PropertyValueFactory<Result, Double>("length"));
		table.getColumns().add(typeCode);
		table.getColumns().add(length);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		getChildren().add(table);
		setPadding(new Insets(10));
		VBox.setVgrow(table, Priority.ALWAYS);

	}

	public void addResult(Result result) {
		table.getItems().add(result);
	}

	public void removeResult() {
		table.getItems().clear();
	}
}
