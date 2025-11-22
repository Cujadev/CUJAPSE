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
    private File personajesFichero;
    private LinkedList<Event> events;
    private Queue<Event> eventQueue;
    private PrincipalCharacter mainCharacter;
    private Scenary scenary;

    public Game() {
        personajesFichero = new File("fichero.dat");
        events = new LinkedList<>();
        eventQueue = new ArrayDeque<>();
        scenary = new Scenary();
    }

    public static Game getInstance() {
        if (game == null)
            game = new Game();
        return game;
    }
    public PrincipalCharacter getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(PrincipalCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }


    public LinkedList<Event> getEventos() {
        return events;
    }

    public void setEventos(LinkedList<Event> events) {
        this.events = events;
    }

    public Queue<Event> getEventQueue() {
        return eventQueue;
    }

    public GameCharacter findCharacter(String id){
        RandomAccessFile raf = FileReaders.openFile(personajesFichero);
        GameCharacter c = FileReaders.findCharacter(id, raf);
        FileReaders.closeFile(raf);

        return c;
    }

    public void inQuequeEvents (){
        LinkedList <Event> events = new LinkedList<>(this.events);
        Random random = new Random();

        while (!events.isEmpty()){
            int index = random.nextInt(events.size());
            eventQueue.offer(events.get(index));
            events.remove(index);
        }
    }
    public Event getNextEvent (){
        return eventQueue.poll();
    }
}
