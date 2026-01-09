package logic.clases.event;

import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;
import logic.auxiliars.tree.Iterator.TreeIterator;

public class Event {
    private String event;
    private DecisionTree<Situation> situations;
    private TreeIterator<Situation> it;
    private String imagePath;

    /// ===Constructores
    public Event(String id, String imagePath,Situation root) {
        situations = new DecisionTree<>(root);
        it = situations.treeIterator();
        this.event = id;
        this.imagePath = imagePath;

    }

    public Event (String id,Situation root){
        situations = new DecisionTree<>(root);
        it = situations.treeIterator();
        event = id;
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

    public String getIdEvent() {
        return event;
    }

    public void setIdEvent(String event) {
        this.event = event;
    }
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
    public DecisionNode <Situation> getActualSituation(){
        return it.getNode();
    }
    public void resetEvent (){
        it = situations.resetIterator();
    }

}
