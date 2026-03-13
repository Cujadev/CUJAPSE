package interfaz.controllers;

import interfaz.sounds.SoundManager;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.auxiliars.dataOfInterfaces.Menssage;
import logic.auxiliars.dataOfInterfaces.PrincipalData;
import interfaz.auxiliars.VisualTree;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;
import org.controlsfx.control.PopOver;

import java.io.InputStream;
import java.util.ArrayList;
import javafx.scene.control.MenuItem;

public class PrincipalController {

    public interface DecisionListener {
        void onDecisionSelected(int codigo);
    }

    private DecisionListener decisionListener;

    public void setDecisionListener(DecisionListener listener) {
        this.decisionListener = listener;
    }

    @FXML
    private BorderPane rootPane;

    @FXML
    private VBox panelIzquierdo;
    @FXML
    private VBox panelChat;
    @FXML
    private VBox vboxMensajes;
    @FXML
    private ScrollPane scrollChat;

    @FXML
    private HBox statsTopBar;
    @FXML
    private HBox labelDinero;
    @FXML
    private HBox labelCafeina;
    @FXML
    private HBox labelPopularidad;
    @FXML
    private HBox labelEstudios;

    @FXML
    private Label labelDineroTexto;
    @FXML
    private Label labelCafeinaTexto;
    @FXML
    private Label labelPopularidadTexto;
    @FXML
    private Label labelEstudiosTexto;

    @FXML
    private VBox decisionArea;
    @FXML
    private Label labelDecisionMessage;
    @FXML
    private Button btnOptionYes;
    @FXML
    private Button btnOptionNo;
    @FXML
    private Button btnSendDecision;
    @FXML
    private Button btnContinuar;
    @FXML
    private MenuItem menuSalirMenu;

    @FXML
    private ScrollPane scrollArbol;

    @FXML
    private VBox treeContainer;

    @FXML
    private VBox panelArbol;

    private int selectedOption = 0;
    private String selectedOptionText = "";
    private boolean decisionEnviada = false;

    private static final String AVATAR_PLAYER = "/visualResources/characters/player.png";
    private static final String FALLBACK_AVATAR = "/visualResources/iconos/default-avatar.png";

    private VisualTree visualTree;
    private DecisionTree<?> logicTree;
    private DecisionNode<?> currentNode;

    private DecisionNode<?> previewNode;   // ⭐ NUEVO

    private ContinuarListener continuarListener;
    private PrincipalData data;

    @FXML
    private Button infoArbolIcon;

    @FXML
    private Button infoTeamIcon;

    @FXML
    private HBox menuSuperior;

    @FXML
    public void initialize() {
        createDecisionTreeInfoPanel();
        infoTeamIcon.setOnMouseClicked(e -> showTeamInfoDialog());

        vboxMensajes.heightProperty().addListener((obs, oldV, newV) ->
                scrollChat.setVvalue(1.0)
        );

        btnSendDecision.setVisible(false);
        btnSendDecision.setDisable(true);

        if (btnContinuar != null) {
            btnContinuar.setVisible(false);
            btnContinuar.setManaged(false);
        }

        labelDecisionMessage.setText("");

        styleOptionButtons();

        setStatValue(1, 0);
        setStatValue(2, 0);
        setStatValue(3, 0);
        setStatValue(4, 0);
    }



