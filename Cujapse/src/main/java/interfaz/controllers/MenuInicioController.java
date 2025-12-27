package interfaz.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MenuInicioController {

    @FXML private AnchorPane rootPane;
    @FXML private StackPane scalableRoot;

    @FXML private Button btnFullscreen;
    @FXML private Button btnNuevaPartida;
    @FXML private Button btnCargarPartida;
    @FXML private Button btnSalir;

    private Stage stage;

    private final double BASE_WIDTH = 972;
    private final double BASE_HEIGHT = 866;

    // Listener que recibirá los códigos 1, 2 y 3
    private MenuInicioListener listener;

    // Permite registrar el listener desde la lógica del juego
    public void setListener(MenuInicioListener listener) {
        this.listener = listener;
    }

    @FXML
    public void initialize() {


        // -----------------------------
        // OBTENER EL STAGE Y ESCALAR
        // -----------------------------
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {

                newScene.windowProperty().addListener((obs2, oldWin, newWin) -> {
                    if (newWin != null) {
                        stage = (Stage) newWin;

                        // Desactivar maximizar y resize
                        stage.setResizable(false);

                        // Escalado proporcional REAL
                        scalableRoot.scaleXProperty().bind(
                                stage.widthProperty().divide(BASE_WIDTH)
                        );
                        scalableRoot.scaleYProperty().bind(
                                stage.heightProperty().divide(BASE_HEIGHT)
                        );
                    }
                });
            }
        });
    }

    // -----------------------------
    // EVENTOS DE BOTONES (1, 2, 3)
    // -----------------------------

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

    // -----------------------------
    // INTERFAZ DEL LISTENER
    // -----------------------------
    public interface MenuInicioListener {
        void onMenuOptionSelected(int codigo);
    }
}
