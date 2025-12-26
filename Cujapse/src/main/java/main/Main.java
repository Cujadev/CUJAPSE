package main;

import interfaz.auxiliars.Mensaje;
import interfaz.controllers.MenuInicioController;
import interfaz.controllers.PrincipalController;
import interfaz.auxiliars.Evento;
import interfaz.auxiliars.Mensaje;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader menuInicio = new FXMLLoader(getClass().getResource("/interfaces/MenuInicio.fxml"));
        Parent root = menuInicio.load();
        //Controlador
        MenuInicioController manuController = menuInicio.getController();

        FXMLLoader principal = new FXMLLoader(getClass().getResource("/interfaces/Principal.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}