package interfaz.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

/**
 * Controller de la pantalla Tutorial.
 * Aquí están los métodos para manejar el flujo del tutorial (FXML).
 * Para manejar la pantalla de tutorial, el MVC debe interactuar con estos métodos:
 *  - setDialogLines(...)
 *  - startTutorial(...)
 *  - onTutorialFinishedCallback(...)
 */
public class TutorialController {

    @FXML
    private Label labelDialogo;

    @FXML
    private Button btnContinuar;

    /**
     * Lista de diálogos a mostrar en el tutorial
     */
    private List<String> dialogLines;
    private int index = 0;

    /*
     * Runnable es el tipo de dato para una interfaz funcional.
     * De esta forma, cuando se finalice el tutorial y se vaya a pasar
     * a la pantalla principal, solo se pasa el FXML por parámetros.
     * Cerrará el tutorial y abrirá la pantalla principal.
     */
    private Runnable onFinish;

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
     * Registra qué debe pasar cuando el tutorial termina
     * (por ejemplo: abrir la interfaz principal).
     */
    public void setOnTutorialFinish(Runnable r) {
        this.onFinish = r;
    }

    // Interacción del usuario para pasar a la siguiente línea del tutorial
    @FXML
    private void onContinuarClick() {
        boolean puedeContinuar = dialogLines != null && !dialogLines.isEmpty();

        if (puedeContinuar) {
            index++;

            if (index < dialogLines.size()) {
                labelDialogo.setText(dialogLines.get(index));
            } else {
                if (onFinish != null) {
                    onFinish.run();
                }
            }
        }
    }
}
