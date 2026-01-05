import logic.auxiliars.chargers.ChargerSituation_Dialogue;
import logic.auxiliars.dataOfInterfaces.PrincipalData;
import logic.clases.event.Event;
import logic.clases.event.Situation;
import logic.clases.game.Game;
import logic.clases.game.Scenary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Principal;

public class ScenaryTest {
    private  Scenary scenary;

    @BeforeEach
    public void setup() {
        Game.getInstance().startNewGame();
        scenary = Game.getInstance().getScenary();
    }

    @Test
    public void checkLengthMenssage(){
        int branch = 0;
        PrincipalData data = scenary.giveData(branch);
        System.out.println(data.getMessages().size());
        Assertions.assertTrue(data.getMessages().size() == 3, "No se estan tomando bien los datos de las respuestas");
    }

    @Test
    public void checkEventInescenary (){
        Event event = scenary.getEvent();
        Situation s =event.getNextSituaion(2);
        ChargerSituation_Dialogue ch = s.getAssociation();

        System.out.println(ch.getIdAnswer());
    }
}
