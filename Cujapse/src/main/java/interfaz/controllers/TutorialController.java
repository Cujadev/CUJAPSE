package interfaz.controllers;

import interfaz.sounds.SoundManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class TutorialController {

    // ========== FXML ==========
    @FXML private BorderPane rootPane;
    @FXML private ImageView backgroundImage;
    @FXML private ImageView personaje;
    @FXML private Label labelDialogo;
    @FXML private Button btnContinuar;

    // ========== LÓGICA ORIGINAL ==========
    private List<String> dialogLines;
    private int index = 0;
    private TutorialListener listener;

    public void setDialogLines(List<String> lines) {
        this.dialogLines = lines;
        this.index = 0;
    }

    public void startTutorial() {
        SoundManager.playEffect("/sound/button_09-190435.mp3");
        if (dialogLines != null && !dialogLines.isEmpty()) {
            labelDialogo.setText(dialogLines.get(0));
        }
    }

    public void setListener(TutorialListener listener) {
        this.listener =  listener;
    }

    @FXML
    private void onContinuarClick() {
        if (dialogLines == null || dialogLines.isEmpty()) return;

        index++;

        if (index < dialogLines.size()) {
            labelDialogo.setText(dialogLines.get(index));
        } else {
            if (listener != null) listener.onTutorialFinished();
        }
    }

    public interface TutorialListener {
        void onTutorialFinished();
    }


    // ========== RESPONSIVE (solo fullscreen + fondo adaptable) ==========
    private Stage stage;

    @FXML
    public void initialize() {

        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {

                newScene.windowProperty().addListener((obs2, oldWin, newWin) -> {
                    if (newWin != null) {

                        stage = (Stage) newWin;

                        // Fullscreen real
                        Platform.runLater(() -> stage.setFullScreen(true));
                        stage.setFullScreenExitHint("");
                        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

                    }
                });
            }
        });
    }
    @FXML
    private void onSkipClick() {
        SoundManager.playEffect("/sound/button_09-190435.mp3");
        if (listener != null) listener.onTutorialFinished();
    }

}
