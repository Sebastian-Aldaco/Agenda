package org.generation;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WindowManager {
    public static final String HOME_VIEW = "home";
    public static final String CONTACTS_VIEW = "contacts";

    private final JFrame window;
    private final Agenda agenda;
    private final CardLayout viewLayout;
    private final JPanel viewContainer;
    private final Set<String> registeredViews;

    public WindowManager() {
        this(new Agenda());
    }

    public WindowManager(Agenda agenda) {
        this.agenda = Objects.requireNonNull(agenda, "La agenda no puede ser nula.");
        this.window = new JFrame("Agenda");
        this.viewLayout = new CardLayout();
        this.viewContainer = new JPanel(viewLayout);
        this.registeredViews = new HashSet<>();

        configureWindow();
        registerView(HOME_VIEW, createHomeView());
        registerView(CONTACTS_VIEW, new ContactosPanel(agenda));
        showView(HOME_VIEW);
    }

    private void configureWindow() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setMinimumSize(new Dimension(720, 480));
        window.setSize(1080, 720);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
        window.add(createHeader(), BorderLayout.NORTH);
        window.add(viewContainer, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        JLabel title = new JLabel("Agenda de contactos");
        title.setFont(title.getFont().deriveFont(22f));
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton homeButton = new JButton("Inicio");
        homeButton.addActionListener(event -> showView(HOME_VIEW));
        JButton contactsButton = new JButton("Contactos");
        contactsButton.addActionListener(event -> showView(CONTACTS_VIEW));
        navigation.add(homeButton);
        navigation.add(contactsButton);

        header.add(title, BorderLayout.WEST);
        header.add(navigation, BorderLayout.EAST);
        return header;
    }

    private JPanel createHomeView() {
        JPanel home = new JPanel(new BorderLayout());
        home.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel welcome = new JPanel(new BorderLayout(12, 12));
        JLabel title = new JLabel("Administra tus contactos en un solo lugar", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(24f));
        JLabel description = new JLabel(
                "Agrega, consulta, busca, edita y elimina los contactos de tu agenda.",
                SwingConstants.CENTER
        );
        JButton openContacts = new JButton("Abrir agenda de contactos");
        openContacts.addActionListener(event -> showView(CONTACTS_VIEW));
        JPanel action = new JPanel();
        action.add(openContacts);
        welcome.add(title, BorderLayout.CENTER);
        welcome.add(description, BorderLayout.SOUTH);
        home.add(welcome, BorderLayout.CENTER);
        home.add(action, BorderLayout.SOUTH);
        return home;
    }

    public void registerView(String viewId, Component view) {
        Objects.requireNonNull(viewId, "El identificador de la vista no puede ser nulo.");
        Objects.requireNonNull(view, "La vista no puede ser nula.");
        if (viewId.isBlank()) {
            throw new IllegalArgumentException("El identificador de la vista no puede estar vacío.");
        }
        if (!registeredViews.add(viewId)) {
            throw new IllegalArgumentException("Ya existe una vista registrada con el identificador: " + viewId);
        }

        viewContainer.add(view, viewId);
    }

    public void showView(String viewId) {
        if (!registeredViews.contains(viewId)) {
            throw new IllegalArgumentException("No existe una vista registrada con el identificador: " + viewId);
        }
        viewLayout.show(viewContainer, viewId);
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public JFrame getWindow() {
        return window;
    }

    public void show() {
        window.setVisible(true);
    }
}
