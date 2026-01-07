import logic.auxiliars.files.FileReaders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;

public class test {
    private String path;

    @BeforeEach
    public void setup() {
        path = "/data/main_character/consecuencias.dat";
    }

    @Test
    public void consecuenceIntegritiTest() throws IOException {
        RandomAccessFile raf = FileReaders.openFile(FileReaders.returnFile(path));
        int cant = raf.readInt();
        System.out.println("cant = " + cant);
        raf.close();
    }
    @AfterEach
    public void teardown() {}
}
