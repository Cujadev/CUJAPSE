package logic.auxiliars.initializers;

import logic.auxiliars.files.FileReaders;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;
import logic.clases.event.Event;
import logic.clases.game.Game;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;

public class InitNewGame {


    public static ArrayList<String> giveTutorialDialogues () {
        GameCharacter character = findGameCharacter("1");
        ArrayList <Dialogue> dialogues = getDialogues(character);
        ArrayList <String> result = null;
        for (int i = 0; i < dialogues.size() - 3; i++) {
            result.add(dialogues.get(i).getContenido());
        }
        return result;
    }

    private static GameCharacter findGameCharacter(String id){
        Game game = Game.getInstance();
        RandomAccessFile raf = FileReaders.openFile(game.getPersonajesFichero());
        GameCharacter c = FileReaders.findCharacter(id, raf);
        FileReaders.closeFile(raf);
        return c;
    }

    private static ArrayList <Dialogue> getDialogues(GameCharacter character){
        File file = FileReaders.returnFile(character.getDialoguesPath());
        RandomAccessFile randomAccessFile = FileReaders.openFile(file);
        ArrayList<Dialogue> characterDialogue= FileReaders.findDialogues(randomAccessFile);
        FileReaders.closeFile(randomAccessFile);
        return characterDialogue;
    }
    public LinkedList <Event> initializeEvents (){
        return null;
    }

}