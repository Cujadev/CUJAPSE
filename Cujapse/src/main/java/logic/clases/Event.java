package logic.clases;

import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;
import logic.auxiliars.tree.Iterator.TreeIterator;

public class Event {
    private DecisionTree<Situation> situations;
    private String idCharacter;

    public Event(String idCharacter) {
        situations = new DecisionTree<>();
        this.idCharacter = idCharacter;
    }
    public Event (Situation root, String idCharacter){
        situations = new DecisionTree<>(root);
        this.idCharacter = idCharacter;
    }

    public DecisionTree<Situation> getSituations() {
        return situations;
    }

    public void setSituations(DecisionTree<Situation> situations) {
        this.situations = situations;
    }

    public String getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(String idCharacter) {
        this.idCharacter = idCharacter;
    }
    public Situation getNextSituaion(int branch){
        Situation s;
        TreeIterator <Situation> it = situations.TreeIterator();

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
            situations.AddNode(newNode, father,branch);
    }
}
