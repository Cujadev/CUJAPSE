package logic.auxiliars.tree;
import logic.clases.event.Situation;
/// Es la clase que representa un nodo del arbol
public class DecisionNode<E>{
    private E info;
    private DecisionNode <E> left;
    private DecisionNode <E> right;

    ///  ==== Constructor ====
    public DecisionNode(E info) {
        this.info = info;
        this.left = null;
        this.right = null;
    }

    /// ==== Getters and Setters ====
    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public DecisionNode<E> getLeft() {
        return left;
    }

    public void setLeft(DecisionNode<E> left) {
        this.left = left;
    }

    public DecisionNode<E> getRight() {
        return right;
    }

    public void setRight(DecisionNode<Situation> right) {
        this.right = (DecisionNode<E>) right;
    }

    public void setLeft(DecisionNode<E> info) {

    }
}
