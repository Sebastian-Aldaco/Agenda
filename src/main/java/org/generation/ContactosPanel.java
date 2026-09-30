package org.generation;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

public class ContactosPanel extends JPanel {

    // COLUMNAS DE LA TABLA
    private static final String[] COLUMNAS = {"Nombre", "Apellido paterno", "Apellido materno", "Teléfono", "Correo", "Dirección", "Año de nacimiento", "Teléfono de emergencia"};

    // COLORES
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color PRIMARY_DARK = new Color(30, 64, 175);
    private static final Color DANGER = new Color(220, 38, 38);
    private static final Color DANGER_DARK = new Color(185, 28, 28);
    private static final Color SUCCESS = new Color(22, 163, 74);
    private static final Color DARK = new Color(15, 23, 42);
    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color BACKGROUND = new Color(248, 250, 252);
    private static final Color WHITE = Color.WHITE;
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color TABLE_ALTERNATE = new Color(248, 250, 252);
    private static final Color TABLE_SELECTION = new Color(219, 234, 254);

    // FUENTES
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_TABLE = new Font("Segoe UI", Font.PLAIN, 13);

    // COMPONENTES
    private final Agenda agenda;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final TableRowSorter<DefaultTableModel> sorter;
    private final JLabel statusLabel;
    private final JTextField searchField;
    private Contacto[] contactosMostrados = new Contacto[0];

    // CONSTRUCTOR

    public ContactosPanel(Agenda agenda) {
        this.agenda = agenda;
        setLayout(new BorderLayout(16, 16));

        setBackground(BACKGROUND);

        setBorder(BorderFactory.createEmptyBorder(24, 30, 20, 30));

        // MODELO DE TABLA
        tableModel = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // TABLA

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(38);
        table.setFont(FONT_TABLE);
        table.setForeground(TEXT);
        table.setBackground(WHITE);
        table.setSelectionBackground(TABLE_SELECTION);
        table.setSelectionForeground(DARK);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // SORTER
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // ESTILO DEL HEADER
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HEADER);
        header.setForeground(WHITE);
        header.setBackground(DARK);
        header.setPreferredSize(new Dimension(header.getWidth(), 42));

        header.setReorderingAllowed(false);

        // RENDERER DEL HEADER
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(DARK);
        headerRenderer.setForeground(WHITE);
        headerRenderer.setFont(FONT_TABLE_HEADER);
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        headerRenderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        header.setDefaultRenderer(headerRenderer);

