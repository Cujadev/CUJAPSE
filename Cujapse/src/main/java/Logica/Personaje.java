package Logica;

import javax.swing.*;
import java.util.Map;

public class Personaje implements CargadorMensaje {
    private String id;
    private  String nombre;
    private ImageIcon imagen;// ver tipo de dato a usar para las imagenes
    private  Map<String, Dialogo> dialogos;// Supongo que lo que se quiere es esto

    //=====Constructor=====
    public Personaje(String id, String nombre, ImageIcon imagen, Map<String, Dialogo> dialogos) {
        setId(id);
        setNombre(nombre);
        setImagen(imagen);
        setDialogos(dialogos);
    }

    //=====Guettes y Setters =====
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(!id.trim().isEmpty()){
            this.id = id;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(!nombre.trim().isEmpty()){
            this.nombre = nombre;
        }
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public Map<String, Dialogo> getDialogos() {
        return dialogos;
    }

    public void setDialogos(Map<String, Dialogo> dialogos) {
        this.dialogos = dialogos;
    }

    public Dialogo cargarDialogo(String id) {
        return dialogos.get(id);
    }

}

