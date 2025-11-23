package logic.clases.character;

import java.io.Serializable;

public class Consequence implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Integer[] Rconsequences;
    private  Integer[] Lconsequeces;

    public Consequence(String id) {
        this.id = id;
        Rconsequences = new Integer[4];
        Lconsequeces = new Integer[4];
    }
    public Consequence(String id, Integer[] c1, Integer[] c2){
        this.id = id;
        Rconsequences = c1;
        Lconsequeces = c2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer[] getRconsequences() {
        return Rconsequences;
    }

    public void setRconsequences(Integer[] rconsecuences) {
        Rconsequences = rconsecuences;
    }

    public Integer[] getLconsequeces() {
        return Lconsequeces;
    }

    public void setLconsequeces(Integer[] lconsecueces) {
        Lconsequeces = lconsecueces;
    }
}
