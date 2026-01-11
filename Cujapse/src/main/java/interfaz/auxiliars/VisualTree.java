package interfaz.auxiliars;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.auxiliars.tree.DecisionNode;

import java.util.HashMap;

public class VisualTree extends Canvas {

    private final GraphicsContext gc;
    private final DecisionNode<?> root;
    private final HashMap<DecisionNode<?>, double[]> positions = new HashMap<>();
    private final ScrollPane scroll;

    private static final double NODE_RADIUS = 20;
    private static final double H_SPACING = 140;
    private static final double V_SPACING = 90;

    public VisualTree(DecisionNode<?> root, ScrollPane scroll) {
        super(2000, 2000);
        this.gc = getGraphicsContext2D();
        this.root = root;
        this.scroll = scroll;
    }

    // ============================================================
    //                       DIBUJAR ÁRBOL
    // ============================================================
    public void drawTree(DecisionNode<?> currentNode, boolean highlightPath) {
        positions.clear();
        gc.setFill(Color.web("#FFF3E0")); // mismo color que el panel
        gc.fillRect(0, 0, getWidth(), getHeight());


        layout(root, 1000, 40);
        drawConnections(root, currentNode, highlightPath);
        drawNodes(root, currentNode);
    }



    // ============================================================
    //                       CENTRAR NODO
    // ============================================================
    public void focusNode(DecisionNode<?> node) {
        if (!positions.containsKey(node)) return;

        double[] pos = positions.get(node);
        double x = pos[0];
        double y = pos[1];

        TranslateTransition tt = new TranslateTransition(Duration.millis(350), scroll);
        tt.setInterpolator(Interpolator.EASE_BOTH);

        tt.setOnFinished(ev -> {
            scroll.setHvalue(x / getWidth());
            scroll.setVvalue(y / getHeight());
        });

        tt.play();
    }

    // ============================================================
    //                       DIBUJAR CONEXIONES
    // ============================================================
    private void drawConnections(DecisionNode<?> n, DecisionNode<?> currentNode, boolean highlightPath) {
        if (n == null) return;

        double[] from = positions.get(n);

        if (n.getLeft() != null) {
            double[] to = positions.get(n.getLeft());

            boolean isPath = isOnPath(n.getLeft(), currentNode);

            if (highlightPath && isPath) {
                gc.setStroke(Color.web("#E85D04"));
                gc.setLineWidth(4);
            } else {
                gc.setStroke(Color.web("#B0B0B0"));
                gc.setLineWidth(2);
            }

            gc.strokeLine(from[0], from[1], to[0], to[1]);
            drawConnections(n.getLeft(), currentNode, highlightPath);
        }

        if (n.getRight() != null) {
            double[] to = positions.get(n.getRight());

            boolean isPath = isOnPath(n.getRight(), currentNode);

            if (highlightPath && isPath) {
                gc.setStroke(Color.web("#E85D04"));
                gc.setLineWidth(4);
            } else {
                gc.setStroke(Color.web("#B0B0B0"));
                gc.setLineWidth(2);
            }

            gc.strokeLine(from[0], from[1], to[0], to[1]);
            drawConnections(n.getRight(), currentNode, highlightPath);
        }
    }

    // ============================================================
    //                       DIBUJAR NODOS
    // ============================================================
    private void drawNodes(DecisionNode<?> n, DecisionNode<?> currentNode) {
        if (n == null) return;

        double[] pos = positions.get(n);

        boolean isPath = isOnPath(n, currentNode);

        if (n == currentNode) {
            gc.setFill(Color.web("#E85D04")); // nodo actual
        } else if (isPath) {
            gc.setFill(Color.web("#F7A440")); // nodo del camino
        } else {
            gc.setFill(Color.web("#FFD966")); // nodo normal
        }

        gc.fillOval(pos[0] - NODE_RADIUS, pos[1] - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        gc.setStroke(Color.web("#3C0E05"));
        gc.strokeOval(pos[0] - NODE_RADIUS, pos[1] - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        drawNodes(n.getLeft(), currentNode);
        drawNodes(n.getRight(), currentNode);
    }


    // ============================================================
    //                       CAMINO HACIA EL NODO
    // ============================================================
    private boolean isOnPath(DecisionNode<?> node, DecisionNode<?> currentNode) {
        if (node == null) return false;
        if (node == currentNode) return true;
        return isOnPath(node.getLeft(), currentNode) || isOnPath(node.getRight(), currentNode);
    }

    // ============================================================
    //                       LAYOUT DEL ÁRBOL
    // ============================================================
    private double layout(DecisionNode<?> n, double x, double y) {
        if (n == null) return 0;

        double leftWidth = layout(n.getLeft(), x - H_SPACING, y + V_SPACING);
        double rightWidth = layout(n.getRight(), x + H_SPACING, y + V_SPACING);

        positions.put(n, new double[]{x, y});

        return Math.max(leftWidth + rightWidth, NODE_RADIUS * 2);
    }
}
