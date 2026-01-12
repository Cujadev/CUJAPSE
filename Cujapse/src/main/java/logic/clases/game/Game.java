package logic.clases.game;

import logic.auxiliars.files.FileReaders;
import logic.auxiliars.files.ProgressManager;
import logic.auxiliars.initializers.InitNewGame;
import logic.clases.character.GameCharacter;
import logic.clases.character.PrincipalCharacter;
import logic.clases.event.Event;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;


public class Game {
    private static Game game;
    private final File fileCharacters;
    private final Queue<Event> eventQueue;
    private PrincipalCharacter mainCharacter;
    private Scenary scenary;

    //====Constructor====
    public Game() {
        fileCharacters = FileReaders.returnFile("/data/characters/personajes.dat");// Revisar si se crea
        eventQueue = new ArrayDeque<>();
        scenary = new Scenary();
    }

    //====Singleton====
    public static Game getInstance() {
        if (game == null)
            game = new Game();
        return game;
    }

    //====Getter and Setters====
    public PrincipalCharacter getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(PrincipalCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public Queue<Event> getEventQueue() {
        return eventQueue;
    }

    public Scenary getScenary() {
        return scenary;
    }

    public void setScenary(Scenary scenary) {
        this.scenary = scenary;
    }

    public File getFileCharacters() {
        return fileCharacters;
    }
    //====Métodos necesarios====

    //Buscar un personaje
    public GameCharacter findCharacter(String id) {
        RandomAccessFile raf = FileReaders.openFile(fileCharacters);//Abre el fichero
        GameCharacter c = FileReaders.findCharacter(id, raf);//Busca el personaje en el fichero
        FileReaders.closeFile(raf);// Cierra el fichero

        return c;
    }

    //Encolar los eventos
    public void inQuequeEvents(ArrayList <Event> events, boolean newGame) {
        Random random = new Random();// Randomizador
        eventQueue.clear();


        if (events == null || events.isEmpty()) { throw new IllegalArgumentException("No hay eventos para encolar"); }

        if (!events.isEmpty()) {
            if (newGame) {
                eventQueue.offer(events.get(0));
                events.remove(0);
                while (!events.isEmpty()) {// Siempre que no esté vacío
                    int index = random.nextInt(events.size());// Se busca un número random entre 0 y el tamaño del array
                    eventQueue.offer(events.get(index));// Se agrega a la cola de elementos ese elemento en el índice random
                    events.remove(index); // Se remueve de la linked copia de eventos.
                }
            }
            else{
                ArrayList <String> played = mainCharacter.getIdEvents();
                while (!events.isEmpty()) {// Siempre que no esté vacío
                    int index = random.nextInt(events.size());
                    if (played.contains(events.get(index).getIdEvent())) {
                        System.out.println("Evento eliminado" + events.get(index).getIdEvent());
                        events.remove(index);
                    }
                    else{
                        System.out.println("Evento agregado" + events.get(index).getIdEvent());
                        eventQueue.offer(events.get(index));
                        events.remove(index);
                    }
                }
            }
            System.out.println("tamaño de cola: " +   eventQueue.size());
        }
    }

    public Event getNextEvent (){//Se van desencolando los eventos
        return eventQueue.poll();
    }

    public List<String> startNewGame(){
        mainCharacter  = new PrincipalCharacter("0","User", "/visualResources/characters/player.png","/data/main_character/principal_dialogues.dat","/data/main_character/consecuencias.dat");
        List <String> list = InitNewGame.giveTutorialDialogues();
        ArrayList <Event> events = InitNewGame.generateNewEvents();
        System.out.println(events.size());
        inQuequeEvents(events, true);
        scenary.setEvent(getNextEvent());
        mainCharacter.resetStats();
        return list;
    }

    public boolean  startGame() {
        boolean result = true;
        try {
            mainCharacter = new PrincipalCharacter (ProgressManager.ChargeCharacter());
            ArrayList<Event> events = InitNewGame.generateEvents();
            inQuequeEvents(events, false);
            if (eventQueue.size() == 0 || eventQueue.size() == 6) {
                result = false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void modifyStats (int selection, String id){
        mainCharacter.modifyStats(selection, id);
    }
}
