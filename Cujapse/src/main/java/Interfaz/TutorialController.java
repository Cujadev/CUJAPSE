package Interfaz;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList; // Importamos ArrayList
import java.util.List;      // Importamos List

// Importa la nueva clase Dialogo
// import Interfaz.Dialogo;

public class TutorialController {

    // --- Variables FXML inyectadas (de PrologoView.fxml) ---
    @FXML private ImageView imgPersonaje;
    @FXML private ImageView imgAppPreview;
    @FXML private Label labelDialogo;
    @FXML private Button btnContinuar;
    @FXML private Button btnNo;        /
    @FXML private HBox hboxBotones;

    // --- Variables de Lógica y Flujo ---
    private List<Dialogo> lineasTutorial; // CAMBIADO de Queue<String> a List<Dialogo>
    private int indiceDialogoActual = 0;  // Nuevo índice para rastrear la posición actual

    // Constantes para identificar momentos clave
    private final String MENSAJE_OMAR_FINAL = "¿Entiendes cómo funciona la app?";
    private final String MENSAJE_PREVIEW_TRIGGER = "[SHOW_APP_PREVIEW]";
    private final String RESPUESTA_NO = "No hay mucha complicación. Cada vez que hables en la app se afectan estas 4 estadísticas de variadas formas. O sea cada vez que digas algo podrás ver como se afecta esa decisión en tu vida universitaria ¿Ya entiendes?";

    @FXML
    public void initialize() {
        prepararTutorial();
        // Inicialmente, solo se muestra el botón "Continuar..."
        btnNo.setVisible(false);
        mostrarSiguienteLineaTutorial();
    }

    private void prepararTutorial() {
        // Inicialización con ArrayList
        lineasTutorial = new ArrayList<>();

        // ** DIÁLOGO COMPLETO DE OMAR CON CONTEXTO **
        // Ahora se usa new Dialogo(...) para añadir cada línea
        lineasTutorial.add(new Dialogo("En los bajos de la facultad el presidente de la FEU de la facultad de informática se reúne con todos los estudiantes de nuevo ingreso para dar algunas indicaciones."));
        lineasTutorial.add(new Dialogo("Se omariza (aparece) y con un tono relajado habla hacia el público."));
        lineasTutorial.add(new Dialogo("Buenas, estudiantes, sean bienvenidos a la “Maravillosa” vida universitaria- (Se nota el sarcasmo) – mentira jajaja"));
        lineasTutorial.add(new Dialogo("Bueno, yo soy el presidente de la FEU de la facultad de informática y estos son los secretarios, las presentaciones se harán luego después en la reunión con la rectora."));
        lineasTutorial.add(new Dialogo("Ahora tengo la tarea de decirles como funciona la facultad. –(Se aclara la voz) – Bueno, este año es diferente a los demás, hemos implementado un sistema de chat para la facultad para que los integrantes puedan comunicarse y separar whatsap y telegram de su escuela."));
        lineasTutorial.add(new Dialogo("Para esto hemos creado “Orchat” una app disponible en “apiklis”."));

        // --- EVENTO DE APARICIÓN DE LA INTERFAZ ---
        lineasTutorial.add(new Dialogo(MENSAJE_PREVIEW_TRIGGER)); // **TRIGGER para mostrar la preview de la app**
        // --- FIN EVENTO ---

        lineasTutorial.add(new Dialogo("Muchos deben estar intranquilos sobre como la app se mantiene funcionando. Tenemos una planta así que pueden relajarse que incomunicados no van a estar nunca o bueno la mayoría del tiempo. (Se aclara la voz y esboza una sonrisa)."));
        lineasTutorial.add(new Dialogo("En esta app ustedes se van a estar comunicando sobre cosas de la facultad. Nuestra profesora de programación estrella -*susurro*- (Ya la conocerán) – ha colaborado para convertirlo casi en una red social pública donde según tus acciones y comentarios los otros pueden votar sobre 4 estadísticas."));
        lineasTutorial.add(new Dialogo("Esto fue a petición de el profesor del laboratorio de impacto social que está haciendo una investigación sobre como ciertos aspectos impactan la vida universitaria."));
        lineasTutorial.add(new Dialogo("Nada de que preocupase, solo nos importa la parte científica de como afectan estas características al estudiante algunos aspectos de la vida universitaria para a posterior poder mejorar la experiencia dentro de la CUJAE. ¿Les parece si les hablo de ellas?"));

        lineasTutorial.add(new Dialogo("Primero tenemos los estudios claramente...es la base de la vida universitaria. “Si no estudio desapruebo y si no estudio y apruebo soy dichoso” es la frase favorita de uno de los profesores de la asignatura de matemática discreta. En lo personal, recomiendo que estudien lo suficiente, pero recuerden que la vida no es solo estudio. Por experiencia personal si no dedicas tiempo a otras cosas, sin dejar de lado este aspecto claramente pueden afectar a su salud."));
        lineasTutorial.add(new Dialogo("La otra característica es la popularidad…no hay mucho que decir de esta, si haces publicaciones y recibes muchos votos positivos más popular eres, aunque hicieron un aspecto que también en la sección de amigos que también afecta a esta estadística. En esta facultad le damos bastante prioridad al trabajo en equipo…yo le hubiera puesto “Social” en vez de popularidad…pero ganó la democracia."));
        lineasTutorial.add(new Dialogo("Ahora hablemos de el dinero…lamentablemente esta es la es la mas polémica de las métricas por lo que la comunidad no vota directamente en ella. Sin embargo en el laboratorio están muy interesados en saber cómo es la situación económica de los estudiantes de la facultad para que podamos tener acciones en función de su comunidad."));
        lineasTutorial.add(new Dialogo("Como presidente de la FEU les recomiendo que vigilen sus ingresos, ya somos adultos y papi y mami no nos ayudan tanto con el dinero. Un día me quede esperando dos horas la guagua que no paso porque no tuve dinero para una gacela, luego me compré moto, pero sigo teniendo esa parte humilde en mi corazón, también me di cuenta que venir en bicicleta es bueno 2 o 3 veces, pero todos los días es…cansado."));
        lineasTutorial.add(new Dialogo("También…se sabe que cada universitario no es nada sin su café a las 3 am… a muchos les gusta publicar los frappuchinos que se toman. Desde el comité encargado de gestionar la app pensamos que es una buena manera de controlar su consumo. La CUJAE tiene un amplio índice de estudiantes con taquicardias por las extenuantes horas de estudio, pero bueno sin café no hay universitario."));
        lineasTutorial.add(new Dialogo("Vamos, que quiero mostrarles a tus compañeros como funciona la app bien."));
        lineasTutorial.add(new Dialogo(MENSAJE_OMAR_FINAL)); // **Última línea antes de la decisión**
    }

