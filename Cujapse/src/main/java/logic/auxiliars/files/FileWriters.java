package logic.auxiliars.files;

import logic.auxiliars.files.Convert;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

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
        RandomAccessFile raf = new RandomAccessFile(fichero,"rw");
        boolean ok = false;
        if (character != null) {
           raf.seek(raf.length());
           byte[] array = Convert.toBytes(character);
           raf.writeLong(array.length);
           raf.write(array);
           ok = true;
           raf.close();
        }
        return ok;
    }

    public GameCharacter findCharacter(String id) throws IOException, ClassNotFoundException {
        boolean found = false;
        GameCharacter character = null;
        RandomAccessFile raf = new RandomAccessFile(fichero,"r");

        while(raf.getFilePointer() < raf.length() && character == null){
            int size = raf.readInt();
            byte[] array = new byte[size];
            raf.read(array);
            GameCharacter gameCharacter = (GameCharacter) Convert.toObject(array);
            if(gameCharacter.getId().equalsIgnoreCase(id))
                character = gameCharacter;
        }
        raf.close();

        return character;
    }

    public Dialogue findDialogue(String idGameCharacter, String idDialogue) throws IOException, ClassNotFoundException {
        GameCharacter character = findCharacter(idGameCharacter);
        Dialogue dialogue = null;
        if(character != null){
            dialogue = character.findDialogue(idDialogue);
        }
        return dialogue;
    }


}
