package interfaz.auxiliars;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.auxiliars.tree.DecisionNode;

import java.util.HashMap;


public class VisualTree  extends Canvas{


    private final Canvas canvas;
    private final GraphicsContext gc;

    private final DecisionNode<?> root;

    private final HashMap<DecisionNode<?>, double[]> positions = new HashMap<>();

    private double scale = 1.0;

    private static final double NODE_RADIUS = 20;
    private static final double H_SPACING = 140;
    private static final double V_SPACING = 90;

    public VisualTree(Canvas canvas, DecisionNode<?> root) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.root = root;

        enableZoom();
    }

    // ============================================================
    //                       DIBUJAR ÁRBOL
    // ============================================================
    public void drawTree() {
        positions.clear();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        layout(root, 1000, 40);
        drawConnections(root);
        drawNodes(root);
    }

    // ============================================================
    //                       CENTRAR NODO
    // ============================================================
    public void focusNode(DecisionNode<?> node) {
        if (!positions.containsKey(node)) return;

        double[] pos = positions.get(node);
        double x = pos[0];
        double y = pos[1];

        ScrollPane scroll = (ScrollPane) canvas.getParent().getParent();

        TranslateTransition tt = new TranslateTransition(Duration.millis(350), scroll);
        tt.setInterpolator(Interpolator.EASE_BOTH);

        tt.setOnFinished(ev -> {
            scroll.setHvalue(x / canvas.getWidth());
            scroll.setVvalue(y / canvas.getHeight());
        });

        tt.play();
    }

    // ============================================================
    //                       ZOOM
    // ============================================================
    private void enableZoom() {
        canvas.addEventFilter(ScrollEvent.SCROLL, e -> {

            double delta = e.getDeltaY() > 0 ? 1.1 : 0.9;
            scale *= delta;

            if (scale < 0.3) scale = 0.3;
            if (scale > 2.4) scale = 2.4;

            canvas.setScaleX(scale);
            canvas.setScaleY(scale);
        });
    }

    // ============================================================
    //                       RESALTAR CAMINO
    // ============================================================
    public void highlightPath(boolean left, boolean right) {

        gc.setStroke(Color.ORANGE);
        gc.setLineWidth(4);

        drawTree();

        DecisionNode<?> n = root;

        while (true) {
            double[] pos = positions.get(n);
            double x = pos[0], y = pos[1];

            if (left && n.getLeft() != null) {
                DecisionNode<?> next = n.getLeft();
                double[] p2 = positions.get(next);
                gc.strokeLine(x, y, p2[0], p2[1]);
                n = next;
                continue;
            }

            if (right && n.getRight() != null) {
                DecisionNode<?> next = n.getRight();
                double[] p2 = positions.get(next);
                gc.strokeLine(x, y, p2[0], p2[1]);
                n = next;
                continue;
            }

            break;
        }
    }

    // ============================================================
    //                       DIBUJAR CONEXIONES
    // ============================================================
    private void drawConnections(DecisionNode<?> n) {
        if (n == null) return;

        double[] from = positions.get(n);

        if (n.getLeft() != null) {
            double[] to = positions.get(n.getLeft());
            gc.setStroke(Color.GRAY);
            gc.setLineWidth(2);
            gc.strokeLine(from[0], from[1], to[0], to[1]);
            drawConnections(n.getLeft());
        }

        if (n.getRight() != null) {
            double[] to = positions.get(n.getRight());
            gc.setStroke(Color.GRAY);
            gc.setLineWidth(2);
            gc.strokeLine(from[0], from[1], to[0], to[1]);
            drawConnections(n.getRight());
        }
    }

    // ============================================================
    //                       DIBUJAR NODOS
    // ============================================================
    private void drawNodes(DecisionNode<?> n) {
        if (n == null) return;

        double[] pos = positions.get(n);

        gc.setFill(Color.YELLOW);
        gc.fillOval(pos[0] - NODE_RADIUS, pos[1] - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        gc.setStroke(Color.BLACK);
        gc.strokeOval(pos[0] - NODE_RADIUS, pos[1] - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        drawNodes(n.getLeft());
        drawNodes(n.getRight());
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
