package logic.auxiliars.tree.Iterator;

import logic.auxiliars.tree.DecisionNode;

public class TreeIterator<E> {
    private DecisionNode<E> actual;

    public TreeIterator(DecisionNode<E> root) throws NullPointerException{
        if (root == null) {
            throw new NullPointerException("No existe nada para recorrer el árbol");
        }
        this.actual = root;
    }

    /** Devuelve el nodo actual del iterador. */
    public DecisionNode<E> getNode() {
        return actual;
    }

    /** Devuelve la información del nodo actual. */
    public E getNodeInfo() {
        return actual.getInfo();
    }

    /**
     * Mueve el iterador a la rama indicada:
     * 1 = izquierda, 2 = derecha.
     * Devuelve el nuevo nodo actual.
     */
    public DecisionNode<E> choose(int branch) throws IllegalArgumentException{
        if (branch == 1) {
            if (actual.getLeft() == null) {
                throw new IllegalArgumentException("No existe rama izquierda desde este nodo");
            }
            actual = actual.getLeft();
        } else if (branch == 2) {
            if (actual.getRight() == null) {
                throw new IllegalArgumentException("No existe rama derecha desde este nodo");
            }
            actual = actual.getRight();
        } else {
            throw new IllegalArgumentException("Solo se permite 1 (izquierda) o 2 (derecha)");
        }
        return actual;
    }
}
