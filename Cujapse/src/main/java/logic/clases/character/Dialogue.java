package logic.clases.character;

import java.io.Serializable;

public class Dialogue implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String contenido;

    //====Constructor=======
    public Dialogue(String id, String contenido) {
        setContenido(contenido);
        setId(id);
    }

    //=====Getters y Setters========
    public String getId() {
        return id;
    }
    public void setId(String id) {
        if(!id.trim().isEmpty()){
            this.id = id;
        }
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        if(!contenido.trim().isEmpty()){
            this.contenido = contenido;
        }
    }

}