    private void showTeamInfoDialog() {
        Label titulo = new Label("Equipo de Desarrollo");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #9A2325;");

        Label roles = new Label(
                "Dirección:\n  • Luis Alberto Pérez Alvarez\n\n" +
                        "Diseño del Juego:\n  • Luis Alberto Pérez Alvarez\n\n" +
                        "Frontend:\n  • Alison Hidalgo Guerra\n  • Patricia Tomé Romero\n\n" +
                        "Backend:\n  • Luis Alberto Pérez Alvarez\n  • Ryan Negrete Menchaca\n  • Rolando del Barrio Benítez\n\n" +
                        "Música:\n  • Luis Alberto Pérez Alvarez\n  • Ryan Negrete Menchaca\n\n" +
                        "Guión:\n  • Maikel Alejandro García Bolívar\n\n" +
                        "Arte y Dibujo:\n  • Patricia Tomé Romero\n\n" +
                        "Diseño Gráfico:\n  • Alison Hidalgo Guerra"
        );
        roles.setStyle("-fx-font-size: 14px; -fx-text-fill: #3C0E05;");

        VBox contentBox = new VBox(12, titulo, roles);
        contentBox.setStyle(
                "-fx-background-color: #FFE8C7;" +
                        "-fx-padding: 20;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #E57500;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0.4, 0, 3);"
        );

        // ⭐ ScrollPane con estilo de barra personalizada
        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: transparent;" +
                        "-fx-border-color: transparent;" +
                        /* Fondo de la barra */
                        ".scroll-bar:vertical { -fx-background-color: linear-gradient(to bottom, #FFC46A, #F0A64D); }" +
                        ".scroll-bar:horizontal { -fx-background-color: linear-gradient(to bottom, #FFC46A, #F0A64D); }" +
                        /* Color del thumb (parte que se mueve) */
                        ".scroll-bar:vertical .thumb { -fx-background-color: linear-gradient(to bottom, #FFC46A, #F0A64D); -fx-background-radius: 5; }" +
                        ".scroll-bar:horizontal .thumb { -fx-background-color: linear-gradient(to right, #FFC46A, #F0A64D); -fx-background-radius: 5; }" +
                        /* Hover */
                        ".scroll-bar:vertical .thumb:hover { -fx-background-color: #FF8C1A; }" +
                        ".scroll-bar:horizontal .thumb:hover { -fx-background-color: #FF8C1A; }"
        );

        // Crear el diálogo modal
        Stage dialog = new Stage();
        dialog.setTitle("Información del equipo");
        dialog.initModality(Modality.APPLICATION_MODAL); // bloquea la app hasta cerrarlo
        dialog.initOwner(rootPane.getScene().getWindow()); // ⭐ se abre sobre la principal
        dialog.setScene(new Scene(scrollPane, 450, 500));

