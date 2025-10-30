module com.example.cujapse {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cujapse to javafx.fxml;
    exports com.example.cujapse;
}