package interfaz.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador del menú de inicio.:
 *  1 = Nueva partida
 *  2 = Cargar partida
 *  3 = Salir del juego
 */
public class MenuInicioController implements Initializable {

    @FXML private Button btnNuevaPartida;
    @FXML private Button btnCargarPartida;
    @FXML private Button btnSalir;

    /**
     * Se ejecuta cuando el usuario toca un botón.
     */
    private MenuInicioListener listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Permite que la lógica registre un listener para recibir los códigos.
     */
    public void setListener(MenuInicioListener listener) {
        this.listener = listener;
    }

    // ------------------ EVENTOS DE BOTONES ------------------

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

    // ------------------ INTERFAZ PARA LA LÓGICA ------------------
    /*
     * Listener muy simple:
     * La interfaz solo envía un código según el botón:
     *   1 = Nueva partida
     *   2 = Cargar partida
     *   3 = Salir
     *
     * La lógica del juego implementa esta interfaz y decide qué hacer
     * cuando recibe ese código. Así el menú no contiene lógica, solo notifica.
     */
    public interface MenuInicioListener {
        void onMenuOptionSelected(int codigo);
    }
}
