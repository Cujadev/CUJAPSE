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

    /*public static void main (String[] args) {
        File file = new File ("src/main/resources/consecuencias.dat");
        Integer[] list1 = new Integer[]{0, 0, 0, 0};
        Integer[] list2 = new Integer[]{0, 0, 0, 0};
        Consecuence c1 = new Consecuence("1", list1, list2);
        saveConsecuence(c1, file);

        Integer[] list3 = new Integer[]{0, 4, 3, 0};
        Integer[] list4 = new Integer[]{0, 3, 1, 0};
        Consecuence c2 = new Consecuence("2", list3, list4);
        saveConsecuence(c2, file);

        Integer[] list5 = new Integer[]{0, 4, 1, 0};
        Integer[] list6 = new Integer[]{0, 1, 0, 0};
        Consecuence c3 = new Consecuence("3", list5, list6);
        saveConsecuence(c3, file);

        Integer[] list7 = new Integer[]{2, 4, 0, 0};
        Integer[] list8 = new Integer[]{4, 1, 0, 0};
        Consecuence c4 = new Consecuence("4", list7, list8);
        saveConsecuence(c4, file);

        Integer[] list9 = new Integer[]{0, 2, 1, 0};
        Integer[] list10 = new Integer[]{0, 1, 0, 0};
        Consecuence c5 = new Consecuence("5", list9, list10);
        saveConsecuence(c5, file);

        Integer[] list11 = new Integer[]{0, 4, 0, 0};
        Integer[] list12 = new Integer[]{0, 1, 1, 0};
        Consecuence c6 = new Consecuence("6", list11, list12);
        saveConsecuence(c6, file);

        Integer[] list13 = new Integer[]{0, 1, 1, 0};
        Integer[] list14 = new Integer[]{0, 1, 0, 0};
        Consecuence c7 = new Consecuence("7", list13, list14);
        saveConsecuence(c7, file);

        Integer[] list15 = new Integer[]{0, 4, 0, 0};
        Integer[] list16 = new Integer[]{0, 1, 0, 0};
        Consecuence c8 = new Consecuence("8", list15, list16);
        saveConsecuence(c8, file);

        Integer[] list17 = new Integer[]{0, 3, 3, 0};
        Integer[] list18 = new Integer[]{0, 1, 0, 0};
        Consecuence c9 = new Consecuence("9", list17, list18);
        saveConsecuence(c9, file);

        Integer[] list19 = new Integer[]{0, 1, 1, 0};
        Integer[] list20 = new Integer[]{1, 0, 0, 0};
        Consecuence c10 = new Consecuence("10", list19, list20);
        saveConsecuence(c10, file);

        Integer[] list21 = new Integer[]{2, 1, 0, 0};
        Integer[] list22 = new Integer[]{1, 4, 0, 0};
        Consecuence c11 = new Consecuence("11", list21, list22);
        saveConsecuence(c11, file);

        Integer[] list23 = new Integer[]{1, 0, 3, 0};
        Integer[] list24 = new Integer[]{1, 0, 0, 0};
        Consecuence c12 = new Consecuence("12", list23, list24);
        saveConsecuence(c12, file);

        Integer[] list25 = new Integer[]{1, 0, 0, 0};
        Integer[] list26 = new Integer[]{0, 4, 0, 0};
        Consecuence c13 = new Consecuence("13", list25, list26);
        saveConsecuence(c13, file);

        Integer[] list27 = new Integer[]{3, 1, 4, 1};
        Integer[] list28 = new Integer[]{1, 4, 0, 0};
        Consecuence c14 = new Consecuence("14", list27, list28);
        saveConsecuence(c14, file);

        Integer[] list29 = new Integer[]{3, 0, 4, 0};
        Integer[] list30 = new Integer[]{0, 0, 1, 0};
        Consecuence c15 = new Consecuence("15", list29, list30);
        saveConsecuence(c15, file);

        Integer[] list31 = new Integer[]{1, 0, 1, 0};
        Integer[] list32 = new Integer[]{0, 4, 0, 0};
        Consecuence c16 = new Consecuence("16", list31, list32);
        saveConsecuence(c16, file);

    }*/
}
