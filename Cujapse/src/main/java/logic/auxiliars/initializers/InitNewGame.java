package logic.auxiliars.initializers;

import logic.auxiliars.chargers.ChargerSituation_Dialogue;
import logic.auxiliars.files.FileReaders;
import logic.auxiliars.tree.DecisionNode;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;
import logic.clases.event.Event;
import logic.clases.event.Situation;
import logic.clases.game.Game;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InitNewGame {
    private static  HashMap <String, String> tutorial = new HashMap<>();
    private static  HashMap <String, String> miguel = new HashMap<>();
    private static  HashMap <String, String> omar = new HashMap<>();


    static {
        tutorial.put("20","1");
        tutorial.put("21","1");
        tutorial.put("22","1");
    }
    public static ArrayList<String> giveTutorialDialogues () {
        GameCharacter character = findGameCharacter("1");
        ArrayList <Dialogue> dialogues = getDialogues(character);
        ArrayList <String> result = new ArrayList<>();
        for (int i = 0; i < dialogues.size() - 3; i++) {
            result.add(dialogues.get(i).getContenido());
        }
        return result.isEmpty() ? null : result;
    }
    public static Event createFirstEvent(){
        ArrayList <Situation> situations = obtainSituations("1");
        Event first = new Event("1",situations.get(0));
        DecisionNode <Situation> node1 = new DecisionNode<>(situations.get(1));
        DecisionNode <Situation> node2 = new DecisionNode<>(situations.get(2));
        first.addSituation(node1, first.getSituations().getRoot(), 1);
        first.addSituation(node2, first.getSituations().getRoot(), 2);
        return first;
    }
    public LinkedList <Event> initializeEvents (){
        return null;
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
    private static ArrayList <Situation> obtainSituations (String id){
        GameCharacter character = findGameCharacter(id);
        ArrayList <Situation> result = new ArrayList<>();
        ArrayList <Dialogue> dialogues;

        if (character.getId().equals("1")){
            ArrayList <Dialogue> tutorialDialogues = getDialogues(character);
            int lastIndex = tutorialDialogues.size();
            List<Dialogue> temporal = tutorialDialogues.subList(lastIndex - 3, lastIndex);
            dialogues = new ArrayList<>(temporal);
            for (Dialogue dialogue : dialogues) {
                result.add(generateSituation(dialogue.getId(), character));
            }
        }
        else {
            dialogues = getDialogues(character);
            for (Dialogue dialogue : dialogues) {
                result.add(generateSituation(dialogue.getId(), character));
            }
        }
        if (result.isEmpty()){
            throw new RuntimeException("No existen situaciones, por algún motivo");
        }
        return result;
    }
    private static Situation generateSituation (String idDial, GameCharacter character){
        HashMap <String, String> map = new HashMap<>();
        switch (character.getId()){
            case "1":{
                map = tutorial;
                tutorial.put("1","1");
                tutorial.put("2","0");
                tutorial.put("3","0");
                break;
            }
            case "2":{
                map = miguel;
                break;
            }
            case "3":{
                map = omar;
                break;
            }
        }
        ChargerSituation_Dialogue association = new ChargerSituation_Dialogue(character.getId(),idDial,map.get(idDial));
        return new Situation(association);
    }

}