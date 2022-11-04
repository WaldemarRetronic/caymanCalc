module pl.valdemar.wire {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens pl.valdemar.wire to javafx.fxml;
    exports pl.valdemar.wire;
}
