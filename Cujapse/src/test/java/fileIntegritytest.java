import logic.auxiliars.files.Convert;
import logic.auxiliars.files.FileReaders;
import logic.auxiliars.files.FileWriters;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;
import logic.clases.game.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class fileIntegritytest {
    Game game;
    static File characters;
    File dialogues;

    @BeforeEach
    void setUp(){
        game = Game.getInstance();
        characters = game.getPersonajesFichero();
        dialogues = new File(game.getMainCharacter().getDialoguesPath());
    }

    @Test
    void verifyExistenceOfCharacters(){
        Assertions.assertTrue(characters.exists());
    }

    @Test
    void verifyExistenceOfMainCharacterDialogues(){
        Assertions.assertTrue(dialogues.exists());
    }

    @Test
    void verifyExistenceOfAllDialoguesDirectory(){
         RandomAccessFile raf = FileReaders.openFile(game.getPersonajesFichero());
         boolean existence = true;
         try{
             int cant = raf.readInt();
             for (int i = 0; i < cant && existence; i++){
                 int tam = raf.readInt();
                 byte[] string = new byte [tam];
                 raf.read(string);
                 GameCharacter c = (GameCharacter) Convert.toObject(string);
                 File f = FileReaders.returnFile(c.getDialoguesPath());
                 System.out.println("El id es:"+c.getId()+"\n"+c.getDialoguesPath());
                 if (!(f.exists())){
                     existence = false;
                 }
             }
             FileReaders.closeFile(raf);
         }catch (ClassNotFoundException | IOException e) {
             throw new RuntimeException(e);
         }

         Assertions.assertTrue(existence);
    }

    public static void main (){
        RandomAccessFile raf = FileReaders.openFile(Game.getInstance().getPersonajesFichero());
        try{
            int cant = raf.readInt();
            for (int i = 0; i < cant; i++){
                long ptr = raf.getFilePointer();
                int tam = raf.readInt();
                byte[] string = new byte[tam];
                raf.read(string);
                GameCharacter c = (GameCharacter) Convert.toObject(string);
                System.out.println(c.getDialoguesPath() + " "+ c.getImagePath() + " " + c.getName());
            }
            FileWriters.closeFile(raf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
