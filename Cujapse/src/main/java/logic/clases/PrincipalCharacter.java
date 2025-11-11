package logic.clases;

import javax.swing.*;
import java.util.ArrayList;

public class PrincipalCharacter extends Character {
    private int cafeina;
    private int popularidad;
    private int dinero;
    private int estudios;
    private final int maximoSinMorir;
    private final int minimoSinMorir;

    //=====Constructor=====
    public PrincipalCharacter(String nombre, String id, ImageIcon imagen, int popularidad, int dinero, int estudios, int cafeina, ArrayList<Dialogue> dialogos, int maximoSinMorir, int minimoSinMorir) {
        super(id, nombre, imagen, dialogos);
        setPopularidad(popularidad);
        setDinero(dinero);
        setEstudios(estudios);
        setCafeina(cafeina);
        this.maximoSinMorir = maximoSinMorir;
        this.minimoSinMorir = minimoSinMorir;
    }

    //====Guetters y Setters====

    public int getCafeina() {
        return cafeina;
    }

    public void setCafeina(int cafeina) {
        this.cafeina = cafeina;
    }

    public int getPopularidad() {
        return popularidad;
    }

    public void setPopularidad(int popularidad) {
        this.popularidad = popularidad;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public int getEstudios() {
        return estudios;
    }

    public void setEstudios(int estudios) {
        this.estudios = estudios;
    }

    //========Metodos========

    //=== Asumo que cuando afecte se le pase un numero negativo y asi se evita hacer dos metodos de cada
    public int variarCafeina(int valor) {
        this.cafeina += valor;
        return this.cafeina;
    }

    public int variarPopularidad(int valor) {
        this.popularidad += valor;
        return this.popularidad;
    }

    public int variarDinero(int valor) {
        this.dinero += valor;
        return this.dinero;
    }

    public int variarEstudios(int valor) {
        this.estudios += valor;
        return this.estudios;
    }

    public boolean murio() {
        boolean murio = false;
        if (cafeina >= maximoSinMorir || dinero >= maximoSinMorir || estudios >= maximoSinMorir || popularidad >= maximoSinMorir) {
            murio = true;
        }

        if (cafeina <= minimoSinMorir || dinero <= minimoSinMorir || estudios <= minimoSinMorir || popularidad <= minimoSinMorir) {
            murio = true;
        }
        return murio;
    }



}
