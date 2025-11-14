package logic.clases;

import logic.auxiliars.chargers.ChargerSituation_Dialogue;

public class Situation {

    private ChargerSituation_Dialogue association;

    public Situation(ChargerSituation_Dialogue association){
        this.association = association;
    }

    // Obtener el diálogo del personaje
    public Dialogue getCharacterDialogue(){
        Game game = Game.getInstance();
        Character c = game.findCharacter(association.getIdCharacter());
        return c.ChargeDialogue(association.getIdDialogueCharacter());
    }

    // Obtener las respuestas del personaje principal
    public Answer[] getPrincipalAnswers(){
        Game game = Game.getInstance();
        PrincipalCharacter pc = game.getMainCharacter();
        String[] ids = association.getIdDialoguePrincipal();

        Answer[] result = new Answer[ids.length];
        for (int i = 0; i < ids.length; i++){
            result[i] = pc.chargeAnswer(ids[i]);
        }
        return result;
    }
}
