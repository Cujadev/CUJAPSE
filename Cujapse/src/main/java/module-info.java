module com.example.cujapse {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< HEAD
=======
    requires javafx.graphics;
>>>>>>> 7a7600f (interfaces 3)
    requires java.desktop;

    opens com.example.cujapse to javafx.fxml;
    exports com.example.cujapse;
}