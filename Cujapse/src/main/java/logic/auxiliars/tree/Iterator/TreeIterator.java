package logic.auxiliars.tree.Iterator;

import logic.auxiliars.tree.DecisionNode;

public class TreeIterator<E> {
    private DecisionNode<E> next;
    private DecisionNode <E> father;

    public TreeIterator (DecisionNode <E> root) throws NullPointerException{
       if (root != null){
           father = root;
           next = null;
       }
       else {
           throw new NullPointerException("No existe nada para recorrer el árbol");
       }
    }

    public boolean hasNext(){
        if (next == null &&  (father.getRight() != null || father.getLeft() != null) ){
            return true;
        }
        if (next != null && (next.getLeft() != null && next.getRight() != null)){
            return true;
        }
        return false;

    }

    public DecisionNode <E> next(int branch){
        if (next == null){
            return next = father;
        }
        else {
            if (branch == 1 && father.getLeft() != null){
                father = next;
                next = (DecisionNode<E>) father.getLeft();
            }
            else if  (branch == 2 && father.getRight() != null){
                father = next;
                next = (DecisionNode<E>) father.getRight();
            }
            else {
                throw new IllegalArgumentException("Revise la rama que está pidiendo");
            }
        }
        return next;
    }

}