        // RENDERER DE CELDAS
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(FONT_TABLE);
                label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                if (isSelected) {
                    label.setBackground(TABLE_SELECTION);
                    label.setForeground(DARK);
                } else {
                    if (row % 2 == 0) {

                        label.setBackground(WHITE);
                    } else {
                        label.setBackground(TABLE_ALTERNATE);
                    }
                    label.setForeground(TEXT);
                }
                return label;
            }
        };
        table.setDefaultRenderer(Object.class, cellRenderer);

        // BUSCADOR
        searchField = new JTextField();
        searchField.setFont(FONT_NORMAL);
        searchField.setPreferredSize(new Dimension(250, 38));
        searchField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER), BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        // ESTADO
        statusLabel = new JLabel();
        statusLabel.setFont(FONT_NORMAL);
        statusLabel.setForeground(TEXT_SECONDARY);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 5, 0, 5));

        // AGREGAR COMPONENTES
        add(createToolbar(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // DOBLE CLICK
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1) {
                    editSelectedContact();
                }
            }
        });

        refreshTable();
    }

    // TOOLBAR
    private JPanel createToolbar() {

        JPanel container = new JPanel(new BorderLayout(0, 12));
        container.setBackground(BACKGROUND);

        // TÍTULO
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        JLabel title = new JLabel("Contactos");
        title.setFont(FONT_TITLE);
        title.setForeground(DARK);

        JLabel subtitle = new JLabel("Administra los contactos registrados en tu agenda");
        subtitle.setFont(FONT_NORMAL);
        subtitle.setForeground(TEXT_SECONDARY);
        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(subtitle, BorderLayout.SOUTH);
        container.add(titlePanel, BorderLayout.NORTH);

        // PANEL DE ACCIONES
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setOpaque(false);
        actions.add(createPrimaryButton("＋ Agregar", event -> addContact()));
        actions.add(createSecondaryButton("✎ Editar", event -> editSelectedContact()));
        actions.add(createDangerButton("✕ Eliminar", event -> deleteSelectedContact()));
        actions.add(createSecondaryButton("✓ Verificar", event -> checkContact()));
        actions.add(createSecondaryButton("▣ Disponibilidad", event -> showStatus()));
        actions.add(createSecondaryButton("↻ Actualizar", event -> refreshTable()));

        // BUSCADOR
        JPanel search = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        search.setOpaque(false);
        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setFont(FONT_NORMAL);
        searchLabel.setForeground(TEXT);
        JButton filterButton = createPrimaryButton("Filtrar", event -> applySearch());
        JButton clearButton = createSecondaryButton("Limpiar", event -> clearSearch());

        search.add(searchLabel);
        search.add(searchField);
        search.add(filterButton);
        search.add(clearButton);
        JPanel toolbarRow = new JPanel(new BorderLayout());
        toolbarRow.setOpaque(false);
        toolbarRow.add(actions, BorderLayout.WEST);
        toolbarRow.add(search, BorderLayout.EAST);
        container.add(toolbarRow, BorderLayout.SOUTH);

        return container;
    }


    // PANEL DE TABLA
    private JPanel createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);
        panel.setBorder(BorderFactory.createLineBorder(BORDER));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // BOTÓN PRINCIPAL
    private JButton createPrimaryButton(String label, java.awt.event.ActionListener action) {
        JButton button = new JButton(label);
        styleButton(button, PRIMARY);
        button.addActionListener(action);
        return button;
    }

    // BOTÓN SECUNDARIO

    private JButton createSecondaryButton(String label, java.awt.event.ActionListener action) {

        JButton button = new JButton(label);
        styleButton(button, DARK);
        button.addActionListener(action);
        return button;
    }

    // BOTÓN PELIGRO

    private JButton createDangerButton(String label, java.awt.event.ActionListener action) {

        JButton button = new JButton(label);
        styleButton(button, DANGER);
        button.addActionListener(action);
        return button;
    }

    // ESTILO DE BOTONES
    private void styleButton(JButton button, Color background) {

        button.setFont(FONT_BUTTON);
        button.setForeground(WHITE);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(9, 15, 9, 15));
    }

    // AGREGAR CONTACTO
    private void addContact() {
        Contacto contacto = showContactForm("Agregar contacto", null);
        if (contacto == null) {
            return;
        }
        if (!agenda.agregarContacto(contacto)) {
            if (agenda.agendaLlena()) {
                showError("La agenda está llena; no se pueden agregar más contactos.");
            } else if (agenda.existeContacto(contacto)) {
                showError("Ya existe un contacto con ese nombre y apellidos.");
            } else {
                showError("El nombre y el apellido paterno son obligatorios.");
            }
            return;
        }

        refreshTable();
        showSuccess("Contacto agregado correctamente.");
    }

    // EDITAR CONTACTO
    private void editSelectedContact() {

        Contacto original = getSelectedContact();

        if (original == null) {
            showError("Selecciona un contacto para editar.");
            return;
        }

        Contacto actualizado = showContactForm("Editar contacto", original);

        if (actualizado == null) {
            return;
        }

        if (!agenda.actualizarContacto(original, actualizado)) {
            showError("No se pudo actualizar. " + "Revisa los datos obligatorios " + "y que no exista un duplicado.");
            return;
        }

        refreshTable();

        showSuccess("Contacto actualizado correctamente.");
    }


    // ELIMINAR CONTACTO
    private void deleteSelectedContact() {

        Contacto contacto = getSelectedContact();

        if (contacto == null) {
            showError("Selecciona un contacto para eliminar.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar a " + contacto.getNombre() + " " + contacto.getApellidoPaterno() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmation == JOptionPane.YES_OPTION && agenda.quitarContacto(contacto)) {
            refreshTable();
            showSuccess("Contacto eliminado correctamente.");
        }
    }


    // VERIFICAR CONTACTO
    private void checkContact() {

        JTextField nombre = createInputField();
        JTextField apellido = createInputField();
        JPanel fields = new JPanel(new GridLayout(2, 2, 12, 12));
        fields.setBackground(WHITE);
        fields.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fields.add(createFormLabel("Nombre:"));
        fields.add(nombre);
        fields.add(createFormLabel("Apellido paterno:"));
        fields.add(apellido);

        int result = showStyledConfirmDialog(fields, "Verificar contacto");

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        if (nombre.getText().isBlank() || apellido.getText().isBlank()) {
            showError("Escribe el nombre " + "y el apellido paterno.");
            return;
        }

        boolean exists = agenda.existeContacto(nombre.getText().trim(), apellido.getText().trim());
        JOptionPane.showMessageDialog(
                this,
                exists ? "El contacto existe en la agenda." : "No se encontró ese contacto.",
                "Resultado de búsqueda",
                exists ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.PLAIN_MESSAGE);
    }

    // DISPONIBILIDAD
    private void showStatus() {
        int total = agenda.obtenerContactos().length;
        JOptionPane.showMessageDialog(
                this,
                "Contactos registrados: " + total + "\n\n" + "Capacidad máxima: " + agenda.getTamanoMaximo() + "\n\n" + "Espacios disponibles: " + agenda.espacioLibres(),
                "Estado de la agenda",
                JOptionPane.INFORMATION_MESSAGE);
    }


    // BÚSQUEDA
    private void applySearch() {

        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(query)));
    }


    // LIMPIAR BÚSQUEDA
    private void clearSearch() {
        searchField.setText("");
        sorter.setRowFilter(null);
    }


    // ACTUALIZAR TABLA
    private void refreshTable() {
        contactosMostrados = agenda.obtenerContactos();
        Arrays.sort(
                contactosMostrados,
                Comparator.comparing(Contacto::getNombre, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(Contacto::getApellidoPaterno, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(Contacto::getApellidoMaterno, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)));
        tableModel.setRowCount(0);

        for (Contacto contacto : contactosMostrados) {
            tableModel.addRow(new Object[]{
                    contacto.getNombre(),
                    contacto.getApellidoPaterno(),
                    contacto.getApellidoMaterno(),
                    contacto.getTelefono(),
                    contacto.getCorreo(),
                    contacto.getDireccion(),
                    contacto.getAnioNacimiento() == null || contacto.getAnioNacimiento() == 0 ? "" : contacto.getAnioNacimiento(),
                    contacto.getTelefonoDeEmergencia()});
        }

        statusLabel.setText(

                "  Contactos: " + contactosMostrados.length + " / " + agenda.getTamanoMaximo() + "     •     Espacios disponibles: " + agenda.espacioLibres());
    }


    // OBTENER CONTACTO SELECCIONADO
    private Contacto getSelectedContact() {

        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        return contactosMostrados[modelRow];
    }


    // FORMULARIO DE CONTACTO
    private Contacto showContactForm(String title, Contacto contacto) {
        String[] labels = {
                "Nombre *", "Apellido paterno *", "Apellido materno", "Teléfono", "Correo", "Dirección", "Año de nacimiento", "Teléfono de emergencia"};
        JTextField[] inputs = new JTextField[labels.length];
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] values;

        if (contacto == null) {
            values = new String[labels.length];
        } else {

            values = new String[]{
                    contacto.getNombre(),
                    contacto.getApellidoPaterno(),
                    contacto.getApellidoMaterno(),
                    contacto.getTelefono(),
                    contacto.getCorreo(),
                    contacto.getDireccion(),
                    contacto.getAnioNacimiento() == null || contacto.getAnioNacimiento() == 0 ? "" : contacto.getAnioNacimiento().toString(),
                    contacto.getTelefonoDeEmergencia()};
        }

        // CONSTRUIR CAMPOS
        for (int i = 0; i < labels.length; i++) {
            GridBagConstraints labelGbc = new GridBagConstraints();
            labelGbc.gridx = 0;
            labelGbc.gridy = i;
            labelGbc.weightx = 0;
            labelGbc.fill = GridBagConstraints.HORIZONTAL;
            labelGbc.insets = new Insets(6, 6, 6, 15);
            form.add(createFormLabel(labels[i]), labelGbc);
            GridBagConstraints fieldGbc = new GridBagConstraints();
            fieldGbc.gridx = 1;
            fieldGbc.gridy = i;
            fieldGbc.weightx = 1;
            fieldGbc.fill = GridBagConstraints.HORIZONTAL;
            fieldGbc.insets = new Insets(6, 6, 6, 6);
            inputs[i] = createInputField();
            inputs[i].setText(values[i] == null ? "" : values[i]);
            form.add(inputs[i], fieldGbc);
        }


        // DIÁLOGO
        while (true) {
            int result = showStyledConfirmDialog(form, title);
            if (result != JOptionPane.OK_OPTION) {
                return null;
            }

            // VALIDAR NOMBRE
            if (inputs[0].getText().isBlank() || inputs[1].getText().isBlank()) {
                showError("El nombre y el apellido " + "paterno son obligatorios.");
                continue;
            }

            // VALIDAR AÑO
            int year = 0;
            String yearValue = inputs[6].getText().trim();
            if (!yearValue.isEmpty()) {
                try {
                    year = Integer.parseInt(yearValue);
                } catch (NumberFormatException exception) {
                    showError("El año de nacimiento " + "debe ser un número entero.");
                    continue;
                }

                if (year < 0) {
                    showError("El año de nacimiento " + "no puede ser negativo.");
                    continue;
                }
            }

            // CREAR CONTACTO
            return new Contacto(
                    inputs[0].getText().trim(),
                    inputs[1].getText().trim(),
                    inputs[2].getText().trim(),
                    inputs[3].getText().trim(),
                    inputs[4].getText().trim(),
                    inputs[5].getText().trim(),
                    year,
                    inputs[7].getText().trim());
        }
    }

    // CAMPO DE TEXTO
    private JTextField createInputField() {
        JTextField field = new JTextField();
        field.setFont(FONT_NORMAL);
        field.setPreferredSize(new Dimension(260, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return field;
    }

    // LABEL DE FORMULARIO
    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_NORMAL);
        label.setForeground(TEXT);
        return label;
    }

    // DIÁLOGO ESTILIZADO
    private int showStyledConfirmDialog(Object message, String title) {
        UIManager.put("OptionPane.background", WHITE);
        UIManager.put("Panel.background", WHITE);
        UIManager.put("OptionPane.messageFont", FONT_NORMAL);
        UIManager.put("OptionPane.buttonFont", FONT_BUTTON);

        return JOptionPane.showConfirmDialog(
                this,
                message,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    // MENSAJE DE ERROR
    private void showError(String message) {
        UIManager.put("OptionPane.background", WHITE);
        UIManager.put("Panel.background", WHITE);
        JOptionPane.showMessageDialog(
                this,
                message,
                "Revisa los datos",
                JOptionPane.WARNING_MESSAGE);
    }

    // MENSAJE DE ÉXITO
    private void showSuccess(String message) {
        UIManager.put("OptionPane.background", WHITE);
        UIManager.put("Panel.background", WHITE);
        JOptionPane.showMessageDialog(
                this,
                message,
                "Operación realizada",
                JOptionPane.INFORMATION_MESSAGE);
    }
}