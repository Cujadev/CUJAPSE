package main;

import interfaz.controllers.MenuInicioController;
import interfaz.controllers.PrincipalController;
import interfaz.controllers.TutorialController;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import interfaz.sounds.SoundManager;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.input.KeyEvent;
import logic.auxiliars.dataOfInterfaces.PrincipalData;
import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;
import logic.clases.event.Event;
import logic.clases.event.Situation;
import logic.clases.game.Game;
import javafx.application.Application;
import logic.clases.game.Scenary;

import java.io.IOException;
import java.util.List;

public class GameControler extends Application implements MenuInicioController.MenuInicioListener, PrincipalController.DecisionListener {
    private static GameControler intance;
    private Game game;
    private Stage primaryStage;

    private Scenary scenary;
    private PrincipalController principalController;
    private boolean inTutorial;
    private PrincipalData rep;

    // ⭐ Una sola escena global
    private Scene mainScene;

    private Integer ultimaDesicion;

    public GameControler() {
        this.game = Game.getInstance();
        ultimaDesicion = null;
    }

    public static GameControler getInstance() {
        if (intance == null) {
            intance = new GameControler();
        }
        return intance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        StackPane emptyRoot = new StackPane();
        mainScene = new Scene(emptyRoot);

        primaryStage.setScene(mainScene);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);

        primaryStage.show();

