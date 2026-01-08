module com.example.cujapse {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;

    // Exportamos controladores si otra parte del programa los necesita
    exports interfaz.controllers;
    exports main;
    exports logic.auxiliars.tree;
    exports logic.auxiliars.tree.Iterator;
    exports logic.auxiliars.files;
    exports logic.auxiliars.chargers;
    exports logic.clases.character;
    exports logic.clases.event;
    exports logic.clases.game;
    // JavaFX necesita acceso con reflexión a controladores y FXML
    opens interfaz.controllers to javafx.fxml;
    opens interfaces to javafx.fxml;
    opens main to javafx.graphics;


}
