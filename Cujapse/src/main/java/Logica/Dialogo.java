package Logica;

public class   Dialogo {
    private String id;
    private String contenido;

    //====Constructor=======
    public Dialogo(String id, String contenido) {
        setContenido(contenido);
        setId(id);
    }

    //=====Guetters y Setters========
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
