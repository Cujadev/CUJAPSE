package logic.auxiliars.tree;

import logic.auxiliars.tree.Iterator.TreeIterator;
import logic.clases.event.Situation;

/// Esta es la clase que simula un arbol de desicion
public class DecisionTree<E>{
    private DecisionNode<E> root;
    private TreeIterator <E> iterator;

    /// ==== Constructor ====
    public DecisionTree (){
        root = null;
        iterator = null;
    }
    public DecisionTree(E root) {
        this.root = new DecisionNode<>(root);
        iterator = new TreeIterator<>(this.root);
    }

    /// ==== Getters y Setters ====
    public DecisionNode<E> getRoot() {
        return  root;
    }

    public void setRoot(DecisionNode<E> root) {
        this.root = root;
    }

    //Se agrega un nodo aprovechandose de las llamadas por referencia, simulando la lincked list


    public void addNode(DecisionNode <E> info, DecisionNode <E> father, int branch)throws IllegalArgumentException{

        if (root == null){ // Si se cumple se agrega en  la raíz
            setRoot(info);
            this.iterator = new TreeIterator<>(this.root);
        }
        else{
            if (branch == 1){// Si se cumple se agrega en la izquierda
                if (father.getLeft() == null){
                    father.setLeft(info);
                }
                else{
                    throw new IllegalArgumentException("Error, rama ya ocupada");
                }
            }
            else if (branch == 2){// Si se cumple se agrega en la derecha
                if (father.getRight() == null){
                    father.setRight(info);
                }
                else {
                    throw new IllegalArgumentException("Error, rama ocupada");
                }
            }
            else {
                throw new IllegalArgumentException("Error, solo se pueden tener 2 ramas");
            }
        }
    }
    // Obtiene el iterador
    public TreeIterator <E> treeIterator() throws NullPointerException{
        TreeIterator <E> it = null;
        if (this.iterator != null){
            return this.iterator;
        }
        else {
            if (this.root != null){
                it = new TreeIterator<>(this.root);
            }
            else{
                throw new NullPointerException("No existen datos en este arbol");
            }
        }
        return iterator =  it;
    }
    public TreeIterator<E> resetIterator(){
        if (this.root != null){
            iterator = new TreeIterator<>(this.root);
        }
        else{
            throw new NullPointerException("No existen datos en este arbol");
        }
        return iterator;
    }

}
