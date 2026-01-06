package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/interfaces/MenuInicio.fxml")
            );

            Scene scene = new Scene(loader.load());
            stage.setTitle("CUJAPSE - Inicio");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR cargando MenuInicio.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
