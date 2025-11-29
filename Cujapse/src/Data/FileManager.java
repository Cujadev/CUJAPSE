package Data;

import logic.auxiliars.files.FileReaders;
import logic.auxiliars.files.FileWriters;
import logic.clases.character.Consecuence;
import logic.clases.character.Dialogue;
import logic.clases.character.GameCharacter;
import logic.clases.character.PrincipalCharacter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;


public class FileManager {

    // Rutas fijas dentro de la carpeta src/Data (puedes cambiarlas si lo deseas)
    public static final File CHARACTERS_FILE = new File("src/Data/personajes.dat");
    public static final File PRINCIPAL_DIALOGUES_FILE = new File("src/Data/principal_dialogues.dat");
    public static final File CONSEQUENCES_FILE = new File("src/Data/consecuencias.dat");

    // --- Utilidades básicas ---

    /**
     * Asegura que el fichero y su directorio padre existan. Si el fichero no existe
     * se crea y se inicializa escribiendo un entero 0 al inicio (contador de elementos).
     */
    public static void ensureFileInitialized(File file) throws IOException {
        Objects.requireNonNull(file);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                // Si no pudo crear, seguirá intentando abrir/crear el fichero; Java lanzará excepción si falla
                System.err.println("Warning: no se pudo crear directorio padre: " + parent.getPath());
            }
        }
        if (!file.exists() || file.length() == 0) {
            try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                // escribe 0 como contador inicial
                raf.seek(0);
                raf.writeInt(0);
            }
        }
    }


    public static File defaultDialoguesFileForCharacter(String charId) {
        return new File("src/Data/dialogues_" + charId + ".dat");
    }

    // --- Operaciones de alto nivel ---


    public static void createSecondaryCharacter(String charId, String name, String imagePath, String dialoguesFile) throws IOException {
        File df;
        if (dialoguesFile == null || dialoguesFile.trim().isEmpty()) {
            df = defaultDialoguesFileForCharacter(charId);
        } else {
            df = new File(dialoguesFile);
        }
        // inicializar ficheros
        ensureFileInitialized(CHARACTERS_FILE);
        ensureFileInitialized(df);

        GameCharacter gc = new GameCharacter(charId, name, imagePath, df.getPath());
        FileWriters.saveCharacter(gc, CHARACTERS_FILE);
    }

    /**
     * Añade un diálogo para un personaje (ruta al fichero de diálogos dada).
     */
    public static void addSecondaryDialogue(String charId, String id, String contenido) throws IOException {
        File df = defaultDialoguesFileForCharacter(charId);
        ensureFileInitialized(df);

        Dialogue d = new Dialogue(id, contenido);
        FileWriters.saveDialogue(d, df);
    }


    /**
     * Añade un par de respuestas para el personaje principal (dos diálogos consecutivos con el mismo id)
     */
    public static void addPrincipalAnswerPair(String numericId, String leftContenido, String rightContenido) throws IOException {
        ensureFileInitialized(PRINCIPAL_DIALOGUES_FILE);
        Dialogue d1 = new Dialogue(numericId, leftContenido);
        Dialogue d2 = new Dialogue(numericId, rightContenido);
        FileWriters.saveDialogue(d1, PRINCIPAL_DIALOGUES_FILE);
        FileWriters.saveDialogue(d2, PRINCIPAL_DIALOGUES_FILE);
    }

    /**
     * Añade una consecuencia asociada a un id (usa el fichero de consecuencias en src/Data)
     */
    public static void addConsequence(String numericId, Integer[] leftConsequences, Integer[] rightConsequences) throws IOException {
        ensureFileInitialized(CONSEQUENCES_FILE);
        Consecuence c = new Consecuence(numericId, rightConsequences, leftConsequences);
        FileWriters.saveConsecuence(c, CONSEQUENCES_FILE);
    }

    /**
     * Crea un personaje secundario con diálogo, añade par de respuestas al principal y consecuencia.
     */
    public static void createSecondaryWithAll(String charId, String name, String imagePath, String dialogueId, String dialogueContent,
                                              String principalLeft, String principalRight, Integer[] leftConsequences, Integer[] rightConsequences) throws IOException {
        // ruta de diálogos del secundario
        File df = defaultDialoguesFileForCharacter(charId);
        // inicializar ficheros principales
        ensureFileInitialized(CHARACTERS_FILE);
        ensureFileInitialized(df);
        ensureFileInitialized(PRINCIPAL_DIALOGUES_FILE);
        ensureFileInitialized(CONSEQUENCES_FILE);

        // crear y guardar secundario
        GameCharacter gc = new GameCharacter(charId, name, imagePath, df.getPath());
        FileWriters.saveCharacter(gc, CHARACTERS_FILE);

        // guardar diálogo del secundario
        Dialogue sd = new Dialogue(dialogueId, dialogueContent);
        FileWriters.saveDialogue(sd, df);

        // guardar par del principal
        Dialogue pLeft = new Dialogue(dialogueId, principalLeft);
        Dialogue pRight = new Dialogue(dialogueId, principalRight);
        FileWriters.saveDialogue(pLeft, PRINCIPAL_DIALOGUES_FILE);
        FileWriters.saveDialogue(pRight, PRINCIPAL_DIALOGUES_FILE);

        // guardar consecuencia
        Consecuence c = new Consecuence(dialogueId, rightConsequences, leftConsequences);
        FileWriters.saveConsecuence(c, CONSEQUENCES_FILE);
    }

    /**
     * Borra todos los ficheros .dat dentro de src/Data (ficheros de prueba) y vuelve a crear
     * los ficheros esenciales vacíos (personajes, principal_dialogues, consecuencias) inicializados.
     * Usar esta función si quieres limpiar los datos de prueba antes de meter datos 'de verdad'.
     */
    public static void resetDataFiles() throws IOException {
        File dataDir = new File("src/Data");
        if (dataDir.exists() && dataDir.isDirectory()) {
            File[] files = dataDir.listFiles((dir, name) -> name.endsWith(".dat"));
            if (files != null) {
                for (File f : files) {
                    try {
                        if (!f.delete()) {
                            System.err.println("No se pudo borrar el fichero: " + f.getPath());
                        }
                    } catch (SecurityException se) {
                        System.err.println("No permitido borrar: " + f.getPath());
                    }
                }
            }
        } else {
            // crear carpeta src/Data si no existe
            if (!dataDir.mkdirs()) {
                System.err.println("Aviso: no se pudo crear carpeta src/Data");
            }
        }
        // recrear e inicializar los ficheros esenciales vacíos
        ensureFileInitialized(CHARACTERS_FILE);
        ensureFileInitialized(PRINCIPAL_DIALOGUES_FILE);
        ensureFileInitialized(CONSEQUENCES_FILE);
    }
    //usar este cuando quiaran meter al prota
    public static void meterPrincipal() throws IOException {
        ensureFileInitialized(CHARACTERS_FILE);
        ensureFileInitialized(PRINCIPAL_DIALOGUES_FILE);
        ensureFileInitialized(CONSEQUENCES_FILE);

        PrincipalCharacter gc = new PrincipalCharacter("PC001", "Protagonista", "src/Images/protagonista.png", PRINCIPAL_DIALOGUES_FILE.getPath(),CONSEQUENCES_FILE.getPath());
        FileWriters.saveCharacter(gc, CHARACTERS_FILE);
    }

}
