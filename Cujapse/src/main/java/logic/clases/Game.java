package logic.clases;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;


public class Game {
    private static Game game;
    private File personajesFichero;

    private ArrayList<GameCharacter> personajes;
    private ArrayList <Event> eventos;
    private ArrayList <Scenary> escenarios;
    private Queue<Event> colaEventos;
    private PrincipalCharacter mainCharacter;

    public Game() {
        personajesFichero = new File("fichero.dat");
        personajes = new ArrayList<>();
        eventos = new ArrayList<>();
        escenarios = new ArrayList<>();
        colaEventos = new ArrayDeque<>();
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

    public ArrayList<Scenary> getEscenarios() {
        return escenarios;
    }

    public void setEscenarios(ArrayList<Scenary> escenarios) {
        this.escenarios = escenarios;
    }

    public ArrayList<Event> getEventos() {
        return eventos;
    }

    public void setEventos(ArrayList<Event> eventos) {
        this.eventos = eventos;
    }

    public Queue<Event> getColaEventos() {
        return colaEventos;
    }

    public GameCharacter findCharacter(String id){
        boolean found = false;
        GameCharacter c = null;

        for(int i = 0; i < personajes.size() && !found; i++){
            if(personajes.get(i).getId().equalsIgnoreCase(id)){
                found = true;
                c = personajes.get(i);
            }
        }

        return c;
    }
}
