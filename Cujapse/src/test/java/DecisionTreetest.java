import logic.auxiliars.tree.DecisionNode;
import logic.auxiliars.tree.DecisionTree;
import logic.auxiliars.tree.Iterator.TreeIterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DecisionTreetest{
    DecisionNode <String> root;
    DecisionNode <String> left;
    DecisionNode <String> rigth;
    DecisionNode <String> children;
    DecisionTree <String> tree;
    DecisionTree <String> tree2;
    @BeforeEach
    void setUp(){
        root = new DecisionNode<>("Raiz");
        left = new DecisionNode<>("izquierda");
        rigth = new DecisionNode<>("Derecha");
        children = new DecisionNode<>("hijo");
        tree = new DecisionTree<>();
        tree2 = new DecisionTree<String>("Raiz");
        tree2.getRoot().setRight(rigth);
        tree2.getRoot().setLeft(left);
    }
    /// Nota para mí: Se deben tener en cuenta todos los posibles casos que ve tu código, de momento se ha dejado de
    /// lado la información repetida para que la validacion del arbol sea más sencilla
    @Test
     void addRooTest () {
        tree.addNode(root, null, 1);
        assertEquals("Raiz", tree.getRoot().getInfo());
    }

    @Test
    void addLefTest () {
        tree.addNode(root,null,1);
        tree.addNode(left, tree.getRoot(), 1);
        assertEquals("izquierda", tree.getRoot().getLeft().getInfo());
    }
    @Test
    void addRigthTest (){
        tree.addNode(root,null,1);
        tree.addNode(rigth, tree.getRoot(), 2);
        assertEquals("Derecha", tree.getRoot().getRight().getInfo());
    }

    @Test
    void addInUsedBranchTest () {
        tree.addNode(root, null, 1);
        tree.addNode(left, tree.getRoot(), 1);
        String info = "Error";
        DecisionNode <String> err = new DecisionNode<>(info);
        assertThrows(IllegalArgumentException.class,() -> tree.addNode(err,tree.getRoot(), 1));
    }

    @Test
    void addInIllegalBranch (){
        tree.addNode(root, null, 1);
        DecisionNode <String> err = new DecisionNode<>("Error0");
        assertThrows(IllegalArgumentException.class, () -> tree.addNode(err,tree.getRoot(), 3));
    }
    @Test
    void addInFlowTest (){
        tree.addNode(root, null, 1);
        DecisionNode <String> neW = new DecisionNode<>("Otra derecha");
        tree.addNode(left, tree.getRoot(), 1);
        tree.addNode(neW, left, 1);
        assertEquals("Otra derecha", tree.getRoot().getLeft().getLeft().getInfo());
    }

    @Test
    void moveInTreeTestToRigth (){
        TreeIterator <String> it = tree2.treeIterator();
        assertEquals(rigth, it.choose(2));
    }

    @Test
    void moveInTreeTestToLeft (){
        TreeIterator <String> it = tree2.treeIterator();
        assertEquals(left, it.choose(1));
    }

    @Test
    void verrfyNullIncaseofNotRoot (){
        assertThrows(NullPointerException.class, () -> tree.treeIterator());
    }
}
