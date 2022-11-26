module pl.valdemar.wire {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.common;

    opens pl.valdemar.wire to javafx.fxml;
    exports pl.valdemar.wire;
    exports pl.valdemar.wire.model;
    opens pl.valdemar.wire.model to javafx.fxml;
    exports pl.valdemar.wire.controllers;
    opens pl.valdemar.wire.controllers to javafx.fxml;
    exports pl.valdemar.wire.dialogs;
    opens pl.valdemar.wire.dialogs to javafx.fxml;
}