    // --- Métodos de Eventos y Flujo ---

    @FXML
    private void onContinuarClick() {
        // Este método actúa como "Continuar..." y luego como "Sí"
        if (btnContinuar.getText().equals("Continuar...")) {
            mostrarSiguienteLineaTutorial();
        } else {
            onSiClick();
        }
    }

    @FXML
    private void onNoClick() {
        // Muestra la explicación y vuelve a pedir la confirmación (bucle)
        labelDialogo.setText(RESPUESTA_NO);
    }

    private void onSiClick() {
        labelDialogo.setText("Se espera que los mejores estudiantes sean los que sepan mantener en equilibrio estos cuatro aspectos. Si suben o bajan mucho se espera que el estudiante no sea capaz de mantener una universidad estable. Espero que todos tengan un lindo primer día y nos vemos en el que viene.");

        // Ocultar botones y la preview
        btnContinuar.setVisible(false);
        btnNo.setVisible(false);
        imgAppPreview.setVisible(false);

        // Esperamos 4 segundos para que el usuario pueda leer la frase final
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(event -> transicionarAJuegoPrincipal());
        pause.play();
    }

    private void mostrarSiguienteLineaTutorial() {
        // Cambiado el chequeo de la cola a la comprobación del índice
        if (indiceDialogoActual < lineasTutorial.size()) {

            // Obtener el objeto Dialogo en la posición actual
            Dialogo dialogo = lineasTutorial.get(indiceDialogoActual);
            String linea = dialogo.getTexto();

            // Incrementamos el índice para la siguiente vez
            indiceDialogoActual++;

            if (linea.equals(MENSAJE_PREVIEW_TRIGGER)) {
                // Si encontramos el trigger, mostramos la preview de la app
                imgAppPreview.setVisible(true);
                // Inmediatamente cargamos la siguiente línea (llamada recursiva)
                mostrarSiguienteLineaTutorial();
                return;
            }

            labelDialogo.setText(linea);

            if (linea.equals(MENSAJE_OMAR_FINAL)) {
                // Si es la última línea, cambiamos la interfaz a la decisión Sí/No
                btnContinuar.setText("Sí");
                btnNo.setVisible(true);
            }

        } else {
            // Si el índice supera el tamaño de la lista, la historia ha terminado.
            transicionarAJuegoPrincipal();
        }
    }

    private void transicionarAJuegoPrincipal() {
        try {
            // La transición final al juego principal.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cujapse/views/JuegoPrincipal.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) labelDialogo.getScene().getWindow();

            // Se asume que el stage.initStyle(StageStyle.UNDECORATED) 
            // fue llamado en la clase MainApp.

            stage.setScene(new Scene(root));
            stage.setTitle("Cujapse - Orchat");
            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cargar la vista principal del juego: " + e.getMessage());
            e.printStackTrace();
        }
    }
}