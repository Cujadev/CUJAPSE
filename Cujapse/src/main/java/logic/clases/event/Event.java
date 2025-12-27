package logic.clases.event;

import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;
import logic.auxiliars.tree.Iterator.TreeIterator;

public class Event {
    private DecisionTree<Situation> situations;
    private TreeIterator<Situation> it;
    private String imagePath;

    /// ===Constructores
    public Event(String imagePath) {
        situations = new DecisionTree<>();
        it = situations.treeIterator();

    }
    public Event (Situation root){
        situations = new DecisionTree<>(root);
        it = situations.treeIterator();
    }
    ///+++++++++++++++++++++++++++++++++++++++++++++++++++++
    ///
    ///
    /// ===Getters y Setters===
    public DecisionTree<Situation> getSituations() {
        return situations;
    }

    public void setSituations(DecisionTree<Situation> situations) {
        this.situations = situations;
    }

    public String getImagePath() {return imagePath;}

    public void setImagePath(String imagePath) {this.imagePath = imagePath;}
    ///+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    ///
    ///
    /// ===Métodos de la clase

    ///Recorre el árbol de situaciones dentro de la clase
    ///Se debe pasar como parámetro 1,2,0
    /// 1 para izquierda
    /// 2 para derecha
    /// 0 nodo actual
    public Situation getNextSituaion(int branch){
        Situation s;

        if (branch == 1){
            it.choose(1);
            s = it.getNodeInfo();
        }
        else if (branch == 2){
            it.choose(2);
            s = it.getNodeInfo();
        }
        else if (branch == 0){
            s = it.getNodeInfo();
        }
        else {
            throw new IllegalArgumentException("Opción para selecionar la situación es incorrecta");
        }
        return s;
    }

    public void addSituation (DecisionNode<Situation> newNode, DecisionNode<Situation> father, int branch){
            situations.addNode(newNode, father,branch);
    }
}
