import logic.auxiliars.files.FileReaders;
import logic.auxiliars.initializers.InitNewGame;
import logic.clases.character.GameCharacter;
import logic.clases.event.Event;
import logic.clases.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class InitGameTest {
    private Game game;
    private ArrayList<String> characters;

    @BeforeEach
    public void setup(){
        //Se instancia un nuevo juego
        game = Game.getInstance();
        characters = new ArrayList<>();
        characters.add("1");
        characters.add("2");
        characters.add("3");
    }
    @Test
    void verifySingleton(){
        Game game2 = Game.getInstance();
        Assertions.assertSame(game,game2,"El singleton no funciona");
    }

    @Test
    void verifyNotnullMainCharacter(){
        Assertions.assertNotNull(game.getMainCharacter(), "El main character  esta nulo");
    }
    @Test
    void veryfyExistenceCharacters (){
        Assertions.assertTrue(game.getPersonajesFichero().exists(),"El fichero no ha sido detectado");
    }

    @Test
    void verifyIntegritiOfcharacters(){
        for (String character : characters){
            GameCharacter c = game.findCharacter(character);
            Assertions.assertNotNull(c,"El character: "+ character+ "esta nulo");
            Assertions.assertTrue(FileReaders.returnFile(c.getDialoguesPath()).exists(), "No existe dirección para el archivo del personaje: " + character);
        }
    }
    @Test
    void verifyListOfReturn(){
        List<String> list = game.startNewGame();
        Assertions.assertTrue(!list.isEmpty(),"Existen problemas con la inicialización de los dialogos del turorial");
        System.out.println(list.get(0));
        Assertions.assertTrue(!list.get(0).isEmpty(), "La primera linea está vácia");
    }
    @Test
    void verifyQuequeOfEvents(){
        List<String> list = game.startNewGame();
        Queue <Event> queue = game.getEventQueue();
        Assertions.assertTrue(!queue.isEmpty(), "Se han detectado problemas a la hora de crear eventos");
        if (!queue.isEmpty()){
            Event e = queue.poll();
            Assertions.assertNotNull(e.getSituations().getRoot().getLeft(), "El nodo izquierdo está vacio");
            Assertions.assertNotNull(e.getSituations().getRoot().getRight(), "El nodo derecho está vacio");
        }
    }
    @Test
    void verifyCreateFirstEvent(){
        Assertions.assertNotNull(InitNewGame.createFirstEvent(),"No se crea bien el evento");
    }

    @AfterEach
    public void teardown(){}
}
