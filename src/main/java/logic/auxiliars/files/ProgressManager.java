package logic.auxiliars.files;

import logic.clases.character.PrincipalCharacter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ProgressManager {
    private static final String SAVE_PATH = "data/SavedPlays/Save.dat";// para poder poner la dirección que sea más fácil :)

    public static void savePlay(PrincipalCharacter character) throws IOException {
        File fichero = new File(SAVE_PATH);

        File carpeta = fichero.getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }

        RandomAccessFile raf = new RandomAccessFile(fichero,"rw");
        byte [] principalCharacterEnBytes = Convert.toBytes(character);
        raf.writeInt(principalCharacterEnBytes.length);
        raf.write(principalCharacterEnBytes);
        raf.close();
    }

    public static PrincipalCharacter ChargeCharacter() throws IOException, ClassNotFoundException {
        File fichero = new File(SAVE_PATH);
        PrincipalCharacter salida = null;

        if(fichero.exists()){
            RandomAccessFile raf = new RandomAccessFile(fichero,"r");
            int tamanio = raf.readInt();
            byte [] bytesDelPersonaje = new byte[tamanio];
            raf.readFully(bytesDelPersonaje);
            salida = (PrincipalCharacter) Convert.toObject(bytesDelPersonaje);
            raf.close();
        }
        System.out.println("Eventos detectados" + salida.getIdEvents());
        return salida;
    }

}
