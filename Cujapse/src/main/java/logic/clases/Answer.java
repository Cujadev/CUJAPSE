package logic.clases;


public class Answer {
    private String id;
    private final Dialogue [] answers;

    public Answer(String id, Dialogue dial1, Dialogue dial2) {
        setId(id);
        this.answers = new Dialogue[2];
        this.setAnswers(dial1,dial2);
    }

    public Dialogue[] getAnswers() {
        return answers;
    }

    private void setAnswers(Dialogue dial1, Dialogue dial2) {
        answers[1] = dial1;
        answers[2] = dial2;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }
}
