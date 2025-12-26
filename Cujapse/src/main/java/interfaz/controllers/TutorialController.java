package interfaz.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

/**
 * Controller de la pantalla Tutorial.
 * El MVC interactúa con:
 *  - setDialogLines(...)
 *  - startTutorial(...)
 *  - setListener(...)
 */
public class TutorialController {

    @FXML
    private Label labelDialogo;

    @FXML
    private Button btnContinuar;

    /** Lista de diálogos a mostrar en el tutorial */
    private List<String> dialogLines;
    private int index = 0;

    /** Listener para notificar al Modelo cuando el tutorial termina */
    private TutorialListener listener;

    /**
     * El Modelo debe pasar las líneas del tutorial aquí.
     */
    public void setDialogLines(List<String> lines) {
        this.dialogLines = lines;
        this.index = 0;
    }

    /**
     * Iniciar el tutorial mostrando la primera línea.
     */
    public void startTutorial() {
        if (dialogLines != null && !dialogLines.isEmpty()) {
            labelDialogo.setText(dialogLines.get(0));
        }
    }

    /**
     * Registrar el listener que recibirá el evento de "tutorial terminado".
     */
    public void setListener(TutorialListener listener) {
        this.listener = listener;
    }

    /**
     * Interacción del usuario para pasar a la siguiente línea del tutorial.
     */
    @FXML
    private void onContinuarClick() {
        if (dialogLines == null || dialogLines.isEmpty()) return;

        index++;

        if (index < dialogLines.size()) {
            labelDialogo.setText(dialogLines.get(index));
        } else {
            // Notificar al Modelo que el tutorial terminó
            if (listener != null) {
                listener.onTutorialFinished();
            }
        }
    }

    /**
     * Listener para comunicar al Modelo que el tutorial terminó.
     */
    public interface TutorialListener {
        void onTutorialFinished();
    }
}
