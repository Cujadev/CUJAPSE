package logic.clases.character;

import logic.auxiliars.files.FileReaders;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

public class PrincipalCharacter extends GameCharacter {

    private ArrayList <Answer> answers;
    private ArrayList <Integer> stats;
    private File consecuences;

    /// Constructor Getters y Setters ///
    public PrincipalCharacter(String id, String name, String imagenPath, String dialoguePath, String consecuensesPath) {
        super(id, name, imagenPath, dialoguePath);
        stats =  new ArrayList<>();
        for (int i = 0; i < 4; i++){
            stats.add(50);
        }
        answers = new ArrayList<Answer>();
        setAnswers();
        consecuences = new File(consecuensesPath);
    }
    public void setAnswers () throws IllegalArgumentException {
        RandomAccessFile draf = FileReaders.openFile(dialogues);
        RandomAccessFile craf = FileReaders.openFile(consecuences);
        ArrayList<Dialogue> dialogues = FileReaders.chargeDialogues(draf);
        FileReaders.closeFile(draf);
        int count = 1;

        Iterator<Dialogue> it = dialogues.iterator();

        while (it.hasNext()) {
            Dialogue d1 = it.next();
            Dialogue d2 = it.next();

            String answerID = "" + count++;
            Consecuence c = FileReaders.searchConsecuence(answerID,craf);
            Answer a = new Answer(answerID,d1,d2,c);

            d1.setId(answerID);
            d2.setId(answerID);
            answers.add(a);
        }
        FileReaders.closeFile(craf);
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

    // Modificar las estadísticas
    public void modifyStats (int election, String idAnswer){
        Consecuence c = chargeAnswer(idAnswer).getConsecuence();
        Integer[] consecuenses;
        if (election == 1){
            consecuenses = c.getLconsecueces();
        }
        else if (election == 2){
            consecuenses = c.getRconsecuences();
        }
        else {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < 4; i++){
            if (consecuenses[i] == 1 || consecuenses[i] == 2){
                stats.set(i, subbStat(stats.get(i), consecuenses[i]));
            }
            if (consecuenses[i] == 3 || consecuenses[i] == 4){
                stats.set(i,incrementStat(stats.get(i), consecuenses[i]));
            }
        }
    }
    private  int subbStat (int stat, int consecuence)  throws  IllegalArgumentException{
        int result = stat;
        if (consecuence == 1){
            result -= 25;
        }
        else if (consecuence == 2){
            result -= 50;
        }
        return result;
    }
    private int incrementStat(int stat, int consecuence){
        int result = stat;
        if (consecuence == 3){
            result += 25;
        }
        if (consecuence == 4){
            result += 50;
        }
        return result;
    }

    public boolean isDead (){
        boolean found = false;
        for (int i = 0; i < stats.size() && !found; i++){
            if (stats.get(i) <= 0 ||stats.get(i) >= 100){
                found = true;
            }
        }
        return found;
    }
    public String causeOfDeath(){
        String id = "";
        boolean found = false;
        int index = 0;
        for (int i = 0; i < stats.size() && !found; i++){
            if (stats.get(i) <= 0 || stats.get(i) >= 100){
                index = i;
            }
        }

        switch (index){
            case 0:{
                if (stats.get(index) <= 0){
                    id = "0";
                }
                else{
                    id = "1";
                }
                break;
            }
            case 1:{
                if (stats.get(index) <= 0){
                    id = "2";
                }
                else{
                    id = "3";
                }
                break;
            }
            case 2:{
                if (stats.get(index) <= 0){
                    id = "4";
                }
                else{
                    id = "5";
                }
                break;
            }
            case 3:{
                if (stats.get(index) <= 0){
                    id = "6";
                }
                else{
                    id = "7";
                }
                break;
            }
        }
        return id;
    }

    //Modificar Características

}
