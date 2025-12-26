package interfaz.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller de la pantalla Tutorial.Aquí están los métodos para manejar el flujo del tutorial(FXML).
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

    /* Runnable es el tipo de dato para una interfaz funcional
     * *De esta forma cuando se finalice el tutorial ,que se vaya a pasar a la pantalla principal ,tan solo es pasar el FXML
     * por parametros.Y cerrara el tutorial y abrirá la pantalla principal */
    private Runnable onFinish;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelDialogo.setText("");
    }

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

    //Interacción dle usuario para pasar a la siguiente línea del tutorial

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

