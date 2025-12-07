package logic.clases.character;

import java.io.Serializable;

// Esta clase es la encargada de obtener las consecuencias de las acciones del personaje principal
public class Consecuence implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Integer[] Rconsecuences;
    private  Integer[] Lconsecueces;

    /// ====Constructor====
    public Consecuence(String id) {// Constructor para una consecuenccia vacia
        this.id = id;
        Rconsecuences = new Integer[4];
        Lconsecueces = new Integer[4];
    }
    public Consecuence(String id, Integer[] c1, Integer[] c2){// Constructor para una consecuencia con todos los datos
        this.id = id;
        Rconsecuences = c1;
        Lconsecueces = c2;
    }

    /// ==== Getters and Setters ====
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
