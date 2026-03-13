package logic.clases.character;

import java.io.Serializable;

///Esta clase es lo que tiene todo lo referente a las respuestas del personaje principal con su respectivas consecuencias
public class Answer  implements Serializable {
    private String id;
    private Dialogue[] answers;
    private Consecuence consecuence;


    /// Nota:
    /// Se puede modificar para que guarde un ID de la consecuencia
    ///
    /// ==== Constructor ====
    public Answer(String id, Dialogue dial1, Dialogue dial2, Consecuence consecuence) {
        setId(id);
        this.answers = new Dialogue[2];
        this.setAnswers(dial1,dial2);
        this.consecuence = consecuence;
    }

    /// ==== Getters and Setters ====
    public Dialogue[] getAnswers() {
        return answers;
    }

    private void setAnswers(Dialogue dial1, Dialogue dial2) {
        answers[0] = dial1;
        answers[1] = dial2;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    public void setAnswers(Dialogue[] answers) {
        this.answers = answers;
    }

    public Consecuence getConsecuence() {
        return consecuence;
    }

    public void setConsecuence(Consecuence consecuence) {
        this.consecuence = consecuence;
    }
}
