package interfaz.auxiliars;

public class Mensaje {
    public String autor;
    public String texto;
    public String avatarPath;
    public final String imagePath; // ← NUEVO

    // Mensaje solo texto
    public Mensaje(String autor, String texto, String avatarPath) {
        this.autor = autor;
        this.texto = texto;
        this.avatarPath = avatarPath;
        this.imagePath = null;
    }

    // Mensaje con imagen
    public Mensaje(String autor, String texto, String avatarPath, String imagePath) {
        this.autor = autor;
        this.texto = texto;
        this.avatarPath = avatarPath;
        this.imagePath = imagePath;
    }
}
