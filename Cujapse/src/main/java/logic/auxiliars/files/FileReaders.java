package logic.auxiliars.files;


import logic.clases.character.Consecuence;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


/// Esta clase se encarga de leer archivos
/// Nota: Hay código que se repide y puede ser perfeccionado para unir 3 métodos en 1
public class FileReaders {

    /// Abre un RAF en modo de lectura
    public static RandomAccessFile openFile (File file) {
        RandomAccessFile returned = null;
        try {
            returned = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returned;
    }

    /// Cierra el RAF
    public static void closeFile (RandomAccessFile arch){
        try {
            if (arch != null) arch.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /// Busca el dialogo en el RAF
    public static ArrayList<Dialogue> findDialogues(RandomAccessFile raf) {
        ArrayList <Dialogue> dialogues = new ArrayList<>();
        if (raf == null) return dialogues;

        try {
            int cant = raf.readInt();// Lee la cantidad de dialogos a leer
            for (int i = 0; i < cant; i++){// Recorre el archivo en la cantidad;
                int tam = raf.readInt();
                byte[] string = new byte[tam];
                raf.readFully(string);// asegurar lectura completa
                Dialogue d = (Dialogue) Convert.toObject(string);
                if (d != null) {
                    dialogues.add(d); // Se agrega a el array list
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return dialogues;
    }

    //  Busca un personaje de la misma forma que el anterior
    public static GameCharacter findCharacter (String id, RandomAccessFile raf){
        GameCharacter returned = null;
        if (raf == null) return null;

        try{
           int cant = raf.readInt();
           for (int i = 0; i < cant; i++){
               int tam = raf.readInt();
               byte[] string = new byte[tam];
               raf.readFully(string);
               GameCharacter c = (GameCharacter) Convert.toObject(string);
               if (c != null && c.getId().equals(id)){
                   returned = c;
               }
           }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return  returned;
    }

    // Se repite el algoritmo
    public static Consecuence searchConsecuence (String id, RandomAccessFile raf){
        Consecuence c=null;
        boolean found = false;
        if (raf == null) return null;

        try {
            int cant = raf.readInt();
            for (int i = 0; i < cant && !found; i++){
                int tam = raf.readInt();
                byte[] string = new  byte[tam];
                raf.readFully(string);
                c = (Consecuence) Convert.toObject(string);
                if (c != null && c.getId().equals(id)){
                    found = true;
                }
            }
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return c;
    }
    // Se repite el algoritmo
    public static Dialogue searchDialogue (String id, RandomAccessFile raf){
        Dialogue dialogue = null;
        boolean found = false;
        if (raf == null) return null;

        try {
            int cant = raf.readInt();
            for (int i = 0; i < cant; i++){
                int tam = raf.readInt();
                byte[] string = new byte[tam];
                raf.readFully(string);
                Dialogue dial =  (Dialogue) Convert.toObject(string);
                if (dial != null && dial.getId().equals(id)){
                    dialogue = dial;
                    found = true;
                }
            }
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dialogue;
    }
    // Se crea un file
    public static File returnFile (String path){
        return new File(path);
    }
}
