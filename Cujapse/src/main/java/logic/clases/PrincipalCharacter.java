package logic.clases;

import javax.swing.*;
import java.util.ArrayList;

public class PrincipalCharacter extends Character {
    private int caffeine;
    private int popularity;
    private int money;
    private int study;
    private ArrayList <Answer> answers;

    /// Constructor Getters y Setters ///
    public PrincipalCharacter(String id, String nombre, ImageIcon imagen, ArrayList<Dialogue> dialogos) {
        super(id, nombre, imagen, dialogos);
        setCaffeine(50);
        setPopularity(50);
        setMoney(50);
        setStudy(50);
    }

    public int getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(int caffeine) {
        this.caffeine = caffeine;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }

    public ArrayList<Answer> ChargeResponses (){

    }
}
