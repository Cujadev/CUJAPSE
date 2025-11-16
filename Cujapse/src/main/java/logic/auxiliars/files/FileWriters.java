package logic.auxiliars.Ficheros;

import logic.auxiliars.files.Convert;
import logic.clases.Dialogue;
import logic.clases.GameCharacter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
public class FileWriters {
    private File fichero;

    public FileWriters(String nombreArchivo) {
        this.fichero = new File(nombreArchivo);
        try {
            if (!this.fichero.exists()) {
                this.fichero.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveCharacter(GameCharacter character) throws IOException {
        int numeroId = -1;
        RandomAccessFile raf = new RandomAccessFile(fichero,"rw");
        boolean ok = false;
        if (character != null) {
            Object id = character.getId();
            if (id instanceof String) {
                numeroId = Integer.parseInt((String) id);
            }else if (id instanceof Integer) {
                numeroId = (Integer) id;
            }
            raf.seek(raf.length());
            raf.writeInt(numeroId);
            byte[] bytesCharacter = Convert.toBytes(character);
            raf.writeInt(bytesCharacter.length);
            raf.write(bytesCharacter);
            ok = true;
        }
        raf.close();
        return ok;
    }

    public GameCharacter findCharacter(Object id) throws IOException, ClassNotFoundException {
        boolean found = false;
        GameCharacter character = null;
        RandomAccessFile raf = new RandomAccessFile(fichero,"r");
        int numeroID = -1;
        if(id instanceof String){
            numeroID = Integer.parseInt((String) id);
        }else if(id instanceof Integer){
            numeroID = (Integer) id;
        }
        while(raf.getFilePointer() < raf.length() && !found){
            int identificador = raf.readInt();
            if(identificador == numeroID){
                found = true;
                int tammanio = raf.readInt();
                byte[] bytesIdentificador = new byte[tammanio];
                raf.readFully(bytesIdentificador);
                character = (GameCharacter) Convert.toObject(bytesIdentificador);
                character.loadResources();
            }else{
                int tamanio = raf.readInt();
                raf.skipBytes(tamanio);
            }
        }
        raf.close();
        return character;
    }

    public Dialogue findDialogue(Object idGameCharacter, Object idDialogue) throws IOException, ClassNotFoundException {
        GameCharacter character = findCharacter(idGameCharacter);
        Dialogue dialogue = null;
        if(character != null){
            dialogue = character.findDialogue(idDialogue);
        }
        return dialogue;
    }


}
