package logic.clases.character;

import java.io.Serializable;

public class Consecuence implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Integer[] Rconsecuences;
    private  Integer[] Lconsecueces;

    public Consecuence(String id) {
        this.id = id;
        Rconsecuences = new Integer[4];
        Lconsecueces = new Integer[4];
    }
    public Consecuence(String id, Integer[] c1, Integer[] c2){
        this.id = id;
        Rconsecuences = c1;
        Lconsecueces = c2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer[] getRconsecuences() {
        return Rconsecuences;
    }

    public void setRconsecuences(Integer[] rconsecuences) {
        Rconsecuences = rconsecuences;
    }

    public Integer[] getLconsecueces() {
        return Lconsecueces;
    }

    public void setLconsecueces(Integer[] lconsecueces) {
        Lconsecueces = lconsecueces;
    }
}
