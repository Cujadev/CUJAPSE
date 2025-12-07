package interfaz.controllers;

<<<<<<< HEAD
<<<<<<< HEAD
public class PrincipalController {
}
=======
import interfaz.auxiliars.Evento;
import interfaz.auxiliars.Mensaje;
import interfaz.auxiliars.VisualTree;
=======
>>>>>>> 0cca16d2fec70aadf4769c45c401c3dd5fcdaa44
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
<<<<<<< HEAD
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
=======
>>>>>>> 0cca16d2fec70aadf4769c45c401c3dd5fcdaa44
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
<<<<<<< HEAD
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;

import java.io.InputStream;
import java.net.URL;
=======
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
>>>>>>> 0cca16d2fec70aadf4769c45c401c3dd5fcdaa44
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

<<<<<<< HEAD
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
=======
    @FXML private VBox vboxMensajes;
    @FXML private ScrollPane scrollChat;
    @FXML private ImageView btnCerrar;
    @FXML private ImageView btnMaximizar;
    @FXML private ImageView btnMinimizar;
    // decision UI
>>>>>>> 0cca16d2fec70aadf4769c45c401c3dd5fcdaa44
    @FXML private VBox decisionBox;
    @FXML private Label labelDecisionMessage;
    @FXML private Button btnOptionYes;
    @FXML private Button btnOptionNo;
    @FXML private Button btnSendDecision;
