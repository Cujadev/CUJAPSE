package main;

import interfaz.controllers.PrincipalController;
import interfaz.controllers.PrincipalController.Evento;
import interfaz.controllers.PrincipalController.Mensaje;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/interfaces/Principal.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Prueba CUJAPSE");
        stage.setScene(scene);
        stage.setResizable(true);   // permite probar redimensionamiento
        stage.show();

        // obtener controlador
        PrincipalController controller = loader.getController();

        // evento de prueba
        Evento evento = new Evento(
                "¿Quieres participar en los Juegos Trece?",     // pregunta principal
                "¡Claro que sí, estoy listo!",                 // texto opción SÍ
                "No creo estar preparado aún…",                // texto opción NO
                List.of(
                        new Mensaje("Omar", "Hola, necesito preguntarte algo importante.", "/visualResources/personajes/Omar_fotoPerfil.png"),
                        new Mensaje("Omar", "Ya comenzaron las inscripciones para los Juegos Trece.", "/visualResources/personajes/Omar_fotoPerfil.png"),
                        new Mensaje("Omar", "¿Quieres participar este año?", "/visualResources/personajes/Omar_fotoPerfil.png")
                )
        );

        controller.cargarEvento(evento);
    }

    public static void main(String[] args) {
        launch();
    }
}
