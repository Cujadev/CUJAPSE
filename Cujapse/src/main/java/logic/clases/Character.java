package logic.clases;

import javafx.scene.image.Image;

import logic.auxiliars.chargers.ChargerMenssage;
import logic.auxiliars.files.FileReaders;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.ListIterator;

/// Constructor, Getters y setters

public class Character implements ChargerMenssage {
    protected String id;
    protected  String name;
    protected Image image;
    protected File dialogues;

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
    public Dialogue ChargeDialogue(String id) {
        Dialogue menssage = null;
        RandomAccessFile raf = FileReaders.openFile(this.dialogues);
        ArrayList <Dialogue> dialogues = FileReaders.chargeDialogues(raf);
        FileReaders.closeFile(raf);
        ListIterator <Dialogue> it = dialogues.listIterator();
        boolean found = false;

        while(it.hasNext() && !found){
            if (it.next().getId().equals(id)){
                menssage = it.previous();
            }
        }
        return menssage;
    }
}

