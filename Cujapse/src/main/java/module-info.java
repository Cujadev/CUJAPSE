module com.example.cujapse {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    // Exportamos controladores si otra parte del programa los necesita
    exports interfaz.controllers;
    exports main;
    // JavaFX necesita acceso con reflexión a controladores y FXML
    opens interfaz.controllers to javafx.fxml;
    opens interfaces to javafx.fxml;
    opens main to javafx.graphics;

}
