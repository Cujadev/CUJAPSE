package logic.auxiliars.files;

import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileWriters {

    public static RandomAccessFile openFile (File file){
        RandomAccessFile raf = null;
        try{
             raf = new RandomAccessFile(file, "rw");
        }catch (IOException e){
            e.printStackTrace();
        }
        return raf;
    }

    public static void closeFile(RandomAccessFile raf){
        try {
            raf.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

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
}
