package logic.auxiliars.tree;

import logic.auxiliars.tree.Iterator.TreeIterator;

/// Esta es la clase que simula un árbol de decisión
public class DecisionTree<E> {

    private DecisionNode<E> root;
    private TreeIterator<E> iterator;

    // ==== Constructores ====
    public DecisionTree() {
        this.root = null;
        this.iterator = null;
    }

    public DecisionTree(E root) {
        this.root = new DecisionNode<>(root);
        this.iterator = new TreeIterator<>(this.root);
    }

    // ==== Getters y Setters ====
    public DecisionNode<E> getRoot() {
        return root;
    }

    public void setRoot(DecisionNode<E> root) {
        this.root = root;
    }

    /**
     * Agrega un nodo al árbol.
     * @param info   nodo nuevo
     * @param father nodo padre
     * @param branch 1 = izquierda, 2 = derecha
     */
    public void addNode(DecisionNode<E> info, DecisionNode<E> father, int branch)
            throws IllegalArgumentException {

        // Si el árbol está vacío, el nuevo nodo es la raíz
        if (root == null) {
            setRoot(info);
            this.iterator = new TreeIterator<>(this.root);
            return;
        }

        // Insertar en la izquierda
        if (branch == 1) {
            if (father.getLeft() == null) {
                father.setLeft(info);
            } else {
                throw new IllegalArgumentException("Error: rama izquierda ya ocupada");
            }
        }
        // Insertar en la derecha
        else if (branch == 2) {
            if (father.getRight() == null) {
                father.setRight(info);
            } else {
                throw new IllegalArgumentException("Error: rama derecha ya ocupada");
            }
        }
        // Rama inválida
        else {
            throw new IllegalArgumentException("Error: solo se permiten ramas 1 (izq) o 2 (der)");
        }
    }

    /**
     * Obtiene el iterador del árbol.
     */
    public TreeIterator<E> treeIterator() throws NullPointerException {
        if (this.iterator != null) {
            return this.iterator;
        }

        if (this.root != null) {
            return new TreeIterator<>(this.root);
        }

        throw new NullPointerException("No existen datos en este árbol");
    }
}
