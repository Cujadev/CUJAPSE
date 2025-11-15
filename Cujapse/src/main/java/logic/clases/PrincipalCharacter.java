package logic.clases;

import javafx.scene.image.Image;
import logic.auxiliars.files.FileReaders;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

public class PrincipalCharacter extends GameCharacter {
    private int caffeine;
    private int popularity;
    private int money;
    private int study;
    private ArrayList <Answer> answers;

    /// Constructor Getters y Setters ///
    public PrincipalCharacter(String id, String name, String imagenPath, String direction) {
        super(id, name, imagenPath, direction);
        setCaffeine(50);
        setPopularity(50);
        setMoney(50);
        setStudy(50);
        answers = new ArrayList<Answer>();
        setAnswers();
    }

    public int getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(int caffeine) {
        this.caffeine = caffeine;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }

    public void setAnswers () throws IllegalArgumentException {
        RandomAccessFile raf = FileReaders.openFile(dialogues);
        ArrayList<Dialogue> dialogues = FileReaders.chargeDialogues(raf);
        FileReaders.closeFile(raf);
        int count = 1;

        Iterator<Dialogue> it = dialogues.iterator();

        while (it.hasNext()) {
            Dialogue d1 = it.next();
            Dialogue d2 = it.next();

            String answerID = "" + count++;
            Answer a = new Answer(answerID,d1,d2);

            d1.setId(answerID);
            d2.setId(answerID);
            answers.add(a);
        }
    }
    public Answer chargeAnswer (String id){
        boolean found = false;
        Answer a = null;

        if (!answers.isEmpty()){
            Iterator <Answer> it = answers.iterator();
            while (it.hasNext() && !found){
                a = it.next();
                if (a.getId().equals(id))
                    found = true;
            }
        }
        return a;
    }
}
