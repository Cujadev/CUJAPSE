package Data;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Interfaz simple para introducir personajes, diálogos y consecuencias usando FileManager.
 */
public class EditorUI extends JFrame {

    // Campos de creación de personaje
    private final JTextField charIdField = new JTextField(10);
    private final JTextField charNameField = new JTextField(12);
    private final JTextField charImageField =
            new JTextField("src/main/resources/visualResources/personajes/example.png", 24);

    // Campos de diálogo secundario
    private final JTextField dialogIdField = new JTextField(6);
    private final JTextArea dialogTextArea = new JTextArea(4, 30);
    private final JTextField dialogCharIdField = new JTextField(6);

    // Campos de diálogo del principal
    private final JTextField principalIdField = new JTextField(6);
    private final JTextArea principalLeftArea = new JTextArea(3, 28);
    private final JTextArea principalRightArea = new JTextArea(3, 28);

    // Campos de consecuencias
    private final JTextField consIdField = new JTextField(6);
    private final JTextField consLeftField = new JTextField("0,0,0,0", 12);
    private final JTextField consRightField = new JTextField("0,0,0,0", 12);

    // Área de estado
    private final JTextArea statusArea = new JTextArea(6, 60);

    public EditorUI() {
        super("Editor de personajes y diálogos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // comprobación ligera de conectividad con FileManager (no modifica ficheros)
        checkFileManagerWiring();

        updateFileStatus();
    }

    private void buildUI() {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        main.add(buildCreateCharPanel());
        main.add(buildCharDialogPanel());
        main.add(buildPrincipalPanel());
        main.add(buildConsequencePanel());
        main.add(buildButtonsPanel());

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createTitledBorder("Estado de ficheros (src/Data)"));

        statusArea.setEditable(false);
        statusPanel.add(new JScrollPane(statusArea), BorderLayout.CENTER);

        JButton refresh = new JButton("Refrescar estado");
        refresh.addActionListener(e -> updateFileStatus());
        statusPanel.add(refresh, BorderLayout.SOUTH);

        main.add(statusPanel);

        add(new JScrollPane(main), BorderLayout.CENTER);
    }

    // ---------------------------
    // 1. Crear personaje
    // ---------------------------
    private JPanel buildCreateCharPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBorder(BorderFactory.createTitledBorder("1) Crear personaje secundario"));

        p.add(new JLabel("id:"));
        p.add(charIdField);

        p.add(new JLabel("nombre:"));
        p.add(charNameField);

        p.add(new JLabel("imagen:"));
        p.add(charImageField);

        JButton btn = new JButton("Crear");
        btn.addActionListener(e -> onCreateCharacter());
        p.add(btn);

