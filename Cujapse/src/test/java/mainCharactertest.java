import interfaz.controllers.PrincipalController;
import logic.auxiliars.files.FileReaders;
import logic.clases.character.Answer;
import logic.clases.character.Dialogue;
import logic.clases.character.PrincipalCharacter;
import logic.clases.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class mainCharactertest {
    private String path;
    PrincipalCharacter character;
    String dialoguePath;

    @BeforeEach
    public void setup() {
        path = "/data/main_character/consecuencias.dat";
        character = Game.getInstance().getMainCharacter();
        dialoguePath = character.getDialoguesPath();
    }

    @Test
    public void consecuenceIntegritiTest() throws IOException {
        RandomAccessFile raf = FileReaders.openFile(FileReaders.returnFile(path));
        int cant = raf.readInt();
        System.out.println("cant = " + cant);
        raf.close();
    }

    @Test
    public void createAllAnswesrs() {
        ArrayList<Answer> answers = character.getAnswers();
        for (Answer answer : answers) {
            System.out.println(answer.getId());
            for (Dialogue dialogue : answer.getAnswers()){
                System.out.println(dialogue.getId());
                System.out.println(dialogue.getContenido());
            }
            System.out.println("id de la consecuencia:" + answer.getConsecuence().getId() + "\n" + Arrays.toString(answer.getConsecuence().getLconsecueces()) + "\n" + Arrays.toString(answer.getConsecuence().getRconsecuences()));
        }
        Assertions.assertTrue(answers.size() > 2, "Error a la hora de instanciar los objetos");
    }

    @Test
    public void cantDialogues () throws IOException {
        RandomAccessFile raf = FileReaders.openFile(FileReaders.returnFile(dialoguePath));
        int cant = raf.readInt();
        System.out.println("cant = " + cant);
        raf.close();
    }

    @AfterEach
    public void teardown() {
    }
}