        showSplashScreen(this::showMainMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onMenuOptionSelected(int codigo) {
        switch (codigo) {
            case 1 -> iniciarNuevaPartida();
            case 2 -> cargarPartida();
            case 3 -> salirDelJuego();
        }
    }

    private void iniciarNuevaPartida() {
        System.out.println("Iniciando partida");
        List<String> stringList = game.startNewGame();
        this.scenary = game.getScenary();
        inTutorial = true;

        PrincipalData data = scenary.giveData(0);
        System.out.println(data.getMessages().get(0).getText());
        // Mostrar pantalla de carga y luego el tutorial
        showLoadingScreen(() -> showTutorial(stringList, () -> showPrincipal(data, scenary.getEvent().getSituations())));
    }

    private void cargarPartida() {
        System.out.println("Cargando partida...");
    }

    private void salirDelJuego() {
        System.out.println("Saliendo del juego...");
        System.exit(0);
    }

    private void showTutorial(List<String> dialogues, Runnable onFinish) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/Tutorial.fxml"));
            Parent root = loader.load();
            TutorialController controller = loader.getController();

            controller.setDialogLines(dialogues);
            controller.startTutorial();

            primaryStage.setTitle("Tutorial");
            mainScene.setRoot(root);


            controller.setListener(() -> {
                rep = scenary.giveData(2);
                scenary.getEvent().resetEvent();
                showLoadingScreen(() -> showPrincipal(scenary.giveData(0), scenary.getEvent().getSituations()));
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showPrincipal(PrincipalData data, DecisionTree<Situation> decisionTree) {
        try {
            SoundManager.stopBackground();
            SoundManager.playBackground("/sound/principal.mp3");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/Principal.fxml"));
            Parent root = loader.load();
            PrincipalController controller = loader.getController();

            controller.setStats(data.getStats());
            controller.loadEvent(data);
            controller.setDecisionListener(this);
            controller.setContinuarListener(() -> {
                int opt = controller.getSelectedOption();
                if (opt > 0) {
                    onDecisionSelected(opt);
                }
            });

            controller.setMenuListener(this::volverAlMenuInicial);
            primaryStage.setTitle("Principal");
            controller.initTree(decisionTree);

            mainScene.setRoot(root);

            principalController = controller;
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            primaryStage.setFullScreen(true);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showMainMenu() {
        try {
            SoundManager.playBackground("/sound/menuInicio.mp3");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/MenuInicio.fxml"));
            Parent root = loader.load();
            MenuInicioController controller = loader.getController();
            controller.setListener(this);

            primaryStage.setTitle("Menú Inicio");
            mainScene.setRoot(root);
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            primaryStage.setFullScreen(true);
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDecisionSelected(int codigo) {
        System.out.println("Decision selected: " + codigo);
        ultimaDesicion = codigo;
        if (inTutorial) {
            loopDecisiones(codigo);
        } else {
            processResult(codigo);
        }
    }

    private void loopDecisiones(int result) {
        if (result == 2) {
            principalController.loadEvent(rep);
            principalController.habilitarOpciones();
        } else if (result == 1) {
            System.out.println("Decisión buena, avanzamos...");
            principalController.clearTree();
            principalController.initTree(scenary.getEvent().getSituations());
            principalController.loadEvent(scenary.giveData(1));
            playGame();
        }
    }

    private void playGame() {
        inTutorial = false;
        this.scenary.setEvent(game.getNextEvent());
        showPrincipal(scenary.giveData(0), scenary.getEvent().getSituations());
    }

    private void processResult(int result) {
        Event current = scenary.getEvent();
        DecisionNode<Situation> actual = current.getActualSituation();

        if (!actual.isLeaf()) {
            scenary.callModificationStats(result);
            if (!scenary.isHeroDeath()) {
                if (result == 1) {
                    principalController.loadEvent(scenary.giveData(1));
                    principalController.habilitarOpciones();
                } else {
                    principalController.loadEvent(scenary.giveData(2));
                    principalController.habilitarOpciones();
                }
            } else {
                System.out.println("Muerte detectada en nodo normal con el dialogo: " + scenary.giveData(0).getMessages().get(0) + "\n Con las stats: " + scenary.giveData(0).getStats());
                showDeath(()-> showMainMenu());
            }
        } else {
            System.out.println("nodo hoja detectado, cambiando escenario");
            if (!game.getEventQueue().isEmpty()) {
                scenary.setEvent(game.getNextEvent());
                showLoadingScreen(() -> showPrincipal(scenary.giveData(0), scenary.getEvent().getSituations()));
            } else {
                endGame();
            }
        }
    }

    private void showLoadingScreen(Runnable onFinish) {
        try {
            Image[] frames = new Image[]{
                    new Image(getClass().getResource("/visualResources/pantallaCarga/frame1.png").toExternalForm()),
                    new Image(getClass().getResource("/visualResources/pantallaCarga/frame2.png").toExternalForm()),
                    new Image(getClass().getResource("/visualResources/pantallaCarga/frame3.png").toExternalForm()),
                    new Image(getClass().getResource("/visualResources/pantallaCarga/frame4.png").toExternalForm()),
                    new Image(getClass().getResource("/visualResources/pantallaCarga/frame5.png").toExternalForm()),
                    new Image(getClass().getResource("/visualResources/pantallaCarga/frame6.png").toExternalForm()),
                    new Image(getClass().getResource("/visualResources/pantallaCarga/frame7.png").toExternalForm())
            };

            ImageView view = new ImageView(frames[0]);
            view.fitWidthProperty().bind(primaryStage.widthProperty());
            view.fitHeightProperty().bind(primaryStage.heightProperty());
            view.setPreserveRatio(false);

            StackPane root = new StackPane(view);
            root.setStyle("-fx-background-color: black;");
            mainScene.setRoot(root);

            Timeline frameAnimation = new Timeline();
            int frameDuration = 120;

            for (int i = 0; i < frames.length; i++) {
                int index = i;
                frameAnimation.getKeyFrames().add(
                        new KeyFrame(Duration.millis(i * frameDuration),
                                e -> view.setImage(frames[index]))
                );
            }

            frameAnimation.setCycleCount(4);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), root);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);

            SequentialTransition seq = new SequentialTransition(fadeIn, frameAnimation, fadeOut);
            seq.setOnFinished(e -> onFinish.run());
            seq.play();

        } catch (Exception e) {
            e.printStackTrace();
            onFinish.run();
        }
    }

    private void showSplashScreen(Runnable onFinish) {
        // Imagen de portada
        ImageView portada = new ImageView(
                new Image(getClass().getResource("/visualResources/escenarios/portada.png").toExternalForm())
        );

        portada.setPreserveRatio(true);
        portada.fitWidthProperty().bind(primaryStage.widthProperty());
        portada.fitHeightProperty().bind(primaryStage.heightProperty());

        // Texto "Presione cualquier tecla para continuar..."
        Label pressKey = new Label("Presione cualquier tecla para continuar...");
        pressKey.setStyle("-fx-font-size: 28px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Animación de parpadeo
        FadeTransition ft = new FadeTransition(Duration.seconds(1.2), pressKey);
        ft.setFromValue(1);
        ft.setToValue(0.2);
        ft.setCycleCount(FadeTransition.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        StackPane root = new StackPane(portada, pressKey);
        StackPane.setAlignment(pressKey, Pos.BOTTOM_CENTER);
        StackPane.setMargin(pressKey, new Insets(0, 0, 60, 0));

        mainScene.setRoot(root);

        // ⭐ Cualquier tecla
        mainScene.setOnKeyPressed(event -> {
            onFinish.run();
            limpiarHandlers();
        });

        // ⭐ Cualquier clic del mouse
        mainScene.setOnMouseClicked(event -> {
            onFinish.run();
            limpiarHandlers();
        });
    }


        /*
        // Duración de la portada
        Timeline wait = new Timeline(new KeyFrame(Duration.seconds(5), e -> onFinish.run()));
        wait.play();*/



    private void volverAlMenuInicial() {
        showMainMenu();
    }

    private void showDeath(Runnable onFinish) {
        try {
            Image i = new Image(getClass().getResource(scenary.giveDeath()).toExternalForm());
            ImageView view = new ImageView(i);
            view.fitWidthProperty().bind(primaryStage.widthProperty());
            view.fitHeightProperty().bind(primaryStage.heightProperty());
            view.setPreserveRatio(false);
            StackPane root = new StackPane(view);
            root.setStyle("-fx-background-color: black;");
            mainScene.setRoot(root); // Fade in

            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), root);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1); // Pausa de 5 segundos
            PauseTransition pause = new PauseTransition(Duration.seconds(5));

            FadeTransition fadeOut = new FadeTransition(Duration.millis(400), root);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0); // Secuencia completa
            SequentialTransition seq = new SequentialTransition(fadeIn, pause, fadeOut);
            seq.setOnFinished(e -> onFinish.run());
            seq.play();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void endGame() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Juego Terminado");
            alert.setHeaderText(null);
            alert.setContentText("Felicidades has terminado Cujapse. Si no entendiste nada, yo tampoco. Pero de parte del equipo te agradecemos por jugar");
            alert.showAndWait();
            showMainMenu();
        });
    }

    private void limpiarHandlers() {
        mainScene.setOnKeyPressed(null);
        mainScene.setOnMouseClicked(null);
    }

}

