package logic.clases.game;

import logic.auxiliars.files.FileReaders;
import logic.auxiliars.initializers.InitNewGame;
import logic.clases.character.GameCharacter;
import logic.clases.character.PrincipalCharacter;
import logic.clases.event.Event;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.*;


public class Game {
    private static Game game;
    private final File personajesFichero;
    private final Queue<Event> eventQueue;
    private PrincipalCharacter mainCharacter;
    private Scenary scenary;

    //====Constructor====
    public Game() {
        personajesFichero = FileReaders.returnFile("/data/characters/personajes.dat");// Revisar si se crea
        eventQueue = new ArrayDeque<>();
        scenary = new Scenary();
        mainCharacter  = new PrincipalCharacter("0","User", "/visualResources/characters/player.png","/data/main_character/principal_dialogues.dat","/data/main_character/consecuencias.dat");
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

    public File getPersonajesFichero() {
        return personajesFichero;
    }
    //====Métodos necesarios====

    //Buscar un personaje
    public GameCharacter findCharacter(String id) {
        RandomAccessFile raf = FileReaders.openFile(personajesFichero);//Abre el fichero
        GameCharacter c = FileReaders.findCharacter(id, raf);//Busca el personaje en el fichero
        FileReaders.closeFile(raf);// cierra el fichero

        return c;
    }

    //Encolar los eventos
    public void inQuequeEvents(ArrayList <Event> events, boolean newGame) {
        Random random = new Random();// Randomizador
        eventQueue.offer(events.get(0));
        events.remove(0);

        if (!events.isEmpty()) {
            if (newGame) {
                while (!events.isEmpty()) {// Siempre que no esté vacio
                    int index = random.nextInt(events.size() - 1);// Se busca un número random entre 0 y el tamaño del array
                    eventQueue.offer(events.get(index));// Se agrega a la cola de elementos ese elemento en el índice random
                    events.remove(index); // Se remueve de la lincked copia de eventos.
                }
            }
            else{
                ArrayList <String> played = mainCharacter.getIdEvents();
                while (!events.isEmpty()) {// Siempre que no esté vacio
                    int index = random.nextInt(events.size() - 1);
                    if (played.contains(events.get(index).getIdEvent())) {
                        events.remove(index);
                    }
                    else{
                        eventQueue.offer(events.get(index));
                        events.remove(index);
                    }
                }
            }
        }
    }

    public Event getNextEvent (){//Se van desencolando los eventos
        return eventQueue.poll();
    }

    public List<String> startNewGame(){
        List <String> list = InitNewGame.giveTutorialDialogues();
        ArrayList <Event> events = new ArrayList();
        events.add(InitNewGame.createFirstEvent());
        inQuequeEvents(events, true);
        scenary.setEvent(getNextEvent());
        return list;
    }
}