        dialog.showAndWait(); // no se puede salir hasta cerrarlo
    }



    private void createDecisionTreeInfoPanel() {

        Label texto = new Label(
                "Este árbol de decisiones representa una estructura de datos jerárquica\n" +
                        "donde cada nodo corresponde a una situación del juego y cada rama a una\n" +
                        "elección posible del usuario.\n\n" +
                        "Su función es mostrar de forma visual cómo se organiza la lógica del\n" +
                        "sistema: un conjunto de decisiones encadenadas que generan rutas\n" +
                        "alternativas. Esta representación permite comprender:\n" +
                        "• La estructura jerárquica de un árbol binario\n" +
                        "• La relación padre–hijo entre situaciones\n" +
                        "• Cómo se modelan decisiones mediante nodos y ramas\n" +
                        "• Cómo se propagan las consecuencias a través del árbol\n" +
                        "• Cómo se implementa un recorrido para avanzar en la narrativa\n\n" +
                        "El objetivo es que el usuario pueda visualizar la lógica interna\n" +
                        "del juego como una estructura de datos real, aplicando conceptos de\n" +
                        "árboles, recorridos y nodos enlazados."
        );

        texto.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: #3C0E05;" +
                        "-fx-line-spacing: 3;"
        );

        VBox content = new VBox(texto);
        content.setStyle(
                "-fx-background-color: #FFE8C7;" +
                        "-fx-padding: 14;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: #E57500;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0.4, 0, 3);"
        );

        PopOver pop = new PopOver(content);
        pop.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        pop.setDetachable(false);
        pop.setAutoHide(true);
        pop.setAutoFix(true);

        infoArbolIcon.setOnMouseClicked(e -> pop.show(infoArbolIcon));
    }


    public void habilitarOpciones() {
        btnOptionYes.setDisable(false);
        btnOptionNo.setDisable(false);
        btnSendDecision.setDisable(true);
        btnSendDecision.setVisible(false);

        if (btnContinuar != null) {
            btnContinuar.setVisible(false);
            btnContinuar.setManaged(false);
        }
    }

    private void setStatValue(int index, int value) {
        if (value < 0) value = 0;
        if (value > 100) value = 100;

        Label target = switch (index) {
            case 1 -> labelCafeinaTexto;
            case 2 -> labelEstudiosTexto;
            case 3 -> labelPopularidadTexto;
            case 4 -> labelDineroTexto;
            default -> null;
        };

        if (target != null) {
            target.setText(value + "%");
        }
    }

    // ⭐ Variables para recordar valores anteriores
    private int lastCafeina = 0;
    private int lastEstudios = 0;
    private int lastPopularidad = 0;
    private int lastDinero = 0;

    public void setStats(ArrayList<Integer> stats) {

        // ⭐ Animaciones ANTES de actualizar los textos
        animateStatChange(labelCafeina, labelCafeinaTexto, lastCafeina, stats.get(0));
        animateStatChange(labelEstudios, labelEstudiosTexto, lastEstudios, stats.get(1));
        animateStatChange(labelPopularidad, labelPopularidadTexto, lastPopularidad, stats.get(2));
        animateStatChange(labelDinero, labelDineroTexto, lastDinero, stats.get(3));

        // ⭐ Tu código original (NO modificado)
        setStatValue(1, stats.get(0));
        setStatValue(2, stats.get(1));
        setStatValue(3, stats.get(2));
        setStatValue(4, stats.get(3));

        // ⭐ Guardar valores nuevos para la próxima comparación
        lastCafeina = stats.get(0);
        lastEstudios = stats.get(1);
        lastPopularidad = stats.get(2);
        lastDinero = stats.get(3);
    }


    private void animateStatChange(HBox container, Label label, int oldValue, int newValue) {

        label.setText(newValue + "%");

        if (oldValue == newValue) return;

        String color = newValue > oldValue ? "#4CAF50" : "#E53935"; // verde o rojo

        container.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 8;");

        FadeTransition ft = new FadeTransition(Duration.millis(600), container);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setAutoReverse(true);
        ft.setCycleCount(2);

        ft.setOnFinished(e -> container.setStyle("")); // volver al estilo normal

        ft.play();
    }


    // ================================================================
    //                         ÁRBOL DE DECISIONES
    // ================================================================
    public void initTree(DecisionTree<?> tree) {
        this.logicTree = tree;
        this.currentNode = tree.getRoot();
        this.previewNode = currentNode;   // ⭐ NUEVO

        visualTree = new VisualTree(tree, scrollArbol);
        VBox wrapper = new VBox(visualTree);
        wrapper.setAlignment(Pos.TOP_CENTER); // ⭐ pega el canvas arriba

        treeContainer.getChildren().add(wrapper);

        visualTree.drawTree(currentNode,true);
        visualTree.focusNode(currentNode);
    }

    private Canvas createTreeCanvas() {
        return new Canvas(2000, 2000);
    }

    public void loadEvent(PrincipalData data) {
        decisionEnviada = false;
        this.data = data;
        setStats(data.getStats());

        if (data != null && data.getMessages() != null) {
            Menssage menssage = data.getMessages().get(0);
            addMessageAnimated(menssage.getNameAutor(), menssage.getText(), menssage.getAvatarAutor(), data.getPathEscenary());
        }

        labelDecisionMessage.setText("");
        clearSelection();
    }

    // ================================================================
    //                         DECISIONES
    // ================================================================
    @FXML
    private void onOptionYesClick() {
        SoundManager.playEffect("/sound/button_09-190435.mp3");
        if (decisionEnviada) return;

        if (data != null && data.getMessages().size() > 1) {
            String text = data.getMessages().get(1).getText();
            selectedOptionText = (text == null || text.isEmpty()) ? "Nada cargado" : text;
        } else {
            selectedOptionText = "Hemos terminado de hablar";
        }

        selectedOption = 1;
        updateDecisionDisplay();

        // ⭐ VISTA PREVIA SIN MOVER EL NODO REAL
        if (currentNode != null && currentNode.getLeft() != null) {
            previewNode = currentNode.getLeft();
            visualTree.drawTree(previewNode, true);
            visualTree.focusNode(previewNode);
        }
    }

    @FXML
    private void onOptionNoClick() {
        SoundManager.playEffect("/sound/error-call-to-attention-129258.mp3");
        if (decisionEnviada) return;

        if (data != null && data.getMessages().size() > 2) {
            String text = data.getMessages().get(2).getText();
            selectedOptionText = (text == null || text.isEmpty()) ? "Nada cargado" : text;
        } else {
            selectedOptionText = "Hemos terminado de hablar";
        }

        selectedOption = 2;
        updateDecisionDisplay();

        // ⭐ VISTA PREVIA SIN MOVER EL NODO REAL
        if (currentNode != null && currentNode.getRight() != null) {
            previewNode = currentNode.getRight();
            visualTree.drawTree(previewNode, true);
            visualTree.focusNode(previewNode);
        }
    }

    private void updateDecisionDisplay() {
        labelDecisionMessage.setText(selectedOptionText);
        btnSendDecision.setDisable(false);
        btnSendDecision.setVisible(true);
        updateOptionStyles();
    }

    private void clearSelection() {
        selectedOption = 0;
        selectedOptionText = "";
        labelDecisionMessage.setText("");
        btnSendDecision.setDisable(true);
        btnSendDecision.setVisible(false);
        updateOptionStyles();
    }

    private void updateOptionStyles() {
        btnOptionYes.getStyleClass().remove("decision-selected");
        btnOptionNo.getStyleClass().remove("decision-selected");

        if (selectedOption == 1) btnOptionYes.getStyleClass().add("decision-selected");
        if (selectedOption == 2) btnOptionNo.getStyleClass().add("decision-selected");
    }

    private void styleOptionButtons() {
        if (!btnOptionYes.getStyleClass().contains("decision-btn"))
            btnOptionYes.getStyleClass().add("decision-btn");
        if (!btnOptionNo.getStyleClass().contains("decision-btn"))
            btnOptionNo.getStyleClass().add("decision-btn");
    }

    @FXML
    private void onSendDecisionClick() {
        SoundManager.playEffect("/sound/interface-2-126517.mp3");
        if (selectedOption == 0 || decisionEnviada) return;

        decisionEnviada = true;

        // ⭐ AHORA SÍ SE MUEVE EL NODO REAL
        currentNode = previewNode;
        visualTree.drawTree(currentNode, true);
        visualTree.focusNode(currentNode);

        addMessageAnimated("Tú", selectedOptionText, AVATAR_PLAYER, null);

        btnOptionYes.setDisable(true);
        btnOptionNo.setDisable(true);

        labelDecisionMessage.setText("");

        if (btnContinuar != null) {
            btnContinuar.setVisible(true);
            btnContinuar.setManaged(true);
        }
        btnSendDecision.setVisible(false);
        System.out.println(selectedOption);
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    @FXML
    private void onContinuarClick() {
        SoundManager.playEffect("/sound/beep-6-96243.mp3");
        if (btnContinuar != null) {
            btnContinuar.setVisible(false);
            btnContinuar.setManaged(false);
        }
        if (decisionListener != null) {
            decisionListener.onDecisionSelected(selectedOption);
        }
        clearSelection();
    }

    public interface ContinuarListener {
        void onContinuarSelected();
    }

    public void setContinuarListener(ContinuarListener continuarListener) {
        this.continuarListener = continuarListener;
    }

    private void addMessageAnimated(String sender, String text,
                                    String avatarPath, String imgPath) {

        HBox row = new HBox();
        row.setAlignment(sender.equalsIgnoreCase("Tú")
                ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        row.setSpacing(10);
        row.setPadding(new Insets(6));

        ImageView avatar = makeAvatar(avatarPath);

        // ============================
        // BURBUJA
        // ============================
        VBox bubble = new VBox();
        bubble.setSpacing(5);

        // ⭐ Límite horizontal REAL (la burbuja nunca será más ancha que esto)
        bubble.setMaxWidth(rootPane.getWidth() * 0.55);

        // ⭐ La burbuja se adapta si cambia el tamaño de la ventana
        bubble.maxWidthProperty().bind(rootPane.widthProperty().multiply(0.55));

        bubble.getStyleClass().add(
                sender.equalsIgnoreCase("Tú") ? "burbuja-player" : "burbuja-npc"
        );

        // ============================
        // TEXTO
        // ============================
        Label lblText = new Label(text);
        lblText.setWrapText(true);

        // ⭐ El texto también tiene límite horizontal
        lblText.maxWidthProperty().bind(rootPane.widthProperty().multiply(0.50));

        lblText.getStyleClass().add("label-mensaje");

        bubble.getChildren().add(lblText);

        // ============================
        // IMAGEN OPCIONAL
        // ============================
        if (imgPath != null && !imgPath.isEmpty()) {
            ImageView iv = new ImageView(safeLoadImage(imgPath));
            iv.setFitWidth(300);
            iv.setPreserveRatio(true);
            bubble.getChildren().add(iv);
        }

        // ============================
        // ORDEN SEGÚN QUIÉN HABLA
        // ============================
        if (sender.equalsIgnoreCase("Tú"))
            row.getChildren().addAll(bubble, avatar);
        else
            row.getChildren().addAll(avatar, bubble);

        // ============================
        // ANIMACIÓN
        // ============================
        row.setOpacity(0);
        row.setTranslateY(12);
        vboxMensajes.getChildren().add(row);

        FadeTransition ft = new FadeTransition(Duration.millis(250), row);
        ft.setFromValue(0);
        ft.setToValue(1);

        TranslateTransition tt = new TranslateTransition(Duration.millis(250), row);
        tt.setFromY(12);
        tt.setToY(0);

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
        if (path == null || path.isEmpty()) return null;

        try {
            if (!path.startsWith("/")) path = "/" + path;
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) return new Image(is);
        } catch (Exception ignored) {
        }

        return null;
    }

    public interface MenuPrincipalListener {
        void onSalirAlMenu();
    }

    private MenuPrincipalListener menuListener;

    public void setMenuListener(MenuPrincipalListener listener) {
        this.menuListener = listener;
    }

    @FXML
    private void onSalirMenuClick() {
        if (menuListener != null) {
            SoundManager.playEffect("/sound/button_09-190435.mp3");
            menuListener.onSalirAlMenu();
        }
    }

    //=============================================================
    //                 LIMPIAR ÁRBOL
    //=============================================================
    public void clearTree() {
        // 1. Borrar el canvas
        if (visualTree != null) {
            GraphicsContext gc = visualTree.getGraphicsContext2D();
            gc.clearRect(0, 0, visualTree.getWidth(), visualTree.getHeight());
        }

        // 2. Borrar el contenedor
        treeContainer.getChildren().clear();

        // 3. Resetear referencias
        visualTree = null;
        currentNode = null;
        previewNode = null;

        // 4. Resetear scroll
        scrollArbol.setHvalue(0);
        scrollArbol.setVvalue(0);
    }

    @FXML
    private void onInfoTeamClick(){
        SoundManager.playEffect("/sound/button_09-190435.mp3");
        showTeamInfoDialog();
    }

    @FXML
    private void onInfoArbolClick(){
        SoundManager.playEffect("/sound/button_09-190435.mp3");
        createDecisionTreeInfoPanel();
    }

    @FXML
    private void onMenuSuperiorClick(){
        SoundManager.playEffect("/sound/button_09-190435.mp3");
    }
}
