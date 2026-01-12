package logic.clases.game;


import logic.auxiliars.dataOfInterfaces.Menssage;
import logic.auxiliars.dataOfInterfaces.PrincipalData;
import logic.auxiliars.tree.DecisionNode;
import logic.clases.character.Answer;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;
import logic.clases.character.PrincipalCharacter;
import logic.clases.event.Event;
import logic.clases.event.Situation;

import java.util.ArrayList;
import java.util.List;

public class Scenary{
    private Event event;
    private ArrayList<String> deathCasesPath; //Es un arraylist con las direcciones de los casos de muerte


    // Pedir al evento dado una selección una situación
    // Poder dar al MVC la imagen a cargar
    // Poder pedir al juego un nuevo evento

    /// ==== Constructor ====
    public Scenary() {
        deathCasesPath = new ArrayList<>();
        deathCasesPath.add("/visualResources/gameOver/cafeinaMAX.png");
        deathCasesPath.add("/visualResources/gameOver/cafeinaMIN.png");
        deathCasesPath.add("/visualResources/gameOver/estudiosMAX.png");
        deathCasesPath.add("/visualResources/gameOver/estudiosMIN.png");
        deathCasesPath.add("/visualResources/gameOver/popularidadMAX.png");
        deathCasesPath.add("/visualResources/gameOver/popularidadMIN.png");
        deathCasesPath.add("/visualResources/gameOver/dineroMAX.png");
        deathCasesPath.add("/visualResources/gameOver/dineroMIN.png");
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
        System.out.println("La siguiente situacion pertenece a personaje :" + s.getAssociation().getIdCharacter() + "\nVinculada al dialogo: " + s.getAssociation().getIdDialogueCharacter() + "\nCon respuesta del personaje principal:" + s.getAssociation().getIdAnswer());
        List<Menssage> dialogues = new ArrayList<>();
        Dialogue seconDialogue = s.getCharacterDialogue(s.getAssociation().getIdCharacter());//Se carga el dialogo del personaje secundario
        dialogues.add(new Menssage(seconDialogue.getContent(), giveCharacterName(), findPathAvatarCharacter()));
        if (s.getAssociation().getIdAnswer() != null) {//Si existe respuesta posible del jugador también se cargan
            Answer a = s.getPrincipalAnswers(s.getAssociation().getIdAnswer());
            Dialogue[] answers = a.getAnswers();
            for (Dialogue answer : answers) {
                dialogues.add(new Menssage(answer.getContent(), Game.getInstance().getMainCharacter().getName(), null));
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
    public String giveDeath (){
        Game game = Game.getInstance();
        int index = Integer.parseInt(game.getMainCharacter().causeOfDeath());

        return deathCasesPath.get(index);
    }

    public ArrayList<Integer> findStats() {
        ArrayList<Integer> stats = Game.getInstance().getMainCharacter().getStats();
        return stats;
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
