package logic.auxiliars.tree.Iterator;

import logic.auxiliars.tree.DecisionNode;
/// Esta clase itera por el árbol que se ha creado para poder alternar entre las situaciones
public class TreeIterator<E> {
    private DecisionNode<E> current;

    public TreeIterator(DecisionNode<E> root) throws NullPointerException{
        if (root == null) {
            throw new NullPointerException("No existe nada para recorrer el árbol");
        }
        this.current = root;
    }

    /** Devuelve el nodo actual del iterador. */
    public DecisionNode<E> getNode() {
        return current;
    }

    /** Devuelve la información del nodo actual. */
    public E getNodeInfo() {
        return current.getInfo();
    }

    /**
     * Mueve el iterador a la rama indicada:
     * 1 = izquierda, 2 = derecha.
     * Devuelve el nuevo nodo actual.
     */

    public DecisionNode<E> choose(int branch) throws IllegalArgumentException{
        if (branch == 1) {
            if (current.getLeft() == null) {
                throw new IllegalArgumentException("No existe rama izquierda desde este nodo");
            }
            current = current.getLeft();
        } else if (branch == 2) {
            if (current.getRight() == null) {
                throw new IllegalArgumentException("No existe rama derecha desde este nodo");
            }
            current = current.getRight();
        } else {
            throw new IllegalArgumentException("Solo se permite 1 (izquierda) o 2 (derecha)");
        }
        return current;
    }
}
