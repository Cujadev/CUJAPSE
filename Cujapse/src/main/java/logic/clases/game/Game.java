package logic.clases.game;

import logic.auxiliars.files.FileReaders;
import logic.clases.character.GameCharacter;
import logic.clases.character.PrincipalCharacter;
import logic.clases.event.Event;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Game {
    private static Game game;
    private final File personajesFichero;
    private LinkedList<Event> events;
    private final Queue<Event> eventQueue;
    private PrincipalCharacter mainCharacter;
    private Scenary scenary;

    //====Constructor====
    public Game() {
        personajesFichero = FileReaders.returnFile("/data/Characters/personajes.dat");// Revisar si se crea
        events = new LinkedList<>();
        eventQueue = new ArrayDeque<>();
        scenary = new Scenary();
        mainCharacter  = new PrincipalCharacter("0","User",null,"/data/main_character/principal_dialogues.dat","/data/main_character/consecuencias.dat");
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


    public LinkedList<Event> getEventos() {return events;}

    public void setEventos(LinkedList<Event> events) {
        this.events = events;
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
    public void inQuequeEvents() {
        LinkedList<Event> events = new LinkedList<>(this.events); //Crea una copia de la lincked list
        Random random = new Random();// Randomizador

        while (!events.isEmpty()) {// Siempre que no esté vacio
            int index = random.nextInt(events.size() - 1);// Se busca un número random entre 0 y el tamaño del array
            eventQueue.offer(events.get(index));// Se agrega a la cola de elementos ese elemento en el índice random
            events.remove(index); // Se remueve de la lincked copia de eventos.
        }
    }

    public Event getNextEvent (){//Se van desencolando los eventos
        return eventQueue.poll();
    }
}
