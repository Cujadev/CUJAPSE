package logic.auxiliars.tree;

import logic.auxiliars.tree.Iterator.TreeIterator;

/// Esta es la clase que simula un árbol de decisión
public class DecisionTree<E> {

    private DecisionNode<E> root;
    private final TreeIterator<E> iterator;

    // ==== Constructores ====
    public DecisionTree() {
        root = null;
        iterator = null;
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
    public void AddNode(DecisionNode<E> info, DecisionNode<E> father, int branch)
            throws IllegalArgumentException {

        if (root == null) {
            // Si el árbol está vacío, el nuevo nodo se convierte en la raíz
            setRoot(info);
            return;
        }

        if (branch == 1) { // izquierda
            if (father.getLeft() == null) {
                father.setLeft(info);
            } else {
                throw new IllegalArgumentException("Error: rama izquierda ya ocupada");
            }
        }
        else if (branch == 2) { // derecha
            if (father.getRight() == null) {
                father.setRight(info);
            } else {
                throw new IllegalArgumentException("Error: rama derecha ya ocupada");
            }
        }
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
