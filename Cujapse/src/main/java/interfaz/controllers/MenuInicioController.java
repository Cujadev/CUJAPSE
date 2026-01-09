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

    @FXML private MenuInicioListener listener;

    @FXML private AnchorPane rootPane;
    @FXML private StackPane scalableRoot;

    @FXML private Pane panelMenu;
    @FXML private VBox vboxMenu;

    @FXML private Button btnNuevaPartida;
    @FXML private Button btnCargarPartida;
    @FXML private Button btnSalir;
    @FXML private Button btnFullscreen;

    @FXML private ImageView backgroundImage;

    private Stage stage;

    // Tamaño base EXACTO del diseño
    private final double BASE_WIDTH = 870;
    private final double BASE_HEIGHT = 820;

    // Posición EXACTA del menú en tu diseño
    private final double BASE_MENU_X = 382;
    private final double BASE_MENU_Y = 362;


    @FXML
    public void initialize() {

        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {

                newScene.windowProperty().addListener((obs2, oldWin, newWin) -> {
                    if (newWin != null) {
                        stage = (Stage) newWin;
                        //Esto es necesario,no se puede quitar
                        //Cada vez que quito el boton ese ,se rompe todo el layout ,asi que esto es un remedio temporal hasta que encuentre solucion
                        btnFullscreen.setOpacity(0);   // No se ve
                        btnFullscreen.setMouseTransparent(true); // No se puede clicar
                        btnFullscreen.setFocusTraversable(false); // No recibe foco

                        stage.setResizable(false);
                        Platform.runLater(() -> { stage.setFullScreen(true); });
                        stage.setFullScreenExitHint("");
                        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                        //Responsive layout
                        backgroundImage.fitWidthProperty().bind(scalableRoot.widthProperty());
                        backgroundImage.fitHeightProperty().bind(scalableRoot.heightProperty());

                        vboxMenu.translateYProperty().bind(
                                scalableRoot.heightProperty().multiply(0.42)
                        );


                        // ⭐ MARGEN DEL BOTÓN (AQUÍ SÍ FUNCIONA)
                        StackPane.setMargin(btnFullscreen, new Insets(15, 15, 0, 0));
                    }
                });
            }
        });
    }



    private void updateLayout() {

        double scaleX = stage.getWidth() / BASE_WIDTH;
        double scaleY = stage.getHeight() / BASE_HEIGHT;

        // ⭐ Reposicionar menú proporcionalmente
        panelMenu.setLayoutX(BASE_MENU_X * scaleX);
        panelMenu.setLayoutY(BASE_MENU_Y * scaleY);

        // ⭐ Ajustar fondo para que cubra todo sin dejar espacios
        backgroundImage.setFitWidth(stage.getWidth());
        backgroundImage.setFitHeight(stage.getHeight());
    }

    // ============================================================
    //  BOTONES DEL MENÚ
    // ============================================================

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

    // ============================================================
    //  BOTÓN PANTALLA COMPLETA
    // ============================================================

    @FXML
    private void onToggleFullscreen() {
        if (stage == null) return;

        boolean nuevoEstado = !stage.isFullScreen();
        stage.setFullScreen(nuevoEstado);

        btnFullscreen.setText(nuevoEstado ? "Salir de Pantalla Completa" : "Pantalla Completa");
    }

    // ============================================================
    //  LISTENER
    // ============================================================


    public void setListener(MenuInicioListener listener) { this.listener=listener;}

    public interface MenuInicioListener {
        void onMenuOptionSelected(int codigo);
    }
}
