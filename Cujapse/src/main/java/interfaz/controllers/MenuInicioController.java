package interfaz.controllers;

import interfaz.sounds.SoundManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.GameControler;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

public class MenuInicioController {

    private MenuInicioListener listener;

    @FXML private AnchorPane rootPane;
    @FXML private StackPane scalableRoot;
    @FXML private VBox vboxMenu;

    @FXML private Button btnNuevaPartida;
    @FXML private Button btnCargarPartida;
    @FXML private Button btnSalir;

    @FXML private ImageView backgroundImage;

    private Stage stage;
    private boolean initialized = false;

    @FXML
    public void initialize() {

        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            btnCargarPartida.setVisible(false);
            if (newScene != null && !initialized) {
                initialized = true;

                newScene.windowProperty().addListener((obs2, oldWin, newWin) -> {
                    if (newWin != null) {
                        stage = (Stage) newWin;

                        stage.setResizable(false);
                        Platform.runLater(() -> stage.setFullScreen(true));
                        stage.setFullScreenExitHint("");
                        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

                    }
                });
            }
        });
    }

    // BOTONES DEL MENÚ

    @FXML
    private void onNuevaPartidaClick() {
        if (listener != null) listener.onMenuOptionSelected(1);
    }

    @FXML
    private void onCargarPartidaClick() {
        if (listener != null) listener.onMenuOptionSelected(2);
    }

    @FXML
    private void onSalirClick() {
        if (listener != null) listener.onMenuOptionSelected(3);
    }

    public void setListener(MenuInicioListener listener) { this.listener = listener; }

    public interface MenuInicioListener {
        void onMenuOptionSelected(int codigo);
    }
}

