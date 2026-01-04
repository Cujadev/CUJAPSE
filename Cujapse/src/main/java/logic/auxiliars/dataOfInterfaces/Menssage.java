package logic.auxiliars.dataOfInterfaces;

import java.util.HashMap;

public class Menssage {
    private String text;
    private String nameAutor;
    private String avatarAutor;

    public Menssage(String text, String nameAutor, String avatarAutor) {
        this.text = text;
        this.nameAutor = nameAutor;
        this.avatarAutor = avatarAutor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNameAutor() {
        return nameAutor;
    }

    public void setNameAutor(String nameAutor) {
        this.nameAutor = nameAutor;
    }

    public String getAvatarAutor() {
        return avatarAutor;
    }

    public void setAvatarAutor(String avatarAutor) {
        this.avatarAutor = avatarAutor;
    }
}
