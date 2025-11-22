package logic.auxiliars.files;


import logic.clases.character.Consecuence;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;

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
        ArrayList <Dialogue> dialogues = new ArrayList<>();

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

    public static GameCharacter findCharacter (String id, RandomAccessFile raf){
        GameCharacter returned = null;
        try{
           int cant = raf.readInt();
           for (int i = 0; i < cant; i++){
               int tam = raf.readInt();
               byte[] string = new byte[tam];
               raf.read(string);
               GameCharacter c = (GameCharacter) Convert.toObject(string);
               if (c.getId().equals(id)){
                   returned = c;
               }
           }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return  returned;
    }
    public static Consecuence searchConsecuence (String id, RandomAccessFile raf){
        Consecuence c=null;
        boolean found = false;

        try {
            int cant = raf.readInt();
            for (int i = 0; i < cant && !found; i++){
                int tam = raf.readInt();
                byte[] string = new  byte[tam];
                c = (Consecuence) Convert.toObject(string);
                if (c.getId().equals(id)){
                    found = true;
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return c;
    }
    public static Dialogue searchDialogue (String id, RandomAccessFile raf){
        Dialogue dialogue = null;
        boolean found = false;

        try {
            int cant = raf.readInt();
            for (int i = 0; i < cant; i++){
                int tam = raf.readInt();
                byte[] string = new byte[tam];
                raf.read(string);
                Dialogue dial =  (Dialogue) Convert.toObject(string);
                if (dial.getId().equals(id)){
                    dialogue = dial;
                    found = true;
                }
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dialogue;
    }
}
