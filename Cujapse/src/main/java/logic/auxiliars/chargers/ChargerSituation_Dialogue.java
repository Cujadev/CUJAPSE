package logic.auxiliars.chargers;

public class ChargerSituation_Dialogue {
    private String idCharacter;
    private String idDialogueCharacter;
    private String [] idDialoguePrincipal;

    public ChargerSituation_Dialogue(String idCharacter, String idDialogueCharacter, String[] idDialoguePrincipal) {
        setIdCharacter(idCharacter);
        setIdDialogueCharacter(idDialogueCharacter);
        setIdDialoguePrincipal(idDialoguePrincipal);
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

    public String[] getIdDialoguePrincipal() {
        return idDialoguePrincipal;
    }

    public void setIdDialoguePrincipal(String[] idDialoguePrincipal) {
        this.idDialoguePrincipal = idDialoguePrincipal;
    }
}
