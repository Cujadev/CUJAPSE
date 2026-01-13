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
import logic.auxiliars.dataOfInterfaces.PrincipalData;
import logic.auxiliars.files.ProgressManager;
import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;
import logic.clases.event.Event;
import logic.clases.event.Situation;
import logic.clases.game.Game;
import javafx.application.Application;
import logic.clases.game.Scenary;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GameControler extends Application implements MenuInicioController.MenuInicioListener, PrincipalController.DecisionListener {
    private static GameControler instance;
    private Game game;
    private Stage primaryStage;

    private Scenary scenary;
    private PrincipalController principalController;
    private boolean inTutorial;
    private PrincipalData rep;
    private MenuInicioController menuInicioController;

    // ⭐ Una sola escena global
    private Scene mainScene;

    private Integer lastDecision;

    public GameControler() {
        this.game = Game.getInstance();
        lastDecision = null;
    }

    public static GameControler getInstance() {
        if (instance == null) {
            instance = new GameControler();
        }
        return instance;
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

        showIntroScreen(() -> showSplashScreen(this::showMainMenu));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void onMenuOptionSelected(int codigo) {
        switch (codigo) {
            case 1 -> StartNewGame();
            case 2 -> ChargeGame();
            case 3 -> Exit();
        }
    }

    private void StartNewGame() {
        SoundManager.stopBackground();
        System.out.println("Iniciando partida");
        List<String> stringList = game.startNewGame();
        this.scenary = game.getScenary();
        inTutorial = true;

        PrincipalData data = scenary.giveData(0);
        System.out.println(data.getMessages().get(0).getText());
        // Mostrar pantalla de carga y luego el tutorial
        showLoadingScreen(() -> showTutorial(stringList, () -> showPrincipal(data, scenary.getEvent().getSituations())));
    }

    private void ChargeGame() {
        System.out.println("Cargando partida...");
        SoundManager.stopBackground();
        boolean canCharge = game.startGame();
        if (!canCharge) {
            showCanotCharge(game.getEventQueue().size());
            menuInicioController.getBtnCargarPartida().setDisable(true);
        }
        else {
            this.scenary = game.getScenary();
            showLoadingScreen(() -> playGame());
        }
    }

    private void Exit() {
        System.out.println("Saliendo del juego...");
        System.exit(0);
    }

    private void showTutorial(List<String> dialogues, Runnable onFinish) {
        try {
            SoundManager.stopBackground();
            SoundManager.playBackground("/sound/tutorial.mp3");
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

            controller.setMenuListener(this::returnToInitialMenu);
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
            SoundManager.stopBackground();
            SoundManager.playBackground("/sound/menuInicio.mp3");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/MenuInicio.fxml"));
            Parent root = loader.load();
            menuInicioController = loader.getController();
            menuInicioController.setListener(this);

            File file = new File("data/SavedPlays/Save.dat");

            if (file.exists()) {
                menuInicioController.getBtnCargarPartida().setDisable(false);
            } else {
                menuInicioController.getBtnCargarPartida().setDisable(true);
            }

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
    public void onDecisionSelected(int id) {
        System.out.println("Decision selected: " + id);
        lastDecision = id;
        if (inTutorial) {
            loopDecisions(id);
        } else {
            processResult(id);
        }
    }

    private void loopDecisions(int result) {
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
                showDeath(() -> showMainMenu());
            }
        } else {
            System.out.println("Nodo hoja detectado, cambiando escenario");
            if (!game.getEventQueue().isEmpty()) {
                game.getMainCharacter().addEvent(scenary.getEvent().getIdEvent());
                try {
                    ProgressManager.savePlay(game.getMainCharacter());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                scenary.setEvent(game.getNextEvent());
                showLoadingScreen(() -> showPrincipal(scenary.giveData(0), scenary.getEvent().getSituations()));
            } else {
                game.getMainCharacter().addEvent(scenary.getEvent().getIdEvent());
                try {
                    ProgressManager.savePlay(game.getMainCharacter());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
        SoundManager.playBackground("/sound/menuInicio.mp3");
        // Imagen de cover
        ImageView cover = new ImageView(
                new Image(getClass().getResource("/visualResources/escenarios/portada.png").toExternalForm())
        );

        cover.setPreserveRatio(true);
        cover.fitWidthProperty().bind(primaryStage.widthProperty());
        cover.fitHeightProperty().bind(primaryStage.heightProperty());

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

        StackPane root = new StackPane(cover, pressKey);
        StackPane.setAlignment(pressKey, Pos.BOTTOM_CENTER);
        StackPane.setMargin(pressKey, new Insets(0, 0, 60, 0));

        mainScene.setRoot(root);

        // ⭐ Cualquier tecla
        mainScene.setOnKeyPressed(event -> {
            SoundManager.playEffect("/sound/button_09-190435.mp3");
            onFinish.run();
            limpiarHandlers();
        });

        // ⭐ Cualquier clic del mouse
        mainScene.setOnMouseClicked(event -> {
            SoundManager.playEffect("/sound/button_09-190435.mp3");
            onFinish.run();
            limpiarHandlers();
        });
    }


        /*
        // Duración de la portada
        Timeline wait = new Timeline(new KeyFrame(Duration.seconds(5), e -> onFinish.run()));
        wait.play();*/


    private void returnToInitialMenu() {
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
            // Imagen final (banner de cierre)
            ImageView endImage = new ImageView(
                    new Image(getClass().getResource("/visualResources/escenarios/juegoTerminado.png").toExternalForm())
            );
            endImage.setPreserveRatio(true);
            endImage.fitWidthProperty().bind(primaryStage.widthProperty());
            endImage.fitHeightProperty().bind(primaryStage.heightProperty());

            // Texto parpadeante
            Label pressKey = new Label("Presione cualquier tecla para volver al Menú Principal...");
            pressKey.setStyle("-fx-font-size: 28px; -fx-text-fill: white; -fx-font-weight: bold;");

            FadeTransition ft = new FadeTransition(Duration.seconds(1.2), pressKey);
            ft.setFromValue(1);
            ft.setToValue(0.2);
            ft.setCycleCount(FadeTransition.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();

            // Contenedor
            StackPane root = new StackPane(endImage, pressKey);
            StackPane.setAlignment(pressKey, Pos.BOTTOM_CENTER);
            StackPane.setMargin(pressKey, new Insets(0, 0, 60, 0));

            // Cambiar la escena principal al splash final
            mainScene.setRoot(root);

            // ⭐ Cualquier tecla → volver al menú
            mainScene.setOnKeyPressed(event -> {
                showMainMenu();
                limpiarHandlers();
            });

            // ⭐ Cualquier clic → volver al menú
            mainScene.setOnMouseClicked(event -> {
                showMainMenu();
                limpiarHandlers();
            });
        });
    }


    private void limpiarHandlers() {
        mainScene.setOnKeyPressed(null);
        mainScene.setOnMouseClicked(null);
    }

    private void showIntroScreen(Runnable onFinish) {
        // Texto a mostrar
        String mensaje = "Este es un juego desarrollado por estudiantes de 2do año \n" + "de la carrera Ingeniería Informática\n" +
                "en la Universidad Tecnológica de La Habana,\n" + "José Antonio Echeverría, CUJAE.\n\n"+"\nLos hechos y personajes mostrados a continuación\n"+"no son reales." +
                "\nCualquier parecido a la realidad es pura coincidencia." +"\nAtte: El Guionista.";

        Label label = new Label();
        label.setStyle(
                "-fx-font-family: 'Courier New';" +   // ⭐ Fuente estilo máquina de escribir
                        "-fx-font-size: 28px;" +       // un poco más grande para fullscreen
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: black;");
        root.setAlignment(Pos.CENTER); // ⭐ asegura que todo esté centrado
        root.setPadding(new Insets(40));

        // ⭐ Usar la escena global en vez de crear una nueva
        mainScene.setRoot(root);

        // Mantener fullscreen global
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);

        // Animación tipo máquina de escribir
        Timeline timeline = new Timeline();
        for (int i = 0; i < mensaje.length(); i++) {
            final int index = i;
            KeyFrame kf = new KeyFrame(Duration.millis(50 * i), e -> {
                label.setText(mensaje.substring(0, index + 1));
            });
            timeline.getKeyFrames().add(kf);
        }

        // Al terminar la animación → continuar
        timeline.setOnFinished(e -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2)); // espera 2 segundos
            pause.setOnFinished(ev -> onFinish.run());
            pause.play();
        });

        timeline.play();
    }

    public void showCanotCharge(int cant) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("No se puede cargar partida");
        if (cant == 0) {
            alerta.setContentText("No hay eventos para cargar. Inicie una nueva partida");
        }
        else {
            alerta.setContentText("Ya se ha terminado el juego en esta partida. Inicie una nueva partida");
        }
        alerta.showAndWait();
    }


}

