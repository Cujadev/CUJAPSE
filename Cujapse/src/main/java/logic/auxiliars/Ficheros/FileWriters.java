package logic.auxiliars.Ficheros;

import logic.auxiliars.files.Convert;
import logic.clases.Dialogue;
import logic.clases.GameCharacter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public boolean saveDialogue(Dialogue dialogue, String direccionFile) throws IOException {
        int numeroId = -1;
        boolean ok = false;
        try {
            Path path = Paths.get(direccionFile);

            if(Files.exists(path)) {
                RandomAccessFile raf = new RandomAccessFile(direccionFile,"rw");
                raf.seek(raf.length());
                if(dialogue != null) {
                    Object id = dialogue.getId();
                    if (id instanceof String) {
                        numeroId =  Integer.parseInt((String) id);
                    }else if (id instanceof Integer) {
                        numeroId = (Integer) id;
                    }

                    raf.writeInt(numeroId);
                    byte[] bytesDialogue = Convert.toBytes(dialogue);
                    raf.writeInt(bytesDialogue.length);
                    raf.write(bytesDialogue);
                    ok = true;
                }else{
                    throw new IllegalArgumentException("Dialogo nulo");
                }
                raf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       return ok;
    }


}
