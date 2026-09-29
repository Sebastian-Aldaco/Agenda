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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

public class ContactosPanel extends JPanel {
    private static final String[] COLUMNAS = {
            "Nombre", "Apellido paterno", "Apellido materno", "Teléfono",
            "Correo", "Dirección", "Año de nacimiento", "Teléfono de emergencia"
    };

    private final Agenda agenda;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final TableRowSorter<DefaultTableModel> sorter;
    private final JLabel statusLabel;
    private final JTextField searchField;
    private Contacto[] contactosMostrados = new Contacto[0];

    public ContactosPanel(Agenda agenda) {
        this.agenda = agenda;
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 24, 24, 24));

        tableModel = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        searchField = new JTextField(18);
        statusLabel = new JLabel();
        add(createToolbar(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        refreshTable();
    }

    private JPanel createToolbar() {
        JPanel toolbar = new JPanel(new BorderLayout(10, 8));
        JPanel actions = new JPanel(new GridLayout(1, 0, 8, 0));
        actions.add(createButton("Agregar", event -> addContact()));
        actions.add(createButton("Editar", event -> editSelectedContact()));
        actions.add(createButton("Eliminar", event -> deleteSelectedContact()));
        actions.add(createButton("Verificar", event -> checkContact()));
        actions.add(createButton("Disponibilidad", event -> showStatus()));
        actions.add(createButton("Actualizar", event -> refreshTable()));

        JPanel search = new JPanel();
        search.add(new JLabel("Buscar:"));
        search.add(searchField);
        search.add(createButton("Filtrar", event -> applySearch()));
        search.add(createButton("Limpiar", event -> clearSearch()));

        toolbar.add(actions, BorderLayout.NORTH);
        toolbar.add(search, BorderLayout.SOUTH);
        return toolbar;
    }

    private JButton createButton(String label, java.awt.event.ActionListener action) {
        JButton button = new JButton(label);
        button.addActionListener(action);
        return button;
    }

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
        JOptionPane.showMessageDialog(this, "Contacto agregado correctamente.");
    }

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
            showError("No se pudo actualizar. Revisa los datos obligatorios y que no exista un duplicado.");
            return;
        }

        refreshTable();
        JOptionPane.showMessageDialog(this, "Contacto actualizado correctamente.");
    }

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
                JOptionPane.WARNING_MESSAGE
        );
        if (confirmation == JOptionPane.YES_OPTION && agenda.quitarContacto(contacto)) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Contacto eliminado correctamente.");
        }
    }

    private void checkContact() {
        JTextField nombre = new JTextField();
        JTextField apellido = new JTextField();
        JPanel fields = new JPanel(new GridLayout(2, 2, 8, 8));
        fields.add(new JLabel("Nombre:"));
        fields.add(nombre);
        fields.add(new JLabel("Apellido paterno:"));
        fields.add(apellido);

        int result = JOptionPane.showConfirmDialog(
                this, fields, "Verificar contacto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        if (nombre.getText().isBlank() || apellido.getText().isBlank()) {
            showError("Escribe el nombre y el apellido paterno.");
            return;
        }

        boolean exists = agenda.existeContacto(nombre.getText().trim(), apellido.getText().trim());
        JOptionPane.showMessageDialog(
                this,
                exists ? "El contacto existe en la agenda." : "No se encontró ese contacto.",
                "Resultado de búsqueda",
                exists ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.PLAIN_MESSAGE
        );
    }

    private void showStatus() {
        int total = agenda.obtenerContactos().length;
        JOptionPane.showMessageDialog(
                this,
                "Contactos registrados: " + total + "\n"
                        + "Capacidad máxima: " + agenda.getTamanoMaximo() + "\n"
                        + "Espacios disponibles: " + agenda.espacioLibres(),
                "Estado de la agenda",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void applySearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(query)));
    }

    private void clearSearch() {
        searchField.setText("");
        sorter.setRowFilter(null);
    }

    private void refreshTable() {
        contactosMostrados = agenda.obtenerContactos();
        Arrays.sort(
                contactosMostrados,
                Comparator.comparing(
                                Contacto::getNombre,
                                Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)
                        )
                        .thenComparing(
                                Contacto::getApellidoPaterno,
                                Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)
                        )
                        .thenComparing(
                                Contacto::getApellidoMaterno,
                                Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)
                        )
        );

        tableModel.setRowCount(0);
        for (Contacto contacto : contactosMostrados) {
            tableModel.addRow(new Object[]{
                    contacto.getNombre(),
                    contacto.getApellidoPaterno(),
                    contacto.getApellidoMaterno(),
                    contacto.getTelefono(),
                    contacto.getCorreo(),
                    contacto.getDireccion(),
                    contacto.getAnioNacimiento() == null || contacto.getAnioNacimiento() == 0
                            ? "" : contacto.getAnioNacimiento(),
                    contacto.getTelefonoDeEmergencia()
            });
        }
        statusLabel.setText(
                "Contactos: " + contactosMostrados.length
                        + " / " + agenda.getTamanoMaximo()
                        + "    |    Espacios disponibles: " + agenda.espacioLibres()
        );
    }

    private Contacto getSelectedContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        int modelRow = table.convertRowIndexToModel(selectedRow);
        return contactosMostrados[modelRow];
    }

    private Contacto showContactForm(String title, Contacto contacto) {
        String[] labels = {
                "Nombre *", "Apellido paterno *", "Apellido materno",
                "Teléfono", "Correo", "Dirección",
                "Año de nacimiento", "Teléfono de emergencia"
        };
        JTextField[] inputs = new JTextField[labels.length];
        JPanel form = new JPanel(new GridLayout(labels.length, 2, 8, 8));

        String[] values = contacto == null ? new String[labels.length] : new String[]{
                contacto.getNombre(),
                contacto.getApellidoPaterno(),
                contacto.getApellidoMaterno(),
                contacto.getTelefono(),
                contacto.getCorreo(),
                contacto.getDireccion(),
                contacto.getAnioNacimiento() == null || contacto.getAnioNacimiento() == 0
                        ? "" : contacto.getAnioNacimiento().toString(),
                contacto.getTelefonoDeEmergencia()
        };
        for (int i = 0; i < labels.length; i++) {
            form.add(new JLabel(labels[i]));
            inputs[i] = new JTextField(values[i] == null ? "" : values[i], 20);
            form.add(inputs[i]);
        }

        while (true) {
            int result = JOptionPane.showConfirmDialog(
                    this, form, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );
            if (result != JOptionPane.OK_OPTION) {
                return null;
            }
            if (inputs[0].getText().isBlank() || inputs[1].getText().isBlank()) {
                showError("El nombre y el apellido paterno son obligatorios.");
                continue;
            }

            int year = 0;
            String yearValue = inputs[6].getText().trim();
            if (!yearValue.isEmpty()) {
                try {
                    year = Integer.parseInt(yearValue);
                } catch (NumberFormatException exception) {
                    showError("El año de nacimiento debe ser un número entero.");
                    continue;
                }
                if (year < 0) {
                    showError("El año de nacimiento no puede ser negativo.");
                    continue;
                }
            }

            return new Contacto(
                    inputs[0].getText().trim(),
                    inputs[1].getText().trim(),
                    inputs[2].getText().trim(),
                    inputs[3].getText().trim(),
                    inputs[4].getText().trim(),
                    inputs[5].getText().trim(),
                    year,
                    inputs[7].getText().trim()
            );
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Revisa los datos", JOptionPane.WARNING_MESSAGE);
    }
}
