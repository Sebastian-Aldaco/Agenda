package org.generation;

public class Main {
    public static void main(String[] args) {
        /// 1. Creamos la agenda base para el sistema
        Agenda agenda = new Agenda();

        /// 2. Creamos el menú y le conectamos la agenda
        Menu menu = new Menu(agenda);

        /// 3. Iniciamos la interacción por consola
        menu.mostrarMenu();

        }
    }
}