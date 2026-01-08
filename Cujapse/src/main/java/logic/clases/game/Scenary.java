package logic.clases.game;


import logic.auxiliars.chargers.ChargerMenssage;
import logic.auxiliars.dataOfInterfaces.Menssage;
import logic.auxiliars.dataOfInterfaces.PrincipalData;
import logic.auxiliars.files.FileReaders;
import logic.auxiliars.tree.DecisionNode;
import logic.clases.character.Answer;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;
import logic.clases.character.PrincipalCharacter;
import logic.clases.event.Event;
import logic.clases.event.Situation;

import javafx.scene.image.Image;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Scenary implements ChargerMenssage {
    private Event event;
    private ArrayList<String> deathCasesPath; //Es un arraylist con las direcciones de los casos de muerte
    private File deadMenssages;

    // Pedir al evento dado una selección una situación
    // Poder dar al MVC la imagen a cargar
    // Poder pedir al juego un nuevo evento

    /// ==== Constructor ====
    public Scenary() {
    }

    /// ==== Getters and Setters ====
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ArrayList<String> getDeathCasesPath() {
        return deathCasesPath;
    }

    public void setDeathCasesPath(ArrayList<String> deathCasesPath) {
        this.deathCasesPath = deathCasesPath;
    }

    /// ==== Métodos necesarios ====

    //====Entregar los diálogos====
    private List<Menssage> generateMessages(int branch) {
        Situation s = event.getNextSituaion(branch); //Se obtiene la situación
        List<Menssage> dialogues = new ArrayList<>();
        Dialogue seconDialogue = s.getCharacterDialogue(s.getAssociation().getIdCharacter());//Se carga el dialogo del personaje secundario
        dialogues.add(new Menssage(seconDialogue.getContenido(), giveCharacterName(), findPathAvatarCharacter()));
        if (s.getAssociation().getIdAnswer() != null) {//Si existe respuesta posible del jugador también se cargan
            Answer a = s.getPrincipalAnswers(s.getAssociation().getIdAnswer());
            Dialogue[] answers = a.getAnswers();
            for (Dialogue answer : answers) {
                dialogues.add(new Menssage(answer.getContenido(), Game.getInstance().getMainCharacter().getName(), null));
            }
        }
        return dialogues;
    }

    public PrincipalData giveData(int branch) {
        List<Menssage> dialogues = generateMessages(branch);
        ArrayList<Integer> stats = findStats();
        String scenary = findSceneryImagePath();
        return new PrincipalData(dialogues, stats, scenary);
    }


    //Entregar la imagen de personaje secundario
    private String findPathAvatarCharacter() {
        Game game = Game.getInstance();
        Situation s = event.getNextSituaion(0);//Se obtiene la situación actual
        GameCharacter c = game.findCharacter(s.getAssociation().getIdCharacter());//Se busca el personaje
        return c.getImagePath();
    }

    //Entregar la imagen del escenario
    private String findSceneryImagePath() {
        return event.getImagePath();
    }

    //Entregar estado del personaje
    public boolean isHeroDeath() {
        PrincipalCharacter p = Game.getInstance().getMainCharacter();
        return p.isDead();//Verifica si el personaje murió
    }

    //Se entrega todo lo necesario para poder trabajar la muerte del personaje
    public ArrayList<Object> giveDeath() {
        ArrayList<Object> result = new ArrayList<>();
        PrincipalCharacter p = Game.getInstance().getMainCharacter();// se obtiene el personaje principal

        String id = p.causeOfDeath();// Se devuelve el id de la causa de muerte
        int index = Integer.parseInt(id);// Se convierte ese id en un índice

        String menssage = ChargeDialogue(id).getContenido();// Se utiliza el id para buscar un dialogó de la causa de muerte
        result.add(menssage);// se agrega ese mensaje
        Image image = new Image(deathCasesPath.get(index));// Se crea la imagen de la muerte
        result.add(image);//Se agrega al array de objetos

        return result;
    }

    public ArrayList<Integer> findStats() {
        ArrayList<Integer> stats = Game.getInstance().getMainCharacter().getStats();
        return stats;
    }

    @Override
    public Dialogue ChargeDialogue(String id) {
        Dialogue result;
        RandomAccessFile raf = FileReaders.openFile(deadMenssages);
        result = FileReaders.searchDialogue(id, raf);
        FileReaders.closeFile(raf);
        return result;
    }

    private String giveCharacterName() {
        return Game.getInstance().findCharacter(event.getNextSituaion(0).getAssociation().getIdCharacter()).getName();
    }

    public void callModificationStats(int selection) {
        DecisionNode<Situation> node = event.getActualSituation();
        Situation s = node.getInfo();

        Game.getInstance().modifyStats(selection, s.getAssociation().getIdAnswer());
    }

    /*private Image convertStringToImage(String string){
        Image i = null;
        try {
            URL imageUrl = getClass().getResource(string);
            if (imageUrl == null){
                throw new IllegalArgumentException("El archivo no existe");
            }
            i = new Image(imageUrl.toExternalForm());
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return i;
    }*/

}
