import logic.auxiliars.dataOfInterfaces.PrincipalData;
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

        Assertions.assertTrue(data.getMessages().size() == 3, "No se estan tomando bien los datos de las respuestas");
    }
}
