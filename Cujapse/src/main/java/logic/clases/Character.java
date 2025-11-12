package logic.clases;

import javafx.scene.image.Image;
import  logic.auxiliars.chargers.CargadorMensaje;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;
import java.util.RandomAccess;

/// Constructor, Getters y setters

public class Character implements CargadorMensaje {
    private String id;
    private  String name;
    private Image image;
    private File dialogues;

    public Character(String id, String name, Image image, String direction) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.dialogues = new File (direction);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public File getDialogues() {
        return dialogues;
    }

    public void setDialogues(File dialogues) {
        this.dialogues = dialogues;
    }

    //Implementar cargar dialogo

    @Override
    public Dialogue cargarDialogo(String id) {
        Dialogue menssage;

        return menssage;
    }
}

