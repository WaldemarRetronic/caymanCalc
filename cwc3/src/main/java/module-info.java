module pl.valdemar.cwc3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.valdemar.cwc3 to javafx.fxml;
    exports pl.valdemar.cwc3;
}
