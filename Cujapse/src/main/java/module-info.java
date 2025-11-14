module com.example.cujapse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.cujapse to javafx.fxml;
    exports com.example.cujapse;
}