        return p;
    }

    // ---------------------------
    // 2. Añadir diálogo a secundario
    // ---------------------------
    private JPanel buildCharDialogPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("2) Añadir diálogo al personaje (usa id del personaje)"));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Tooltips explicativos para que quede claro qué hace cada campo
        dialogCharIdField.setToolTipText("Id del personaje. Se guarda en src/Data/dialogues_{charId}.dat (ej: omar)");
        dialogIdField.setToolTipText("Id del diálogo (ej: d001). Agrupa diálogos y se usa para enlazar pares y consecuencias.");

        top.add(new JLabel("char id:"));
        top.add(dialogCharIdField);

        top.add(new JLabel("dialog id:"));
        top.add(dialogIdField);

        JButton btn = new JButton("Añadir diálogo");
        btn.addActionListener(e -> onAddSecondaryDialogue());
        top.add(btn);

        p.add(top, BorderLayout.NORTH);
        p.add(new JScrollPane(dialogTextArea), BorderLayout.CENTER);

        return p;
    }

    // ---------------------------
    // 3. Añadir par del principal
    // ---------------------------
    private JPanel buildPrincipalPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(BorderFactory.createTitledBorder("3) Añadir par de respuestas al principal"));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Tooltip: id que agrupa el par de respuestas y se debe usar como referencia para consecuencias
        principalIdField.setToolTipText("Id numérico/clave para el par de respuestas; debe coincidir con la id de la consecuencia asociada.");

        top.add(new JLabel("id:"));
        top.add(principalIdField);

        JButton btn = new JButton("Añadir par");
        btn.addActionListener(e -> onAddPrincipalPair());
        top.add(btn);

        p.add(top, BorderLayout.NORTH);

        JPanel texts = new JPanel(new GridLayout(1, 2));
        texts.add(new JScrollPane(principalLeftArea));
        texts.add(new JScrollPane(principalRightArea));

        p.add(texts, BorderLayout.CENTER);

        return p;
    }

    // ---------------------------
    // 4. Consecuencias
    // ---------------------------
    private JPanel buildConsequencePanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBorder(BorderFactory.createTitledBorder("4) Guardar consecuencia (CSV cuatro valores)"));

        // Tooltip: la id debe ser la misma que la usada en el par principal para enlazar consecuencias
        consIdField.setToolTipText("Id de la consecuencia; debe coincidir con la id del par principal que la provoca.");

        p.add(new JLabel("id:"));
        p.add(consIdField);

        p.add(new JLabel("izq:"));
        p.add(consLeftField);

        p.add(new JLabel("der:"));
        p.add(consRightField);

        JButton btn = new JButton("Guardar");
        btn.addActionListener(e -> onAddConsequence());
        p.add(btn);

        return p;
    }

    // ---------------------------
    // Botón cerrar
    // ---------------------------
    private JPanel buildButtonsPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnClose = new JButton("Cerrar");
        btnClose.addActionListener(e -> dispose());
        p.add(btnClose);
        return p;
    }

    // ============================================================
    // ACTIONS
    // ============================================================
    private void onCreateCharacter() {
        String id = charIdField.getText().trim();
        String name = charNameField.getText().trim();
        String img = charImageField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Rellena id y nombre.");
            return;
        }

        try {
            FileManager.createSecondaryCharacter(id, name, img, null);
            JOptionPane.showMessageDialog(this, "Personaje creado: " + id);
            updateFileStatus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear personaje: " + ex.getMessage());
        }
    }

    private void onAddSecondaryDialogue() {
        String charId = dialogCharIdField.getText().trim();
        String did = dialogIdField.getText().trim();
        String text = dialogTextArea.getText().trim();

        if (charId.isEmpty() || did.isEmpty() || text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            FileManager.addSecondaryDialogue(charId, did, text);
            JOptionPane.showMessageDialog(this,
                    "Diálogo guardado en: " +
                            FileManager.defaultDialoguesFileForCharacter(charId).getPath());
            updateFileStatus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void onAddPrincipalPair() {
        String id = principalIdField.getText().trim();
        String left = principalLeftArea.getText().trim();
        String right = principalRightArea.getText().trim();

        if (id.isEmpty() || left.isEmpty() || right.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            FileManager.addPrincipalAnswerPair(id, left, right);
            JOptionPane.showMessageDialog(this,
                    "Par guardado en: " + FileManager.PRINCIPAL_DIALOGUES_FILE.getPath());
            updateFileStatus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void onAddConsequence() {
        String id = consIdField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "id obligatorio.");
            return;
        }

        Integer[] left = parseCSVtoFour(consLeftField.getText());
        Integer[] right = parseCSVtoFour(consRightField.getText());
        if (left == null || right == null) {
            JOptionPane.showMessageDialog(this, "Formato CSV inválido. Ej: 0,0,0,0");
            return;
        }

        try {
            FileManager.addConsequence(id, left, right);
            JOptionPane.showMessageDialog(this,
                    "Consecuencia guardada en: " + FileManager.CONSEQUENCES_FILE.getPath());
            updateFileStatus();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private Integer[] parseCSVtoFour(String csv) {
        try {
            String[] p = csv.split(",");
            if (p.length != 4) return null;

            return new Integer[]{
                    Integer.parseInt(p[0].trim()),
                    Integer.parseInt(p[1].trim()),
                    Integer.parseInt(p[2].trim()),
                    Integer.parseInt(p[3].trim())
            };
        } catch (Exception e) {
            return null;
        }
    }

    // ============================================================
    // Estado de archivos
    // ============================================================
    private void updateFileStatus() {
        StringBuilder sb = new StringBuilder();

        try {
            File dataDir = new File("src/Data");

            File f1 = FileManager.CHARACTERS_FILE;
            sb.append(f1.getPath()).append(" -> ").append(f1.exists() ? "OK (" + f1.length() + " bytes)" : "MISSING").append("\n");

            if (dataDir.exists()) {
                File[] dialogs = dataDir.listFiles((dir, name) -> name.startsWith("dialogues_") && name.endsWith(".dat"));
                sb.append("Dialogues files:\n");
                if (dialogs != null) {
                    for (File df : dialogs) {
                        sb.append(" - ").append(df.getName()).append(" -> OK (").append(df.length()).append(" bytes)\n");
                    }
                }
            }

            sb.append(FileManager.PRINCIPAL_DIALOGUES_FILE.getPath())
                    .append(" -> ").append(FileManager.PRINCIPAL_DIALOGUES_FILE.exists() ? "OK" : "MISSING").append("\n");

            sb.append(FileManager.CONSEQUENCES_FILE.getPath())
                    .append(" -> ").append(FileManager.CONSEQUENCES_FILE.exists() ? "OK" : "MISSING").append("\n");

        } catch (Exception ex) {
            sb.append("Error al comprobar ficheros: ").append(ex.getMessage());
        }

        statusArea.setText(sb.toString());
    }

    private void checkFileManagerWiring() {
        try {
            // solo consultamos las rutas estáticas para comprobar que la clase está accesible
            StringBuilder sb = new StringBuilder();
            sb.append("FileManager: CHARACTERS_FILE=").append(FileManager.CHARACTERS_FILE.getPath()).append("\n");
            sb.append("FileManager: PRINCIPAL_DIALOGUES_FILE=").append(FileManager.PRINCIPAL_DIALOGUES_FILE.getPath()).append("\n");
            sb.append("FileManager: CONSEQUENCES_FILE=").append(FileManager.CONSEQUENCES_FILE.getPath()).append("\n");
            sb.append("\nAyuda rápida:\n");
            sb.append(" - char id (panel 2): el diálogo se guarda en src/Data/dialogues_{charId}.dat\n");
            sb.append(" - dialog id: identificador del diálogo; usar la misma id para el par principal y la consecuencia si están relacionados.\n");
            sb.append(" - principal id (panel 3) ↔ consequence id (panel 4): deben coincidir para enlazar efectos.\n");

            // no escribimos ni inicializamos nada aquí; solo mostramos en el área de estado si todo está accesible
            statusArea.append(sb.toString());
        } catch (Exception ex) {
            statusArea.append("Error accediendo a FileManager: " + ex.getMessage() + "\n");
        }
    }

    // ============================================================
    // MAIN
    // ============================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorUI::new);
    }
}
