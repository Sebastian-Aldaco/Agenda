package org.generation;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WindowManager {

    public static final String HOME_VIEW = "home";
    public static final String CONTACTS_VIEW = "contacts";

    // PALETA DE COLORES
    private static final Color PRIMARY = new Color(37, 99, 235);
    private static final Color PRIMARY_DARK = new Color(30, 64, 175);
    private static final Color DARK = new Color(15, 23, 42);
    private static final Color TEXT = new Color(30, 41, 59);
    private static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private static final Color BACKGROUND = new Color(248, 250, 252);
    private static final Color WHITE = Color.WHITE;
    private static final Color BORDER = new Color(226, 232, 240);

    // FUENTES
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_HOME_TITLE = new Font("Segoe UI", Font.BOLD, 30);
    private static final Font FONT_HOME_DESCRIPTION = new Font("Segoe UI", Font.PLAIN, 15);

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
        this.window = new JFrame("Agenda de Contactos");
        this.viewLayout = new CardLayout();
        this.viewContainer = new JPanel(viewLayout);
        this.viewContainer.setBackground(BACKGROUND);
        this.registeredViews = new HashSet<>();

        configureWindow();

        registerView(HOME_VIEW, createHomeView());
        registerView(CONTACTS_VIEW, new ContactosPanel(agenda));

        showView(HOME_VIEW);
    }

    // CONFIGURACIÓN DE LA VENTANA
    private void configureWindow() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setMinimumSize(new Dimension(800, 550));
        window.setSize(1100, 700);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
        window.getContentPane().setBackground(BACKGROUND);
        window.add(createHeader(), BorderLayout.NORTH);
        window.add(viewContainer, BorderLayout.CENTER);
    }

    // HEADER
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARK);
        header.setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));

        // TÍTULO
        JLabel title = new JLabel("Agenda de Contactos");
        title.setForeground(WHITE);
        title.setFont(FONT_TITLE);

        // NAVEGACIÓN
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navigation.setOpaque(false);
        JButton homeButton = createNavigationButton("Inicio");
        JButton contactsButton = createNavigationButton("Contactos");
        homeButton.addActionListener(event -> showView(HOME_VIEW));
        contactsButton.addActionListener(event -> showView(CONTACTS_VIEW));
        navigation.add(homeButton);
        navigation.add(contactsButton);
        header.add(title, BorderLayout.WEST);
        header.add(navigation, BorderLayout.EAST);

        return header;
    }

    // BOTÓN DE NAVEGACIÓN
    private JButton createNavigationButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setForeground(WHITE);
        button.setBackground(DARK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(10, 18, 10, 18));
        return button;
    }

    // HOME
    private JPanel createHomeView() {

        JPanel home = new JPanel(new BorderLayout());
        home.setBackground(BACKGROUND);
        home.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // TARJETA PRINCIPAL
        JPanel card = new JPanel(new BorderLayout(20, 20));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER, 1), BorderFactory.createEmptyBorder(45, 50, 45, 50)));

        // CONTENIDO
        JPanel content = new JPanel(new BorderLayout(15, 15));

        content.setOpaque(false);
        JLabel title = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Administra tus contactos<br>"
                        + "de forma sencilla"
                        + "</div></html>",
                SwingConstants.CENTER
        );

        title.setFont(FONT_HOME_TITLE);
        title.setForeground(DARK);

        JLabel description = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "Agrega, consulta, busca, edita y elimina "
                        + "los contactos de tu agenda.<br>"
                        + "Toda tu información organizada en un solo lugar."
                        + "</div></html>",
                SwingConstants.CENTER
        );

        description.setFont(FONT_HOME_DESCRIPTION);
        description.setForeground(TEXT_SECONDARY);
        content.add(title, BorderLayout.CENTER);
        content.add(description, BorderLayout.SOUTH);

        // BOTÓN
        JPanel action = new JPanel(new FlowLayout(FlowLayout.CENTER));
        action.setOpaque(false);
        JButton openContacts = createPrimaryButton("Abrir agenda de contactos");
        openContacts.addActionListener(event -> showView(CONTACTS_VIEW));
        action.add(openContacts);

        card.add(content, BorderLayout.CENTER);
        card.add(action, BorderLayout.SOUTH);
        home.add(card, BorderLayout.CENTER);
        return home;
    }

    // BOTÓN PRINCIPAL
    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BUTTON);
        button.setForeground(WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(13, 25, 13, 25));
        return button;
    }

    // REGISTRAR VISTA
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

    // CAMBIAR DE VISTA
    public void showView(String viewId) {
        if (!registeredViews.contains(viewId)) {
            throw new IllegalArgumentException("No existe una vista registrada con el identificador: " + viewId);
        }
        viewLayout.show(viewContainer, viewId);
    }

    // GETTERS
    public Agenda getAgenda() {
        return agenda;
    }

    public JFrame getWindow() {
        return window;
    }

    // MOSTRAR VENTANA
    public void show() {
        window.setVisible(true);
    }
}