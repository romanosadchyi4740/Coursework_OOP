module com.example.courseworkfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens MainWindow to javafx.fxml;
    exports MainWindow;
    exports common;
    opens common to javafx.fxml;
}