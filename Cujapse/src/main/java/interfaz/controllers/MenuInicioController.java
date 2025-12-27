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

    private Stage stage;

    private final double BASE_WIDTH = 972;
    private final double BASE_HEIGHT = 866;

    @FXML
    public void initialize() {



        // Obtener Stage de forma garantizada
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


}
