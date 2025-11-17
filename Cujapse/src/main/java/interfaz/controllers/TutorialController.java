package interfaz.controllers;
import logic.clases.Dialogue;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;


public class TutorialController {

    // --- Variables FXML inyectadas (de PrologoView.fxml) ---
    @FXML private ImageView imgPersonaje;
    @FXML private ImageView imgAppPreview;
    @FXML private Label labelDialogo;
    @FXML private Button btnContinuar;
    @FXML private Button btnNo;
    @FXML private HBox hboxBotones;

    // --- Variables de Lógica y Flujo ---
    private ArrayList<String> lineasTutorial;
    private int indiceDialogoActual = 0;

    // Constantes para identificar momentos clave
    private final String MENSAJE_OMAR_FINAL = "¿Entiendes cómo funciona la app?";
    private final String MENSAJE_PREVIEW_TRIGGER = "[SHOW_APP_PREVIEW]";
    private final String RESPUESTA_NO = "No hay mucha complicación. Cada vez que hables en la app se afectan estas 4 estadísticas de variadas formas. O sea cada vez que digas algo podrás ver como se afecta esa decisión en tu vida universitaria ¿Ya entiendes?";

    @FXML
    public void initialize() {
        // Inicialmente, solo se muestra el botón "Continuar..."
        btnNo.setVisible(false);
        mostrarSiguienteLineaTutorial();
    }

    private void prepararTutorial(ArrayList<String>dialogos) {
        lineasTutorial = new ArrayList<>();
        for(int i = 0; i < dialogos.size(); i++) {
            lineasTutorial.add(dialogos.get(i));
        }
    }

    // --- Métodos de Eventos y Flujo ---

    @FXML
    private void onContinuarClick() {
        // Este método actúa como "Continuar..." y luego como "Sí"
        if (btnContinuar.getText().equals("Continuar...")) {
            mostrarSiguienteLineaTutorial();
        } else {
            onSiClick();
        }
    }

    @FXML
    private void onNoClick() {
        labelDialogo.setText(RESPUESTA_NO);
    }

    private void onSiClick() {
        labelDialogo.setText("Se espera que los mejores estudiantes sean los que sepan mantener en equilibrio estos cuatro aspectos. Si suben o bajan mucho se espera que el estudiante no sea capaz de mantener una universidad estable. Espero que todos tengan un lindo primer día y nos vemos en el que viene.");
        btnContinuar.setVisible(false);
        btnNo.setVisible(false);
        imgAppPreview.setVisible(false);
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(event -> transicionarAJuegoPrincipal());
        pause.play();
    }

    private void mostrarSiguienteLineaTutorial() {

    }

    private void transicionarAJuegoPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cujapse/views/JuegoPrincipal.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) labelDialogo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cujapse - Orchat");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista principal del juego: " + e.getMessage());
            e.printStackTrace();
        }
    }
}