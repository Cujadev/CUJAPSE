package logic.clases;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Scenary {
    private Event event;
    private ArrayList <Image> sceneryImages;

    // Pedir al evento dado una selección una situación
    // Poder dar al MVC la imagen a cargar
    // Poder pedir al juego un nuevo evento

    public Scenary(Event event) {
        sceneryImages = new ArrayList<>();
        this.event = event;
    }

    public Event getEvento() {
        return event;
    }

    public void setEvento(Event evento) {

    }

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
}
