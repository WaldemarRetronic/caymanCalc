module pl.valdemar.calcwires {
    requires javafx.controls;
    requires javafx.fxml;


    opens pl.valdemar.calcwires to javafx.fxml;
    exports pl.valdemar.calcwires;
}
