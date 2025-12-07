package logic.auxiliars.tree;

import logic.clases.event.Situation;

public class DecisionNode<E>{
    private E info;
    private DecisionNode <E> left;
    private DecisionNode <E> right;

    public DecisionNode(E info) {
        this.info = info;
        this.left = null;
        this.right = null;
    }

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public DecisionNode<E> getLeft() {
        return left;
    }

    public void setLeft(DecisionNode<Situation> left) {
        this.left = (DecisionNode<E>) left;
    }

    public DecisionNode<E> getRight() {
        return right;
    }

    public void setRight(DecisionNode<Situation> right) {
        this.right = (DecisionNode<E>) right;
    }
}