<<<<<<< HEAD
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
=======

    // Estado de elección actual (1 = sí, 2 = no, 0 = nada)
    private int selectedOption = 0;
    private String selectedOptionText = "";
    private boolean decisionEnviada = false;

    // Rutas de avatar por defecto
    private static final String AVATAR_OMAR = "/visualResources/personajes/Omar_fotoPerfil.png";
    private static final String AVATAR_PLAYER = "/visualResources/personajes/player.png";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Mantener scroll abajo al agregar mensajes
        vboxMensajes.heightProperty().addListener((obs, oldVal, newVal) -> scrollChat.setVvalue(1.0));

        // Estado inicial de la decisión - label vacío
        labelDecisionMessage.setText("");

        btnSendDecision.setDisable(true); // sin selección, no enviar
        btnSendDecision.setVisible(false); // Ocultar inicialmente

        styleOptionButtons();

        // Inicialmente ocultar el área de decisión hasta que se cargue un evento
        decisionBox.setVisible(false);
    }

    // ------------- Para cargar evento -------------
    public static class Mensaje {
        public final String autor;
        public final String texto;
        public final String avatarPath;
        public Mensaje(String autor, String texto, String avatarPath) {
            this.autor = autor;
            this.texto = texto;
            this.avatarPath = avatarPath;
        }
    }

    public static class Evento {
        public final String pregunta;
        public final String opcionSi;
        public final String opcionNo;
        public final List<Mensaje> mensajes;
        public Evento(String pregunta, String opcionSi, String opcionNo, List<Mensaje> mensajes) {
            this.pregunta = pregunta;
            this.opcionSi = opcionSi;
            this.opcionNo = opcionNo;
            this.mensajes = mensajes;
        }
    }

    public void cargarEvento(Evento evento) {
        vboxMensajes.getChildren().clear();
        decisionEnviada = false; // Resetear el estado de decisión enviada

        // mostrar mensajes del evento (con animación)
        if (evento.mensajes != null) {
            for (Mensaje m : evento.mensajes) {
                addMessageAnimated(m.autor, m.texto, m.avatarPath);
            }
        }

        // configurar la zona de decisión fija abajo
        labelDecisionMessage.setText(""); // Label vacío inicialmente
        btnOptionYes.setText("Sí"); // Botones siempre muestran "Sí" y "No"
        btnOptionNo.setText("No");

        // Mostrar el área de decisión y habilitar botones
        decisionBox.setVisible(true);
        enableDecisionButtons(true);
        clearSelection();
    }

    // ------------- selección de opciones (solo marcan) -------------
    @FXML
    private void onOptionYesClick() {
        if (decisionEnviada) return; // No permitir cambios después de enviar
        selectedOption = 1;
        selectedOptionText = "¡Claro que sí, estoy listo!"; // Texto fijo para Sí
        updateDecisionDisplay();
>>>>>>> 0cca16d2fec70aadf4769c45c401c3dd5fcdaa44
    }

    @FXML
    private void onOptionNoClick() {
<<<<<<< HEAD
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
>>>>>>> 7a7600f (interfaces 3)
=======
        if (decisionEnviada) return; // No permitir cambios después de enviar
        selectedOption = 2;
        selectedOptionText = "No creo estar preparado aún..."; // Texto fijo para No
        updateDecisionDisplay();
    }

    private void updateDecisionDisplay() {
        // Mostrar el texto seleccionado en el label
        labelDecisionMessage.setText(selectedOptionText);

        // Mostrar y habilitar el botón de enviar
        btnSendDecision.setDisable(false);
        btnSendDecision.setVisible(true);

        updateOptionStyles();
    }

    private void clearSelection() {
        selectedOption = 0;
        selectedOptionText = "";
        labelDecisionMessage.setText(""); // Limpiar el label
        btnSendDecision.setDisable(true);
        btnSendDecision.setVisible(false); // Ocultar el botón de enviar
        updateOptionStyles();
    }

    private void updateOptionStyles() {
        btnOptionYes.getStyleClass().removeAll("decision-selected");
        btnOptionNo.getStyleClass().removeAll("decision-selected");
        if (selectedOption == 1) btnOptionYes.getStyleClass().add("decision-selected");
        if (selectedOption == 2) btnOptionNo.getStyleClass().add("decision-selected");
    }

    private void styleOptionButtons() {
        // ensure style class present (CSS defines appearance)
        if (!btnOptionYes.getStyleClass().contains("decision-btn"))
            btnOptionYes.getStyleClass().add("decision-btn");
        if (!btnOptionNo.getStyleClass().contains("decision-btn"))
            btnOptionNo.getStyleClass().add("decision-btn");
    }

    // Habilitar o deshabilitar botones de decisión
    private void enableDecisionButtons(boolean enable) {
        btnOptionYes.setDisable(!enable);
        btnOptionNo.setDisable(!enable);
        if (!enable) {
            btnSendDecision.setDisable(true);
            btnSendDecision.setVisible(false);
        }
    }

    // ------------- enviar la decisión (confirma) -------------
    @FXML
    private void onSendDecisionClick() {
        if (selectedOption == 0 || decisionEnviada) return; // nada seleccionado o ya enviado

        // Marcar que ya se envió una decisión
        decisionEnviada = true;

        // enviar al chat como mensaje del jugador (con avatar)
        addMessageAnimated("Tú", selectedOptionText, AVATAR_PLAYER);

        // Deshabilitar todos los botones de decisión hasta el próximo evento
        enableDecisionButtons(false);

        // Limpiar el display de decisión
        labelDecisionMessage.setText("");

        // Aqui llama la lógica del juego con el código 1 o 2
        System.out.println("Decision enviada al engine -> codigo: " + selectedOption + ", texto: " + selectedOptionText);
    }

    // ------------- helpers UI: avatar + bubble + animaciones -------------
    private void addMessageAnimated(String autor, String texto, String avatarPath) {
        // crear fila
        HBox fila = new HBox();
        fila.setPadding(new Insets(8, 12, 8, 12));
        fila.setSpacing(10);

        boolean isPlayer = autor.equalsIgnoreCase("Tú") || autor.equalsIgnoreCase("Jugador");
        fila.setAlignment(isPlayer ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        // avatar
        ImageView avatar = makeAvatar(avatarPath);

        // burbuja
        VBox burbuja = new VBox();
        burbuja.setMaxWidth(520);
        burbuja.getStyleClass().add(isPlayer ? "burbuja-player" : "burbuja-npc");

        Label rem = new Label(autor.equalsIgnoreCase("Tú") ? "Tú" : autor);
        rem.getStyleClass().add("label-usuario");
        Label contenido = new Label(texto);
        contenido.getStyleClass().add("label-mensaje");
        contenido.setWrapText(true);

        burbuja.getChildren().addAll(rem, contenido);

        if (isPlayer) {
            fila.getChildren().addAll(burbuja, avatar);
        } else {
            fila.getChildren().addAll(avatar, burbuja);
        }

        // Inicialmente invisible y desplazado para animación
        fila.setOpacity(0);
        fila.setTranslateY(10);

        vboxMensajes.getChildren().add(fila);

        // Animaciones: fade + translate
        FadeTransition ft = new FadeTransition(Duration.millis(300), fila);
        ft.setFromValue(0);
        ft.setToValue(1);

        TranslateTransition tt = new TranslateTransition(Duration.millis(300), fila);
        tt.setFromY(10);
        tt.setToY(0);

        SequentialTransition st = new SequentialTransition();
        st.getChildren().addAll(ft, tt);
        st.play();
    }

    private ImageView makeAvatar(String avatarPath) {
        Image img = null;
        try (InputStream is = getClass().getResourceAsStream(avatarPath)) {
            if (is != null) img = new Image(is);
        } catch (Exception ex) {
            System.err.println("No se pudo cargar avatar: " + avatarPath + " -> " + ex.getMessage());
        }
        ImageView iv;
        if (img != null) {
            iv = new ImageView(img);
        } else {
            iv = new ImageView(); // vacío, pero evita NPE
        }
        iv.setFitWidth(44);
        iv.setFitHeight(44);
        iv.setPreserveRatio(true);
        Circle clip = new Circle(22, 22, 22);
        iv.setClip(clip);
        return iv;
    }
}
>>>>>>> 0cca16d2fec70aadf4769c45c401c3dd5fcdaa44
