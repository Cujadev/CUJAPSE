package logic.auxiliars.Ficheros;

import logic.auxiliars.files.Convert;
import logic.clases.Dialogue;
import logic.clases.GameCharacter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        if (character == null) {
            throw new IllegalArgumentException("Personaje nulo");
        }

        int numeroId = Integer.parseInt(character.getId());
        boolean ok = false;

        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {

            raf.seek(raf.length());

            raf.writeInt(numeroId);

            byte[] bytesCharacter = Convert.toBytes(character);
            raf.writeInt(bytesCharacter.length);
            raf.write(bytesCharacter);

            ok = true;
        }

        return ok;
    }


    public boolean saveDialogue(Dialogue dialogue, String direccionFile) throws IOException {

        if (dialogue == null) {
            throw new IllegalArgumentException("Dialogo nulo");
        }

        int numeroId = Integer.parseInt(dialogue.getId());
        boolean ok = false;

        Path path = Paths.get(direccionFile);

        if (path.getParent() != null && !Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        try (RandomAccessFile raf = new RandomAccessFile(direccionFile, "rw")) {

            raf.seek(raf.length());

            raf.writeInt(numeroId);

            byte[] bytesDialogue = Convert.toBytes(dialogue);
            raf.writeInt(bytesDialogue.length);
            raf.write(bytesDialogue);

            ok = true;
        }

        return ok;
    }
}
