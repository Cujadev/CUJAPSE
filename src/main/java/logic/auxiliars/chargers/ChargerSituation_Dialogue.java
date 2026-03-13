package logic.auxiliars.chargers;

public class ChargerSituation_Dialogue {
    private String idCharacter;
    private String idDialogueCharacter;
    private String idAnswer;

    public ChargerSituation_Dialogue(String idCharacter, String idDialogueCharacter, String idAnswer) {
        setIdCharacter(idCharacter);
        setIdDialogueCharacter(idDialogueCharacter);
        setIdAnswer(idAnswer);
    }

    public String getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(String idCharacter) {
        this.idCharacter = idCharacter;
    }

    public String getIdDialogueCharacter() {
        return idDialogueCharacter;
    }

    public void setIdDialogueCharacter(String idDialogueCharacter) {
        this.idDialogueCharacter = idDialogueCharacter;
    }

    public String getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(String idAnswer) {
        this.idAnswer = idAnswer;
    }
}
