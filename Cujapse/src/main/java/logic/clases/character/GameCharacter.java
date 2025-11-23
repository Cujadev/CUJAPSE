package logic.clases.character;

import javafx.scene.control.Dialog;
import logic.auxiliars.chargers.ChargerMenssage;
import logic.auxiliars.files.Convert;
import logic.auxiliars.files.FileReaders;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

/// Constructor, Getters y setters

public class GameCharacter implements ChargerMenssage, Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected transient File dialogues;
    protected String imagePath;
    protected String dialoguesPath;

    public GameCharacter(String id, String name, String imagePath, String dialoguesPath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.dialoguesPath = dialoguesPath;
        try {
            loadResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public File getDialogues() {
        return dialogues;
    }

    public void setDialogues(File dialogues) {
        this.dialogues = dialogues;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDialoguesPath() {
        return dialoguesPath;
    }

    public void setDialoguesPath(String dialoguesPath) {
        this.dialoguesPath = dialoguesPath;
    }

    //Implementar cargar dialogo

    @Override
    public Dialogue ChargeDialogue(String id) {
        Dialogue message = null;
        RandomAccessFile raf = FileReaders.openFile(this.dialogues);
        ArrayList <Dialogue> dialogues = FileReaders.chargeDialogues(raf);
        FileReaders.closeFile(raf);
        ListIterator <Dialogue> it = dialogues.listIterator();

        while(it.hasNext() && message == null){
            Dialogue d = it.next();
            if (d.getId().equals(id))
                message = d;
        }
        return message;
    }

    // para cargar los recursos y poder meter esto en un fichero
    public void loadResources() throws IOException {
        if (dialoguesPath != null) {
            this.dialogues = new File(dialoguesPath);
            if (!this.dialogues.exists()) {
                this.dialogues.createNewFile();
            }
        }
    }


    // Para sacar del fichero solo el diálogo que se necesita
    public Dialogue findDialogue(String id) throws IOException, ClassNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(dialogues,"r");
        Dialogue dialogue = null;

        while(raf.getFilePointer() < raf.length() && dialogue == null){
           int size = raf.readInt();
           byte[] array = new byte[size];
           raf.read(array);
           Dialogue d = (Dialogue)Convert.toObject(array);
           if(d.getId().equalsIgnoreCase(id))
               dialogue = d;
        }
        raf.close();

        return dialogue;
    }

    // Guardar un diálogo nuevo
    public boolean saveDialogue(Dialogue dialogue) throws IOException {
        boolean ok = false;
        RandomAccessFile raf = new RandomAccessFile(dialogues, "rw");

        if(dialogue != null){
            raf.seek(raf.length());
            byte[] dialogueBytes = Convert.toBytes(dialogue);
            raf.writeInt(dialogueBytes.length);
            raf.write(dialogueBytes);
            ok = true;
        }
        raf.close();

        return ok;
    }
}

