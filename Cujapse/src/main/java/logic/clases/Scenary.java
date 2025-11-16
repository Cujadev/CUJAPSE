package logic.clases;

import java.awt.Image;

public class Scenary {
    private Image fondo;
    private Event evento;

    // Pedir al evento dado una selección una situación
    // Poder dar al MVC la imagen a cargar
    // Poder pedir al juego un nuevo evento

    public Scenary(Image fondo, Event evento) {
        this.fondo = fondo;
        this.evento = evento;
    }

    public Image getFondo() {
        return fondo;
    }

    public void setFondo(Image fondo) {
        this.fondo = fondo;
    }

    public Event getEvento() {
        return evento;
    }

    public void setEvento(Event evento) {

    }

    public
}
