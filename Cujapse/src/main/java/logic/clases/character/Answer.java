package logic.clases.character;


public class Answer {
    private String id;
    private Dialogue[] answers;
    private Consequence consequence;


    public Answer(String id, Dialogue dial1, Dialogue dial2, Consequence consecuence) {
        setId(id);
        this.answers = new Dialogue[2];
        this.setAnswers(dial1,dial2);
        this.consequence = consecuence;
    }

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

    public Consequence getConsequence() {
        return consequence;
    }

    public void setConsequence(Consequence consequence) {
        this.consequence = consequence;
    }
}
