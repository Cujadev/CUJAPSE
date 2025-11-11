package logic.clases;

import logic.auxiliars.chargers.CargadorMensaje;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Character implements CargadorMensaje {
    private String id;
    private  String nombre;
    private ImageIcon imagen;// ver tipo de dato a usar para las imagenes
    private ArrayList<Dialogue> dialogos;// Supongo que lo que se quiere es esto
    private File archivo;

    //=====Constructor=====
    public Character(String id, String nombre, ImageIcon imagen, ArrayList<Dialogue> dialogos) {
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

    public ArrayList<Dialogue> getDialogos() {
        return dialogos;
    }

    public void setDialogos(ArrayList<Dialogue> dialogos) {
        this.dialogos = dialogos;
    }

    //====== Asi se cumple la mierda de ellos de no romper for y eso ======
    public Dialogue cargarDialogo(String id) {
        boolean encontrado = false;
        int tope = dialogos.size();
        int contador = 0;
        Dialogue dialogo = null;

        while(!encontrado && contador < tope){
            if(dialogos.get(contador).getId().equals(id)){
                dialogo = dialogos.get(contador);
                encontrado = true;
            }
            contador++;
        }
        return dialogo;
    }

}

