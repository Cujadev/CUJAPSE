package logic.clases.game;

import javafx.scene.image.Image;
import logic.auxiliars.chargers.ChargerMenssage;
import logic.auxiliars.files.FileReaders;
import logic.clases.character.Answer;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;
import logic.clases.character.PrincipalCharacter;
import logic.clases.event.Event;
import logic.clases.event.Situation;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Scenary implements ChargerMenssage {
    private Event event;
    private ArrayList<String> deathCasesPath; //Es un arraylist con las direcciones de los casos de muerte
    private File deadMensages;

    // Pedir al evento dado una selección una situación
    // Poder dar al MVC la imagen a cargar
    // Poder pedir al juego un nuevo evento

    /// ==== Constructor ====
    public Scenary (){

    }

    /// ==== Getters and Setters ====
    public Event getEvento() {
        return event;
    }

    public void setEvento() {
        Game g = Game.getInstance();
        this.event = g.getNextEvent();
    }

    public ArrayList<String> getDeathCasesPath() {
        return deathCasesPath;
    }

    public void setDeathCasesPath(ArrayList<String> deathCasesPath) {
        this.deathCasesPath = deathCasesPath;
    }

    ///==== Métodos necesarios ====

    //====Entregar los diálogos====
    public ArrayList<String> giveSituation(int branch){
        Situation s = event.getNextSituaion(branch); //Se obtiene la situación
        ArrayList <String> dialogues = new ArrayList<>();
        Dialogue seconDialogue = s.getCharacterDialogue(s.getAssociation().getIdCharacter());//Se carga el dialogo del personaje secundario
        dialogues.add(seconDialogue.getContenido());
        if (s.getAssociation().getIdAnswer() != null){//Si existe respuesta posible del jugador también se cargan
            Answer a = s.getPrincipalAnswers(s.getAssociation().getIdAnswer());
            Dialogue [] answers = a.getAnswers();
            for (Dialogue answer : answers) {
                dialogues.add(answer.getContenido());
            }
        }
        return dialogues;
    }

    //Entregar la imagen de personaje secundario
    public Image giveImageCharacter(){
        Game game = Game.getInstance();
        Situation s = event.getNextSituaion(0);
        GameCharacter c = game.findCharacter(s.getAssociation().getIdCharacter());
        return new Image(c.getImagePath());
    }
    //Entregar la imagen del personaje principal
    public Image giveMainCharacterImage (){
        Game game = Game.getInstance();
        PrincipalCharacter p = game.getMainCharacter();
        return new Image(p.getImagePath());
    }
    //Entregar la imagen del escenario
    public Image giveSceneryImage (){
        return new Image(event.getImagePath());
    }

    public boolean heroIsDeath (){
        PrincipalCharacter p = Game.getInstance().getMainCharacter();
        return p.isDead();
    }

    public ArrayList <Object> giveDeath(){
        ArrayList <Object> result = new ArrayList<>();
        PrincipalCharacter p = Game.getInstance().getMainCharacter();

        String id = p.causeOfDeath();
        int index = Integer.parseInt(id);

        String menssage = ChargeDialogue(id).getContenido();
        result.add(menssage);
        Image image = new Image (deathCasesPath.get(index));
        result.add(image);

        return result;
    }

    @Override
    public Dialogue ChargeDialogue(String id) {
        Dialogue result;
        RandomAccessFile raf = FileReaders.openFile(deadMensages);
        result = FileReaders.searchDialogue(id, raf);
        FileReaders.closeFile(raf);
        return result;
    }
}
