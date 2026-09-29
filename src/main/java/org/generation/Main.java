package org.generation;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WindowManager windowManager = new WindowManager(new Agenda());
            windowManager.show();
        });
    }
}
