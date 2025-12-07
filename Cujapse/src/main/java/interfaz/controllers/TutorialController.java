package interfaz.controllers;
<<<<<<< HEAD
<<<<<<< HEAD

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TutorialController {

    // --------------------------- FXML ----------------------------
    @FXML private AnchorPane rootPane;

    @FXML private ImageView imgPersonaje;  // FONDO
    @FXML private ImageView imgOmar;       // OMAR (se desliza)
    @FXML private ImageView imgAppPreview; // PREVIEW ORCHAT (zoom)

    @FXML private Label labelDialogo;
    @FXML private Button btnContinuar;
    @FXML private Button btnNo;
    @FXML private HBox hboxBotones;

    // --------------------------- TUTORIAL -------------------------
    private final List<String> lineas = new ArrayList<>();
    private int index = 0;

    // TRIGGERS
    private static final String TRIGGER_PREVIEW = "[SHOW_APP_PREVIEW]";
    private static final String TRIGGER_LLAMADO = "[CALL_PLAYER]";
    private static final String TRIGGER_PREGUNTA_FINAL = "[END_QUESTION]";

    private static final String MENSAJE_FINAL =
            "Se espera que los mejores estudiantes sean los que sepan mantener en equilibrio estos cuatro aspectos...";

    private static final String RESPUESTA_NO =
            "No hay mucha complicación. Cada vez que hables en la app se afectan estas 4 estadísticas de variadas formas. "
                    + "¿Ya entiendes?";

    private boolean escribiendo = false;  // Para controlar el typewriter

    // --------------------------------------------------------------
    //                INICIALIZACIÓN
    // --------------------------------------------------------------
    @FXML
    public void initialize() {
        prepararGuion();
        btnNo.setVisible(false);
        imgAppPreview.setVisible(false);
        activarEscaladoAutomatico();
        // Omar entra deslizando
        animarEntradaOmar();

        mostrarSiguienteLinea();
    }

    // --------------------------------------------------------------
    //                GUION COMPLETO DEL TUTORIAL
    // --------------------------------------------------------------
    private void prepararGuion() {
        lineas.add("En los bajos de la facultad el presidente...");
        lineas.add("Se omariza (aparece) y con un tono relajado...");
        lineas.add("Buenas, estudiantes, sean bienvenidos...");
        lineas.add("Bueno, yo soy el presidente de la FEU...");
        lineas.add("Ahora tengo la tarea de explicarles cómo funciona...");

        lineas.add("Para esto hemos creado “Orchat” una app disponible en apiklis.");

        lineas.add(TRIGGER_PREVIEW);

        lineas.add("Muchos deben estar intranquilos sobre cómo la app se mantiene funcionando...");
        lineas.add("En esta app ustedes estarán comunicándose sobre cosas...");
        lineas.add("Esto fue a petición del profesor del laboratorio...");
        lineas.add("Nada de qué preocuparse...");

        lineas.add("Primero tenemos los estudios claramente...");
        lineas.add("La otra característica es la popularidad...");
        lineas.add("Ahora hablemos del dinero...");
        lineas.add("Como presidente de la FEU les recomiendo...");
        lineas.add("También… se sabe que cada universitario no es nada sin su café...");
        lineas.add("Vamos, que quiero mostrarles a sus compañeros como funciona la app bien.");

        lineas.add(TRIGGER_LLAMADO);

        lineas.add(TRIGGER_PREGUNTA_FINAL);
    }

    // --------------------------------------------------------------
    //                 SLIDE-IN DE OMAR
    // --------------------------------------------------------------
    private void animarEntradaOmar() {
        imgOmar.setTranslateX(-500);

        TranslateTransition tt = new TranslateTransition(Duration.millis(900), imgOmar);
        tt.setToX(0);
        tt.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition ft = new FadeTransition(Duration.millis(900), imgOmar);
        ft.setFromValue(0);
        ft.setToValue(1);

        new ParallelTransition(tt, ft).play();
    }

    // --------------------------------------------------------------
    //                CONTROL PRINCIPAL DEL TUTORIAL
    // --------------------------------------------------------------
    private void mostrarSiguienteLinea() {
        if (index >= lineas.size()) {
            irAPantallaPrincipal();
            return;
        }
        String linea = lineas.get(index);
        // TRIGGERS
        if (manejarTriggers(linea)) return;
        // TYPEWRITER
        escribirTexto(linea);
        index++;
    }

    private boolean manejarTriggers(String linea) {
        switch (linea) {
            case TRIGGER_PREVIEW -> {
                imgAppPreview.setVisible(true);
                animarAparecerPreview();
                index++;
                mostrarSiguienteLinea();
                return true;
            }
            case TRIGGER_LLAMADO -> {
                imgAppPreview.setVisible(true);
                animarZoomApp(this::irAPantallaPrincipal);
                return true;
            }
            case TRIGGER_PREGUNTA_FINAL -> {
                escribirTexto("¿Entiendes cómo funciona la app?");
                prepararDecisionFinal();
                index++;
                return true;
            }
        }

        return false;
    }

    // --------------------------------------------------------------
    //                        TYPEWRITER
    // --------------------------------------------------------------
    private void escribirTexto(String texto) {
        escribiendo = true;
        labelDialogo.setText("");

        int total = texto.length();
        int[] i = {0};

        Timeline t = new Timeline(new KeyFrame(Duration.millis(22), e -> {
            if (i[0] < total) {
                labelDialogo.setText(labelDialogo.getText() + texto.charAt(i[0]));
                i[0]++;
            } else {
                escribiendo = false;
            }
        }));

        t.setCycleCount(total);
        t.play();
    }

    // --------------------------------------------------------------
    //                      PREVIEW ORCHAT
    // --------------------------------------------------------------
    private void animarAparecerPreview() {
        FadeTransition ft = new FadeTransition(Duration.millis(600), imgAppPreview);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    // --------------------------------------------------------------
    //                      ZOOM “ABRIR APP”
    // --------------------------------------------------------------
    private void animarZoomApp(Runnable alTerminar) {
        ScaleTransition st = new ScaleTransition(Duration.millis(900), imgAppPreview);
        st.setFromX(1); st.setFromY(1);
        st.setToX(3.5); st.setToY(3.5);
        st.setInterpolator(Interpolator.EASE_IN);

        FadeTransition fadeFondo = new FadeTransition(Duration.millis(700), imgPersonaje);
        fadeFondo.setToValue(0);

        ParallelTransition pt = new ParallelTransition(st, fadeFondo);
        pt.setOnFinished(e -> alTerminar.run());
        pt.play();
    }

    // --------------------------------------------------------------
    //                      BOTONES SI/NO
    // --------------------------------------------------------------
    private void prepararDecisionFinal() {
        btnContinuar.setText("Sí");
        btnNo.setVisible(true);
    }

    @FXML
    private void onContinuarClick() {
        if (escribiendo) {
            escribiendo = false;
            return;
        }

        if (btnContinuar.getText().equals("Sí")) {
            escribirTexto(MENSAJE_FINAL);

            btnContinuar.setVisible(false);
            btnNo.setVisible(false);

            PauseTransition p = new PauseTransition(Duration.seconds(4));
            p.setOnFinished(e -> irAPantallaPrincipal());
            p.play();
        } else {
            mostrarSiguienteLinea();
        }
    }

    @FXML
    private void onNoClick() {
        escribirTexto(RESPUESTA_NO);
    }

    // --------------------------------------------------------------
    //                  PASAR A PANTALLA PRINCIPAL
    // --------------------------------------------------------------
    private void irAPantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cujapse/views/JuegoPrincipal.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------
    //                 AUTOESCALADO FULLSCREEN
    // --------------------------------------------------------------

    private void activarEscaladoAutomatico() {
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Stage stage = (Stage) newScene.getWindow();
                stage.widthProperty().addListener((a, b, c) -> escalar(stage));
                stage.heightProperty().addListener((a, b, c) -> escalar(stage));
            }
        });
    }

    private void escalar(Stage stage) {
        double baseW = 923, baseH = 564;

        double scaleX = stage.getWidth() / baseW;
        double scaleY = stage.getHeight() / baseH;
        double scale = Math.min(scaleX, scaleY);

        rootPane.setScaleX(scale);
        rootPane.setScaleY(scale);

        rootPane.setLayoutX((stage.getWidth() - baseW * scale) / 2);
        rootPane.setLayoutY((stage.getHeight() - baseH * scale) / 2);
=======

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller de la pantalla Tutorial.Aquí están los métodos para manejar el flujo del tutorial(FXML).
 * Para manejar la pantalla de tutorial, el MVC debe interactuar con estos métodos:
 *  - setDialogLines(...)
 *  - startTutorial(...)
 *  - onTutorialFinishedCallback(...)
 */
public class TutorialController implements Initializable {

    @FXML private Label labelDialogo;
    @FXML private Button btnContinuar;

    /** Lista de diálogos a mostrar en el tutorial */
    private List<String> dialogLines;
    private int index = 0;

    /* Runnable es el tipo de dato para una interfaz funcional
    * *De esta forma cuando se finalice el tutorial ,que se vaya a pasar a la pantalla principal ,tan solo es pasar el FXML
    * por parametros.Y cerrara el tutorial y abrirá la pantalla principal */
    private Runnable onFinish;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelDialogo.setText("");
    }

    /**
     * El Modelo debe pasar las líneas del tutorial aquí.
     */
    public void setDialogLines(List<String> lines) {
        this.dialogLines = lines;
        this.index = 0;
    }

    /**
     * Iniciar el tutorial mostrando la primera línea.
     */
    public void startTutorial() {
        if (dialogLines != null && !dialogLines.isEmpty()) {
            labelDialogo.setText(dialogLines.get(0));
        }
    }

    /**
     * Registra qué debe pasar cuando el tutorial termina
     * (por ejemplo: abrir la interfaz principal).
     */
    public void setOnTutorialFinish(Runnable r) {
        this.onFinish = r;
    }

    //Interacción dle usuario para pasar a la siguiente línea del tutorial

    @FXML
    private void onContinuarClick() {
        boolean puedeContinuar = dialogLines != null && !dialogLines.isEmpty();

        if (puedeContinuar) {
            index++;

            if (index < dialogLines.size()) {
                labelDialogo.setText(dialogLines.get(index));
            } else {
                if (onFinish != null) {
                    onFinish.run();
                }
            }
        }
>>>>>>> 7a7600f (interfaces 3)
    }
}
=======
public class TutorialController {

}
>>>>>>> 0cca16d2fec70aadf4769c45c401c3dd5fcdaa44
