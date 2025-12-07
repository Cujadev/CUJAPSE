package logic.auxiliars.tree;

<<<<<<< HEAD
import logic.clases.event.Situation;

=======
/// Es la clase que representa un nodo del arbol
>>>>>>> 0cca16d2fec70aadf4769c45c401c3dd5fcdaa44
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
