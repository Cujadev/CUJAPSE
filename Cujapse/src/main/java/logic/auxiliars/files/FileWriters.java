package logic.auxiliars.files;

import logic.clases.character.Consecuence;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/// Se encarga de escribir en los achivos
/// Nota: Se debe tener en cuenta la misma observación
public class FileWriters {

    /// Métodos útiles

    // Abre un archivo
    public static RandomAccessFile openFile (File file){
        RandomAccessFile raf = null;
        try{
             raf = new RandomAccessFile(file, "rw");
        }catch (IOException e){
            e.printStackTrace();
        }
        return raf;
    }

    //Cierra el archivo
    public static void closeFile(RandomAccessFile raf){
        try {
            raf.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //Guarda un personaje en el fichero
    public static  void saveCharacter(GameCharacter character, File file){
        int cant = 0;
        RandomAccessFile  raf = null;
        try {
            if (file.exists()){
                raf = openFile(file);
                cant = raf.readInt();
            }
            else{
                raf = openFile(file);
            }
            raf.seek(0);
            raf.writeInt(cant + 1);
            byte[] string = Convert.toBytes(character);
            raf.seek(raf.length());
            raf.writeInt(string.length);
            raf.write(string);
            closeFile(raf);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Guarda un Dialogo en el fichero
    public  static void saveDialogue (Dialogue dialogue, File file){
        RandomAccessFile raf = null;
        int cant = 0;

        try{
            if (file.exists()){
                raf =openFile(file);
                cant = raf.readInt();
            }
            else{
                raf = openFile(file);
            }
            raf.seek(0);
            raf.writeInt(cant + 1);
            byte[] string = Convert.toBytes(dialogue);
            raf.seek(raf.length());
            raf.writeInt(string.length);
            raf.write(string);
            closeFile(raf);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // Guarda la consecuencia
    public  static void saveConsecuence (Consecuence consecuence, File file){
        RandomAccessFile raf = null;
        int cant = 0;

        try{
            if (file.exists()){
                raf =openFile(file);
                cant = raf.readInt();
            }
            else{
                raf = openFile(file);
            }
            raf.seek(0);
            raf.writeInt(cant + 1);
            byte[] string = Convert.toBytes(consecuence);
            raf.seek(raf.length());
            raf.writeInt(string.length);
            raf.write(string);
            closeFile(raf);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
