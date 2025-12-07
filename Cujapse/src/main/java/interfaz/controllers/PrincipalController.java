package interfaz.controllers;

import interfaz.auxiliars.Evento;
import interfaz.auxiliars.Mensaje;
import interfaz.auxiliars.VisualTree;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    // ================================================================
    //                        FXML ELEMENTOS
    // ================================================================
    @FXML private VBox panelIzquierdo;
    @FXML private VBox panelChat;
    @FXML private VBox vboxMensajes;
    @FXML private ScrollPane scrollChat;

    // Contenedores HBox para estadísticas (mantener nombres del FXML)
    @FXML private HBox statsTopBar;
    @FXML private HBox labelDinero;
    @FXML private HBox labelCafeina;
    @FXML private HBox labelPopularidad;
    @FXML private HBox labelEstudios;

    // Labels para el texto de porcentaje
    @FXML private Label labelDineroTexto;
    @FXML private Label labelCafeinaTexto;
    @FXML private Label labelPopularidadTexto;
    @FXML private Label labelEstudiosTexto;

    @FXML private AnchorPane decisionArea;
    @FXML private VBox decisionBox;
    @FXML private Label labelDecisionMessage;
    @FXML private Button btnOptionYes;
    @FXML private Button btnOptionNo;
    @FXML private Button btnSendDecision;
    @FXML private VBox panelArbol;

    // ================================================================
    //                        VARIABLES INTERNAS
    // ================================================================
    private int selectedOption = 0;
    private boolean decisionSent = false;
    private static final String DEFAULT_PLAYER_AVATAR = "/visualResources/personajes/player.png";
    private static final String FALLBACK_AVATAR = "/visualResources/iconos/default-avatar.png";
    private VisualTree visualTree;
    private DecisionTree<?> logicTree;
    private DecisionNode<?> currentNode;

    // ================================================================
    //                           INITIALIZE
    // ================================================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vboxMensajes.heightProperty().addListener((obs, oldV, newV) ->
                scrollChat.setVvalue(1.0)
        );

        decisionBox.setVisible(false);
        btnSendDecision.setVisible(false);

        // Asegurar que se muestren los valores iniciales
        setStatValue(1, 0);
        setStatValue(2, 0);
        setStatValue(3, 0);
        setStatValue(4, 0);
    }

    // ================================================================
    //                         ESTADISTICAS (UI)
    // ================================================================
    /**
     * Método llamado desde el MVC para actualizar un porcentaje.
     */
    public void setStatValue(int index, int value) {
        if (value < 0) value = 0;
        if (value > 100) value = 100;

        Label target = null;
        switch(index) {
            case 1: target = labelDineroTexto; break;
            case 2: target = labelCafeinaTexto; break;
            case 3: target = labelPopularidadTexto; break;
            case 4: target = labelEstudiosTexto; break;
            default: return;
        }

        if (target != null) {
            target.setText(value + "%");

            // DEBUG: Verificar que se actualiza
            System.out.println("Actualizando estadística " + index + " a " + value + "%");
        }
    }

    // ================================================================
    //                         ÁRBOL DE DECISIONES
    // ================================================================
    public void initTree(DecisionTree<?> tree) {
        this.logicTree = tree;
        this.currentNode = tree.getRoot();
        visualTree = new VisualTree(createTreeCanvas(), tree.getRoot());
        panelArbol.getChildren().add(visualTree);
        visualTree.drawTree();
        visualTree.focusNode(tree.getRoot());
    }

    public void moveToNode(DecisionNode<?> newNode) {
        currentNode = newNode;
        visualTree.drawTree();
        visualTree.focusNode(newNode);
    }

    private Canvas createTreeCanvas() {
        return new Canvas(2000, 2000);
    }

    // ================================================================
    //                              CHAT
    // ================================================================
    public void loadEvent(Evento evento) {
        vboxMensajes.getChildren().clear();
        decisionSent = false;

        if (evento != null && evento.getMensajes() != null) {
            for (Mensaje m : evento.getMensajes()) {
                addMessageAnimated(m.autor, m.texto, m.avatarPath, m.imagePath);
            }
        }

        labelDecisionMessage.setText("");
        btnOptionYes.setDisable(false);
        btnOptionNo.setDisable(false);
        decisionBox.setVisible(true);
        btnSendDecision.setVisible(false);
    }

    @FXML
    private void onOptionYesClick() {
        if (decisionSent) return;
        selectedOption = 1;
        labelDecisionMessage.setText("Sí");
        btnSendDecision.setVisible(true);
    }

    @FXML
    private void onOptionNoClick() {
        if (decisionSent) return;
        selectedOption = 2;
        labelDecisionMessage.setText("No");
        btnSendDecision.setVisible(true);
    }

    @FXML
    private void onSendDecisionClick() {
        if (selectedOption == 0) return;
        decisionSent = true;
        addMessageAnimated("Tú", labelDecisionMessage.getText(),
                DEFAULT_PLAYER_AVATAR, null);
        btnOptionYes.setDisable(true);
        btnOptionNo.setDisable(true);
        btnSendDecision.setVisible(false);
        System.out.println("MVC -> decisión enviada = " + selectedOption);
    }

    // ================================================================
    //                    MENSAJES CON ANIMACIÓN
    // ================================================================
    private void addMessageAnimated(String sender, String text,
                                    String avatarPath, String imgPath) {
        HBox row = new HBox();
        row.setAlignment(sender.equalsIgnoreCase("Tú")
                ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        row.setSpacing(10);
        row.setPadding(new Insets(6));

        ImageView avatar = makeAvatar(avatarPath);
        VBox bubble = new VBox();
        bubble.setSpacing(5);
        bubble.setMaxWidth(500);
        bubble.getStyleClass().add(
                sender.equalsIgnoreCase("Tú") ? "burbuja-player" : "burbuja-npc"
        );

        Label lblSender = new Label(sender);
        lblSender.getStyleClass().add("label-usuario");
        Label lblText = new Label(text);
        lblText.setWrapText(true);
        lblText.getStyleClass().add("label-mensaje");
        bubble.getChildren().addAll(lblSender, lblText);

        if (imgPath != null && !imgPath.isEmpty()) {
            ImageView iv = new ImageView(safeLoadImage(imgPath));
            iv.setFitWidth(300);
            iv.setPreserveRatio(true);
            bubble.getChildren().add(iv);
        }

        if (sender.equalsIgnoreCase("Tú"))
            row.getChildren().addAll(bubble, avatar);
        else
            row.getChildren().addAll(avatar, bubble);

        row.setOpacity(0);
        row.setTranslateY(12);
        vboxMensajes.getChildren().add(row);

        FadeTransition ft = new FadeTransition(Duration.millis(250), row);
        ft.setFromValue(0); ft.setToValue(1);
        TranslateTransition tt = new TranslateTransition(Duration.millis(250), row);
        tt.setFromY(12); tt.setToY(0);
        new SequentialTransition(ft, tt).play();
    }

    private ImageView makeAvatar(String path) {
        Image img = safeLoadImage(path);
        if (img == null) img = safeLoadImage(FALLBACK_AVATAR);
        ImageView iv = new ImageView(img);
        iv.setFitWidth(44);
        iv.setFitHeight(44);
        iv.setClip(new Circle(22, 22, 22));
        return iv;
    }

    private Image safeLoadImage(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        try {
            // Asegurar que la ruta empieza con /
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                return new Image(is);
            } else {
                System.err.println("No se pudo cargar imagen: " + path);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen " + path + ": " + e.getMessage());
        }
        return null;
    }

    // Método para probar si se ven las estadísticas
    public void testStats() {
        setStatValue(1, 50);
        setStatValue(2, 75);
        setStatValue(3, 25);
        setStatValue(4, 100);
    }
}