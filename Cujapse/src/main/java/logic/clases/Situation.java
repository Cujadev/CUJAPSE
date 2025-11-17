package logic.clases;

import logic.auxiliars.chargers.ChargerSituation_Dialogue;

public class Situation {

    private ChargerSituation_Dialogue association;

    public Situation(ChargerSituation_Dialogue association){
        this.association = association;
    }

    public ChargerSituation_Dialogue getAssociation (){
        return association;
    }

    // Obtener el diálogo del personaje
    public Dialogue getCharacterDialogue(String idCharacter){
        Game game = Game.getInstance();
        GameCharacter c = game.findCharacter(association.getIdCharacter());
        return c.ChargeDialogue(association.getIdDialogueCharacter());
    }

    // Obtener las respuestas del personaje principal
    public Answer getPrincipalAnswers(String id){
        Game game = Game.getInstance();
        PrincipalCharacter p = game.getMainCharacter();
        return p.chargeAnswer(id);
    }
}
