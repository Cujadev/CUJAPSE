package logic.auxiliars.tree;

import logic.auxiliars.tree.Iterator.TreeIterator;

//Crear los trabajos con Iteradores
public class DecisionTree<E>{
    private DecisionNode<E> root;
    private TreeIterator <E> iterator;

    public DecisionTree (){
        root = null;
        iterator = null;
    }
    public DecisionTree(E root) {
        this.root = new DecisionNode<>(root);
        iterator = new TreeIterator<>(this.root);
    }

    public DecisionNode<E> getRoot() {
        return root;
    }

    public void setRoot(DecisionNode<E> root) {
        this.root = root;
    }

    //Se agrega un nodo aprovechandose de las llamadas por referencia, simulando la lincked list
    public boolean AddNode (E info, DecisionNode <E> father, int branch)throws IllegalArgumentException{

        if (root == null){
            setRoot(new DecisionNode<>(info));
            return true;
        }
        else{
            if (branch == 1){
                if (father.getLeft() == null){
                    DecisionNode <E> newNode = new DecisionNode<>(info);
                    father.setLeft(newNode);
                }
                else{
                    throw new IllegalArgumentException("Error, rama ya ocupada");
                }
            }
            else if (branch == 2){
                if (father.getRight() == null){
                    father.setLeft(new DecisionNode<>(info));
                }
                else {
                    throw new IllegalArgumentException("Error, rama ocupada");
                }
            }
            else {
                throw new IllegalArgumentException("Error, solo se pueden tener 2 ramas");
            }
        }
        return false;
    }
    public TreeIterator <E> TreeIterator() throws NullPointerException{
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
        return it;
    }

}
