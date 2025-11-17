package logic.clases;

import javafx.scene.image.Image;

import logic.auxiliars.chargers.ChargerMenssage;
import logic.auxiliars.files.Convert;
import logic.auxiliars.files.FileReaders;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

public class GameCharacter implements ChargerMenssage, Serializable {

    protected String id;
    protected String name;
    protected transient Image image;
    protected transient File dialogues;
    protected String imagePath;
    protected String dialoguesPath;
    private static final long serialVersionUID = 1L;

    public GameCharacter(String id, String name, String imagePath, String dialoguesPath) {
        setId(id);
        setName(name);
        this.imagePath = imagePath;
        this.dialoguesPath = dialoguesPath;
        try {
            loadResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public File getDialogues() {
        return dialogues;
    }

    public void setDialogues(File dialogues) {
        this.dialogues = dialogues;
    }


    @Override
    public Dialogue ChargeDialogue(String idString) {
        Dialogue result = null;

        RandomAccessFile raf = FileReaders.openFile(dialogues);
        ArrayList<Dialogue> allDialogues = FileReaders.chargeDialogues(raf);
        FileReaders.closeFile(raf);

        ListIterator<Dialogue> it = allDialogues.listIterator();
        boolean found = false;

        while (it.hasNext() && !found) {
            Dialogue d = it.next();
            if (d.getId().equals(idString)) {
                result = d;
                found = true;
            }
        }

        return result;
    }

    public void loadResources() throws IOException {
        if (imagePath != null) {
            this.image = new Image("file:" + imagePath);
        }
        if (dialoguesPath != null) {
            this.dialogues = new File(dialoguesPath);
            if (!dialogues.exists()) {
                dialogues.createNewFile();
            }
        }
    }


    public Dialogue findDialogue(int idDialogue) throws IOException, ClassNotFoundException {
        RandomAccessFile raf = new RandomAccessFile(dialogues, "r");

        boolean found = false;
        Dialogue dialogue = null;

        while (raf.getFilePointer() < raf.length() && !found) {

            int storedId = raf.readInt();

            if (storedId == idDialogue) {
                found = true;

                int size = raf.readInt();
                byte[] bytes = new byte[size];
                raf.readFully(bytes);

                dialogue = (Dialogue) Convert.toObject(bytes);
            } else {
                int size = raf.readInt();
                raf.skipBytes(size);
            }
        }

        raf.close();
        return dialogue;
    }

    public boolean saveDialogue(Dialogue dialogue) throws IOException {

        boolean ok = false;
        RandomAccessFile raf = new RandomAccessFile(dialogues, "rw");

        if (dialogue != null) {

            int idAsInt = Integer.parseInt(dialogue.getId());

            raf.seek(raf.length());
            raf.writeInt(idAsInt);

            byte[] bytes = Convert.toBytes(dialogue);
            raf.writeInt(bytes.length);
            raf.write(bytes);

            ok = true;
        }

        raf.close();
        return ok;
    }
}
