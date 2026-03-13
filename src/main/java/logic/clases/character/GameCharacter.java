package logic.clases.character;

import logic.auxiliars.chargers.ChargerMenssage;
import logic.auxiliars.files.FileReaders;
import logic.auxiliars.files.FileWriters;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.Serializable;



public class GameCharacter implements ChargerMenssage, Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected String imagePath;
    protected String dialoguesPath;

    /// ==== Constructor ====
    public GameCharacter(String id, String name, String imagePath, String dialoguesPath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.dialoguesPath = dialoguesPath;
    }

    ///  ==== Getters y Setters ====
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

    /// ==== Métodos útiles ====

    @Override
    // Esta función devuelve el diálogo para ser utilizado por el escenario
    public Dialogue ChargeDialogue(String id) {
        RandomAccessFile raf = FileReaders.openFile(FileReaders.returnFile(dialoguesPath));//Abre un RAF
        Dialogue dialogue = FileReaders.searchDialogue(id,raf);// Busca el diálogo según el ID dado
        FileReaders.closeFile(raf);// Cierra el Fichero
        return dialogue;
    }

    //Esta función guarda diálogos
    public void saveDialogue (Dialogue d){
        File f = FileReaders.returnFile(getDialoguesPath());// Crea el file
        FileWriters.saveDialogue(d,f);// Guarda el dialogo
    }
}
/*
Estos son los métodos creados por Rolando que de momento no son necesarios no se van a
borrar para tenerlos en cuenta caso de bug

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
    }

    // guardar un dialogo nuevo
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
    }
 */

