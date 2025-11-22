package logic.clases.character;

import logic.auxiliars.chargers.ChargerMenssage;
import logic.auxiliars.files.FileReaders;

import java.io.RandomAccessFile;
import java.io.Serializable;

/// Constructor, Getters y setters

public class GameCharacter implements ChargerMenssage, Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected String imagePath;
    protected String dialoguesPath;

    public GameCharacter(String id, String name, String imagePath, String dialoguesPath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.dialoguesPath = dialoguesPath;
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
        Dialogue menssage = null;
        RandomAccessFile raf = FileReaders.openFile(FileReaders.returnFile(dialoguesPath));
        Dialogue dialogue = FileReaders.searchDialogue(id,raf);
        FileReaders.closeFile(raf);
        return dialogue;
    }

    /*
    // para sacar del fichero solo el dialgo que se necesita
    public Dialogue findDialogue(Object id) throws IOException, ClassNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(dialogues,"r");
        boolean found = false;
        Dialogue dialogue = null;
        int numeroID = -1;
        if(id instanceof String){
            numeroID = Integer.parseInt((String) id);
        }else if(id instanceof Integer){
            numeroID = (Integer) id;
        }

        while(raf.getFilePointer() < raf.length() && !found){
            int idDialogue = raf.readInt();
            if(idDialogue == numeroID){
                found = true;
                int tamanioDialogue = raf.readInt();
                byte[] bytesDialogue = new byte[tamanioDialogue];
                raf.readFully(bytesDialogue);
                dialogue = (Dialogue) Convert.toObject(bytesDialogue);
            }else{
                int tamanio = raf.readInt();
                raf.skipBytes(tamanio);
            }
        }
        raf.close();
        return dialogue;
    }*/

    /*// guardar un dialogo nuevo
    public boolean saveDialogue(Dialogue dialogue) throws IOException {
        boolean ok = false;
        int numeroID = -1;
        Object id;
        RandomAccessFile raf = new RandomAccessFile(dialogues, "rw");
        if(dialogue != null){
            id = dialogue.getId();
            if(id instanceof String){
                numeroID = Integer.parseInt((String) id);
            }else if(id instanceof Integer){
                numeroID = (Integer) id;
            }
            raf.seek(raf.length());
            raf.writeInt(numeroID);
            byte[] dialogueBytes = Convert.toBytes(dialogue);
            raf.writeInt(dialogueBytes.length);
            raf.write(dialogueBytes);
            ok = true;
        }
        raf.close();
        return ok;
    }*/


}

