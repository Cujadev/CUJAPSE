package main;

import interfaz.auxiliars.Mensaje;
import interfaz.controllers.PrincipalController;
import interfaz.auxiliars.Evento;
import interfaz.auxiliars.Mensaje;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/Principal.fxml"));
        Parent root = loader.load();

        // Obtener el controlador
        PrincipalController controller = loader.getController();

        // Configurar la escena
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("Prueba de Interfaz - Simulador de Decisiones");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Probar las estadísticas después de mostrar la ventana
        javafx.application.Platform.runLater(() -> {
            // Probar actualización de estadísticas
            testStats(controller);

            // Probar carga de evento de chat
            testChat(controller);
        });
    }

    private void testStats(PrincipalController controller) {
        System.out.println("=== Probando Estadísticas ===");

        // Probar diferentes valores
        controller.setStatValue(1, 75);   // Dinero 75%
        controller.setStatValue(2, 50);   // Cafeína 50%
        controller.setStatValue(3, 25);   // Popularidad 25%
        controller.setStatValue(4, 100);  // Estudios 100%

        // Probar valores límite
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

        controller.setStatValue(1, 150);  // Debería limitarse a 100%
        controller.setStatValue(2, -10);  // Debería limitarse a 0%

        System.out.println("=== Fin prueba estadísticas ===");
    }

    private void testChat(PrincipalController controller) {
        System.out.println("=== Probando Chat ===");

        // Crear mensajes - USANDO LA CLASE CORRECTA
        List<Mensaje> mensajes = new ArrayList<>();  // ← Cambiado a Message

        // Mensaje 1: NPC - USANDO CONSTRUCTOR DE Message (ajusta parámetros)
        Mensaje msg1 = new Mensaje("Profesor",
                "¡Hola! ¿Estás listo para el examen de mañana?",
                "/visualResources/personajes/profesora.png",
                null);
        mensajes.add(msg1);

        // Mensaje 2: NPC con imagen
        Mensaje msg2 = new Mensaje("Amigo",
                "Mira lo que encontré en internet, ¿no es genial?",
                "/visualResources/personajes/fiestero.png",
                "/visualResources/escenarios/fondoTutorial.jpg");
        mensajes.add(msg2);

        // Mensaje 3: Sistema
        Mensaje msg3 = new Mensaje("Omar",
                "Tienes que tomar una decisión importante...",
                "/visualResources/iconos/Omar_fotoPerfil.png",
                null);
        mensajes.add(msg3);

        // Crear evento - Evento también debe usar Message, no Mensaje
        // Esto dependerá de cómo esté definida tu clase Evento
        Evento evento = new Evento(mensajes);

        // Cargar el evento en el controlador
        controller.loadEvent(evento);

        System.out.println("=== Fin prueba chat ===");
    }

    public static void main(String[] args) {
        launch(args);
    }
}