module com.example.cujapse {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

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
    exports logic.auxiliars.initializers;
    exports logic.auxiliars.dataOfInterfaces;
    // JavaFX necesita acceso con reflexión a controladores y FXML
    opens interfaz.controllers to javafx.fxml;
    opens interfaces to javafx.fxml;
    opens main to javafx.graphics;

}
