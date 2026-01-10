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
    private static final HashMap<String, String> tutorial = new HashMap<>();
    private static final HashMap<String, String> miguel = new HashMap<>();
    private static final HashMap<String, String> omar = new HashMap<>();



    public static ArrayList<String> giveTutorialDialogues() {
        GameCharacter character = findGameCharacter("1");
        ArrayList<Dialogue> dialogues = getDialogues(character);
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < dialogues.size() - 3; i++) {
            result.add(dialogues.get(i).getContenido());
        }
        return result.isEmpty() ? null : result;
    }

    private static Event createFirstEvent() {
        ArrayList<Situation> situations = obtainTutorialSituations("1");
        Event first = new Event("1", situations.get(0));
        DecisionNode<Situation> node1 = new DecisionNode<>(situations.get(1));
        DecisionNode<Situation> node2 = new DecisionNode<>(situations.get(2));
        first.addSituation(node1, first.getSituations().getRoot(), 1);
        first.addSituation(node2, first.getSituations().getRoot(), 2);
        return first;
    }

    public LinkedList<Event> initializeEvents() {
        return null;
    }

    private static GameCharacter findGameCharacter(String id) {
        Game game = Game.getInstance();
        RandomAccessFile raf = FileReaders.openFile(game.getPersonajesFichero());
        GameCharacter c = FileReaders.findCharacter(id, raf);
        FileReaders.closeFile(raf);
        return c;
    }

    private static ArrayList<Dialogue> getDialogues(GameCharacter character) {
        File file = FileReaders.returnFile(character.getDialoguesPath());
        RandomAccessFile randomAccessFile = FileReaders.openFile(file);
        ArrayList<Dialogue> characterDialogue = FileReaders.findDialogues(randomAccessFile);
        FileReaders.closeFile(randomAccessFile);
        return characterDialogue;
    }

    private static ArrayList<Situation> obtainTutorialSituations(String id) {
        GameCharacter character = findGameCharacter(id);
        ArrayList<Situation> result = new ArrayList<>();
        ArrayList<Dialogue> dialogues;

        if (character.getId().equals("1")) {
            ArrayList<Dialogue> tutorialDialogues = getDialogues(character);
            int lastIndex = tutorialDialogues.size();
            List<Dialogue> temporal = tutorialDialogues.subList(lastIndex - 3, lastIndex);
            dialogues = new ArrayList<>(temporal);
            for (Dialogue dialogue : dialogues) {
                result.add(generateSituation(dialogue.getId(), character));
            }
        }
        if (result.isEmpty()) {
            throw new RuntimeException("No existen situaciones, por algún motivo");
        }
        return result;
    }

    private static Situation generateSituation(String idDial, GameCharacter character) {
        HashMap<String, String> map = new HashMap<>();
        switch (character.getId()) {
            case "1": {
                map = tutorial;
                tutorial.put("20", "1");
                tutorial.put("21", "1");
                tutorial.put("22", "1");
                break;
            }
            case "2": {
                map = omar;
                omar.put("1", "2");
                omar.put("9", "6");
                omar.put("2", "3");
                omar.put("6", "5");
                omar.put("3", "4");
                omar.put("11", null);
                omar.put("10", null);
                omar.put("8", null);
                omar.put("7", null);
                omar.put("5", null);
                omar.put("4", null);

                omar.put("12", "7");
                omar.put("13", "8");
                omar.put("14", "9");
                omar.put("18", "10");
                omar.put("15", null);
                omar.put("16", null);
                omar.put("17", null);
                omar.put("19", null);
                omar.put("20", null);
                break;
            }
            case "3": {
                map = miguel;
                miguel.put("1", "11");
                miguel.put("2", "12");
                miguel.put("5", "13");
                miguel.put("3", null);
                miguel.put("4", null);
                miguel.put("6", null);
                miguel.put("7", null);

                miguel.put("8", "14");
                miguel.put("9", "15");
                miguel.put("12", "16");
                miguel.put("10", null);
                miguel.put("11", null);
                miguel.put("13", null);
                miguel.put("14", null);
                break;
            }
        }
        ChargerSituation_Dialogue association = new ChargerSituation_Dialogue(character.getId(), idDial, map.get(idDial));
        return new Situation(association);
    }


    public static ArrayList<Event> generateEvents() {
        ArrayList<Event> result = new ArrayList<>();
        result.add(createFirstEvent());
        ArrayList<Event> omarEvents = generateEventsOmar();
        if (omarEvents != null && !omarEvents.isEmpty()) {
            result.addAll(omarEvents);
        }
        ArrayList <Event> miguelEvents = generateEventsMiguel();
        if (miguelEvents != null && !miguelEvents.isEmpty()) {
            result.addAll(miguelEvents);
        }
        if (result.isEmpty()) {
            throw new IllegalStateException("No se generaron eventos iniciales");
        }
        return result;
    }

    private static ArrayList<Event> generateEventsOmar() {
        ArrayList<Event> result = new ArrayList<>();
        GameCharacter character = findGameCharacter("3");

        Situation situation1 = generateSituation("1", character);
        DecisionNode<Situation> node1 = new DecisionNode<>(situation1);
        Event e1 = new Event("2", situation1);

        Situation situation2 = generateSituation("9", character);
        DecisionNode<Situation> node2 = new DecisionNode<>(situation2);
        e1.addSituation(node2, e1.getSituations().getRoot(), 1);

        Situation situation3 = generateSituation("2", character);
        DecisionNode<Situation> node3 = new DecisionNode<>(situation3);
        e1.addSituation(node3, e1.getSituations().getRoot(), 2);

        Situation situation4 = generateSituation("11", character);
        DecisionNode<Situation> node4 = new DecisionNode<>(situation4);
        e1.addSituation(node4, node2, 1);

        Situation situation5 = generateSituation("10", character);
        DecisionNode<Situation> node5 = new DecisionNode<>(situation5);
        e1.addSituation(node5, node2, 2);

        Situation situation6 = generateSituation("6", character);
        DecisionNode<Situation> node6 = new DecisionNode<>(situation6);
        e1.addSituation(node6, node3, 1);

        Situation situation7 = generateSituation("3", character);
        DecisionNode<Situation> node7 = new DecisionNode<>(situation7);
        e1.addSituation(node7, node3, 2);

        Situation situation8 = generateSituation("8", character);
        DecisionNode<Situation> node8 = new DecisionNode<>(situation8);
        e1.addSituation(node8, node6, 1);

        Situation situation9 = generateSituation("7", character);
        DecisionNode<Situation> node9 = new DecisionNode<>(situation9);
        e1.addSituation(node9, node6, 2);

        Situation situation10 = generateSituation("5", character);
        DecisionNode<Situation> node10 = new DecisionNode<>(situation10);
        e1.addSituation(node10, node7, 1);

        Situation situation11 = generateSituation("4", character);
        DecisionNode<Situation> node11 = new DecisionNode<>(situation11);
        e1.addSituation(node11, node7, 2);
        result.add(e1);

        Situation situation12 = generateSituation("12", character);
        DecisionNode<Situation> node12 = new DecisionNode<>(situation12);
        Event e2 = new Event("3", situation12);


        Situation situation14 = generateSituation("18", character);
        DecisionNode<Situation> node14 = new DecisionNode<>(situation14);
        e2.addSituation(node14, e2.getSituations().getRoot(), 1);

        Situation situation13 = generateSituation("13", character);
        DecisionNode<Situation> node13 = new DecisionNode<>(situation13);
        e2.addSituation(node13, e2.getSituations().getRoot(), 2);

        Situation situation15 = generateSituation("20", character);
        DecisionNode<Situation> node15 = new DecisionNode<>(situation15);
        e2.addSituation(node15, node14, 1);

        Situation situation16 = generateSituation("19", character);
        DecisionNode<Situation> node16 = new DecisionNode<>(situation16);
        e2.addSituation(node16, node14, 2);

        Situation situation17 = generateSituation("17", character);
        DecisionNode<Situation> node17 = new DecisionNode<>(situation17);
        e2.addSituation(node17, node13, 1);

        Situation situation18 = generateSituation("14", character);
        DecisionNode<Situation> node18 = new DecisionNode<>(situation18);
        e2.addSituation(node18, node13, 2);

        Situation situation19 = generateSituation("16", character);
        DecisionNode<Situation> node19 = new DecisionNode<>(situation19);
        e2.addSituation(node19, node18, 1);

        Situation situation20 = generateSituation("15", character);
        DecisionNode<Situation> node20 = new DecisionNode<>(situation20);
        e2.addSituation(node20, node18, 2);

        result.add(e2);
        return result;
    }
    private static ArrayList <Event> generateEventsMiguel(){
        ArrayList <Event> events = new ArrayList();
        GameCharacter character = findGameCharacter("2");

        Situation situation1 = generateSituation("1",character);
        Event e1 = new Event("3", situation1);

        Situation situation2 = generateSituation("2",character);
        DecisionNode<Situation> node2 = new DecisionNode<>(situation2);
        e1.addSituation(node2, e1.getSituations().getRoot(), 1);

        Situation situation3 = generateSituation("5",character);
        DecisionNode<Situation> node3 = new DecisionNode<>(situation3);
        e1.addSituation(node3, e1.getSituations().getRoot(), 2);

        Situation situation4 = generateSituation("3",character);
        DecisionNode<Situation> node4 = new DecisionNode<>(situation4);
        e1.addSituation(node4, node2, 1);

        Situation situation5 = generateSituation("4",character);
        DecisionNode<Situation> node5 = new DecisionNode<>(situation5);
        e1.addSituation(node5, node2, 2);

        Situation situation6 = generateSituation("6",character);
        DecisionNode<Situation> node6 = new DecisionNode<>(situation6);
        e1.addSituation(node6, node3, 1);

        Situation situation7 = generateSituation("7",character);
        DecisionNode<Situation> node7 = new DecisionNode<>(situation7);
        e1.addSituation(node7, node3, 2);

        events.add(e1);

        Situation situation8 = generateSituation("8",character);
        Event e2 = new Event("4", situation8);

        Situation situation9 = generateSituation("9",character);
        DecisionNode<Situation> node9 = new DecisionNode<>(situation9);
        e2.addSituation(node9, e2.getSituations().getRoot(), 1);

        Situation situation10 = generateSituation("12",character);
        DecisionNode<Situation> node10 = new DecisionNode<>(situation10);
        e2.addSituation(node10, e2.getSituations().getRoot(), 2);

        Situation situation11 = generateSituation("10",character);
        DecisionNode<Situation> node11 = new DecisionNode<>(situation11);
        e2.addSituation(node11, node9, 1);

        Situation situation12 = generateSituation("11",character);
        DecisionNode<Situation> node12 = new DecisionNode<>(situation12);
        e2.addSituation(node12, node9, 2);

        Situation situation13 = generateSituation("13",character);
        DecisionNode<Situation> node13 = new DecisionNode<>(situation13);
        e2.addSituation(node13, node10, 1);

        Situation situation14 = generateSituation("14",character);
        DecisionNode<Situation> node14 = new DecisionNode<>(situation14);
        e2.addSituation(node14, node10, 2);

        events.add(e2);

        return events;
    }

}