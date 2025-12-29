package main;
import interfaz.controllers.MenuInicioController;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import logic.auxiliars.files.Convert;
import logic.auxiliars.files.FileReaders;
import logic.auxiliars.files.FileWriters;
import logic.clases.character.GameCharacter;
import logic.clases.game.Game;
import javafx.application.Application;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GameControler extends Application implements MenuInicioController.MenuInicioListener {
    private Game game;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/MenuInicio.fxml"));
        Parent root = loader.load();
        MenuInicioController controller = loader.getController();
        controller.setListener(this);
        Scene scene = new Scene(root, 972, 866);
        stage.setTitle("Menú Inicio");
        stage.setScene(scene);
        stage.show();
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

    private void iniciarNuevaPartida() { System.out.println("Iniciando nueva partida...");}

    private void cargarPartida() {
        System.out.println("Cargando partida...");
    }

    private void salirDelJuego() {
        System.out.println("Saliendo del juego...");
        System.exit(0);
    }
}

// RandomAccessFile raf = FileReaders.openFile(Game.getInstance().getPersonajesFichero());
// try{
//            int cant = raf.readInt();
//            for (int i = 0; i < cant; i++){
//                long ptr = raf.getFilePointer();
//                int tam = raf.readInt();
//                byte[] string = new byte[tam];
//                raf.read(string);
//                GameCharacter c = (GameCharacter) Convert.toObject(string);
//                System.out.println(c.getDialoguesPath() + " "+ c.getImagePath() + " " + c.getName());
//            }
//            FileWriters.closeFile(raf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
