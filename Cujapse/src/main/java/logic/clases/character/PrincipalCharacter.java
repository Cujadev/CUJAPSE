package logic.clases.character;

import logic.auxiliars.files.FileReaders;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;

public class PrincipalCharacter extends GameCharacter {

    private final ArrayList<Answer> answers;
    private  ArrayList<Integer> stats;
    private final String consequences;
    private ArrayList <String> idEvents;

    /// ==== Constructor ====
    public PrincipalCharacter(String id, String name, String imagenPath, String dialoguePath, String consequensesPath) {
        super(id, name, imagenPath, dialoguePath);
        stats = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            stats.add(50);
        }
        answers = new ArrayList<Answer>();
        consequences = consequensesPath;
        setAnswers();

    }
    /// Getters And Setters
    public String getConsequences() {
        return consequences;
    }

    public ArrayList<String> getIdEvents() {
        return idEvents;
    }

    public void setIdEvents(ArrayList<String> idEvents) {
        this.idEvents = idEvents;
    }
    public void addIdEvent(String event){
        idEvents.add(event);
    }


    public ArrayList<Integer> getStats() {
        return stats;
    }

    public void setStats(ArrayList<Integer> stats) {
        this.stats = stats;
    }
    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    /// ==== Métodos útiles ====
    public void setAnswers() throws IllegalArgumentException {
        RandomAccessFile draf = FileReaders.openFile(FileReaders.returnFile(dialoguesPath));//Abre el fichero de dialogos
        RandomAccessFile craf = FileReaders.openFile(FileReaders.returnFile(consequences));//Abre el fichero de consecuencia
        ArrayList<Dialogue> dialogues = FileReaders.findDialogues(draf);// Se cargan todos los dialogos del personaje principal
        FileReaders.closeFile(draf);// Se cierra el fichero

        Iterator<Dialogue> it = dialogues.iterator();

        /*
         * Para que este código funcione bien, el personaje principal debe tener 2 diálogos consecutivos con el mismo id*/
        while (it.hasNext()) {
            Dialogue d1 = it.next();
            Dialogue d2 = it.next();

            String answerID = d1.getId();// Como el d1 y el d2 tienen el mismo ID se le asigna es te ID a la answer
            Consecuence c = FileReaders.searchConsecuence(answerID, craf);// Se busca la consecuencia que tiene tomar las distintas respuestas
            Answer a = new Answer(answerID, d1, d2, c);// Se crea la nueva answer
            answers.add(a);// Se agrega al arraylist
        }
        FileReaders.closeFile(craf);// Se cierra el fichero de las consecuencias
    }

    // Obtener la respuesta
    public Answer chargeAnswer(String id) {
        boolean found = false;
        Answer a = null;

        // En este fragmento buscamos la answer por el ID nada del otro mundo XD
        if (!answers.isEmpty()) {
            Iterator<Answer> it = answers.iterator();
            while (it.hasNext() && !found) {
                a = it.next();
                if (a.getId().equals(id))
                    found = true;
            }
        }
        return a;
    }

    // Modificar las estadísticas
    public void modifyStats(int election, String idAnswer) { //Se toma la eleccion y la ID de la respuesta dada en el guion
        Consecuence c = chargeAnswer(idAnswer).getConsecuence();// Se obtiene la consecuencia
        Integer[] consecuenses; // Se crea un Array de enteros
        if (election == 1) {// En dependencia si la consecuencia es  1 o 2
            consecuenses = c.getLconsecueces(); // Se toma el array que representa a las consecuencias de la izquierda
        } else if (election == 2) {
            consecuenses = c.getRconsecuences();// Se toma el array que representa a las consecuencias de la derecha
        } else {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < consecuenses.length; i++) {// por cada consecuencia y stat
            if (consecuenses[i] == 1 || consecuenses[i] == 2) {// si se cumple esto restamos
                stats.set(i, subbStat(stats.get(i), consecuenses[i]));
            }
            if (consecuenses[i] == 3 || consecuenses[i] == 4) {// si se cumple esto sumamo
                stats.set(i, incrementStat(stats.get(i), consecuenses[i]));
            }
        }
    }
    // Resta en dependencia de la consecuencia
    private  int subbStat (int stat, int consecuence)  throws  IllegalArgumentException{
        int result = stat;
        if (consecuence == 1){
            result -= 15; //Si es 1 se resta por 25
        }
        else if (consecuence == 2){
            result -= 25;// Si es 2 se resta por 50
        }
        return Math.max(result,0);// Se retorna el mayor entre 0 y resultado
    }
    // Sumar en dependencia de la consecuencia
    private int incrementStat(int stat, int consecuence){
        int result = stat;
        if (consecuence == 3){
            result += 15;// Si es 3 se resta por 25
        }
        if (consecuence == 4){
            result += 25;// Si es 4 se resta por 50
        }
        return Math.min(result, 100);// Se retorna el menor entre resultado y 100
    }

    // Verifica si el personaje sigue vivo
    public boolean isDead (){
        boolean found = false;
        for (int i = 0; i < stats.size() && !found; i++) { // Se recorren todas las stats
            if ((stats.get(i) == 0) || (stats.get(i) == 100)) {// se verifica si se cumplen algunos de los valores límite
                found = true;
            }
        }
        return found;
    }

    // Se devuelve la causa de muerte para que el escenario pueda dar todo lo necesario
    public String causeOfDeath(){ //
        String id = "";
        boolean found = false;
        int index = 0;
        for (int i = 0; i < stats.size() && !found; i++){// Se verifica que stat no cumple
            if (stats.get(i) <= 0 || stats.get(i) >= 100){
                index = i;
            }
        }

        switch (index){// Se verifica en esa stat el tipo de muerte
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
}
