package main;
import interfaz.controllers.MenuInicioController;
import interfaz.controllers.TutorialController;
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

    public GameControler() {
        this.game = Game.getInstance();
    }

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

    private void iniciarNuevaPartida() {
        System.out.println("Iniciando nueva partida...");
        RandomAccessFile raf = FileWriters.openFile(Game.getInstance().getPersonajesFichero());
        try {
            int cant = raf.readInt();
            for (int i = 0; i < cant; i++) {
                long ptr = raf.getFilePointer();
                int tam = raf.readInt();
                byte[] string = new byte[tam];
                raf.readFully(string);
                GameCharacter c = (GameCharacter) Convert.toObject(string);
                System.out.println(c.getDialoguesPath() + " " + c.getImagePath() + " " + c.getName());
                if (i == 0){
                    c.setDialoguesPath("/data/characters/dialogues/dialogues_1.dat");
                }
                if (i == 1){
                    c.setDialoguesPath("/data/characters/dialogues/dialogues_2.dat");
                }
                if (i == 2){
                    c.setDialoguesPath("/data/characters/dialogues/dialogues_3.dat");
                }
                byte[] data = Convert.toBytes(c);
                raf.seek(ptr);
                raf.writeInt(data.length);
                raf.write(data);
            }
            FileWriters.closeFile(raf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void cargarPartida() {
        System.out.println("Cargando partida...");
    }

    private void salirDelJuego() {
        System.out.println("Saliendo del juego...");
        System.exit(0);
    }

    private void showTutorial ()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaces/Tutorial.fxml"));
            Parent root = loader.load();
            TutorialController controller = loader.getController();

            controller.setDialogLines(game.startNewGame());
            Stage stage = new Stage();
            stage.setTitle("Tutorial");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//RandomAccessFile raf = FileReaders.openFile(Game.getInstance().getPersonajesFichero());
//try{
//           int cant = raf.readInt();
//           for (int i = 0; i < cant; i++){
//               long ptr = raf.getFilePointer();
//               int tam = raf.readInt();
//               byte[] string = new byte[tam];
//               raf.read(string);
//               GameCharacter c = (GameCharacter) Convert.toObject(string);
//               System.out.println(c.getDialoguesPath() + " "+ c.getImagePath() + " " + c.getName());
//           }
//           FileWriters.closeFile(raf);
//       } catch (IOException e) {
//           e.printStackTrace();
//       } catch (ClassNotFoundException e) {
//           e.printStackTrace();
//        }}

//RandomAccessFile raf = FileWriters.openFile(Game.getInstance().getPersonajesFichero());
//        try {
//            int cant = raf.readInt();
//            for (int i = 0; i < cant; i++) {
//                long ptr = raf.getFilePointer();
//                int tam = raf.readInt();
//                byte[] string = new byte[tam];
//                raf.readFully(string);
//
//                GameCharacter c = (GameCharacter) Convert.toObject(string);
//                System.out.println(c.getDialoguesPath() + " " + c.getImagePath() + " " + c.getName());
//
//                if (i == 0){
//                    c.setDialoguesPath("/data/characters/dialogues/dialogues_1.dat");
//                }
//                if (i == 1){
//                    c.setDialoguesPath("/data/characters/dialogues/dialogues_2.dat");
//                }
//                if (i == 2){
//                    c.setDialoguesPath("/data/characters/dialogues/dialogues_3.dat");
//                }
//                byte[] data = Convert.toBytes(c);
//
//                raf.seek(ptr);
//                raf.writeInt(data.length);
//                raf.write(data);
//            }
//            FileWriters.closeFile(raf);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//
//        }
