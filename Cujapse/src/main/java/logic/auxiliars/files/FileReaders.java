package logic.auxiliars.files;


import logic.clases.Dialogue;
import logic.clases.Character;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;



public class FileReaders {

    public static RandomAccessFile openFile (File file) {
        RandomAccessFile returned = null;
        try {
            returned = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returned;
    }
    public static boolean closeFile (RandomAccessFile arch){
        try {
            arch.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }
    public static ArrayList<Dialogue> chargeDialogues(RandomAccessFile raf) {
        ArrayList <Dialogue> dialogues = null;

        try {
            int cant = raf.readInt();
            for (int i = 0; i < cant; i++){
                int tam = raf.readInt();
                byte[] string = new byte[tam];
                raf.read(string);
                Dialogue d = (Dialogue) Convert.toObject(string);
                if (d != null) {
                    dialogues.add(d);
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return dialogues;
    }

    public static Character findCharacter (String id, RandomAccessFile raf){
        Character returned = null;
        try{
           int cant = raf.readInt();
           for (int i = 0; i < cant; i++){
               int tam = raf.readInt();
               byte[] string = new byte[tam];
               raf.read(string);
               Character c = (Character) Convert.toObject(string);
               if (c.getId().equals(id)){
                   returned = c;
               }

           }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return  returned;
    }
}
