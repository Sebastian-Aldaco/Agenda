package org.generation;

import java.util.Scanner;

public class Menu {
    private Agenda agenda;
    private Scanner scanner;

    // Constructor que conecta la agenda desde Main
    public Menu(Agenda agenda) {
        this.agenda = agenda;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion = 0;

        do {
            System.out.println("\n--- 📞 AGENDA DE CONTACTOS ---");
            System.out.println("1. ➕ Añadir contacto");
            System.out.println("2. 🔍 Verificar si existe contacto");
            System.out.println("3. 📋 Listar contactos");
            System.out.println("4. 🔎 Buscar contacto por nombre");
            System.out.println("5. 🗑️ Eliminar contacto");
            System.out.println("6. ✏️ Modificar teléfono");
            System.out.println("7. 📊 Ver estado de la agenda");
            System.out.println("8. 🚪 Salir");
            System.out.print("Selecciona una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el búfer
                procesarOpcion(opcion);
            } else {
                System.out.println("❌ Por favor, ingresa un número entero.");
                scanner.nextLine();
            }

        } while (opcion != 8);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> {
                System.out.print("Nombre: ");
                String nom = scanner.nextLine();
                System.out.print("Apellido: ");
                String ape = scanner.nextLine();
                System.out.print("Teléfono: ");
                String tel = scanner.nextLine();
                agenda.añadirContacto(new Contacto(nom, ape, tel));
            }
            case 2 -> {
                System.out.print("Nombre: ");
                String nom = scanner.nextLine();
                System.out.print("Apellido: ");
                String ape = scanner.nextLine();
                agenda.existeContacto(new Contacto(nom, ape));
            }
            case 3 -> agenda.listarContactos();
            case 4 -> {
                System.out.print("Nombre a buscar: ");
                String nom = scanner.nextLine();
                agenda.buscaContacto(nom);
            }
            case 5 -> {
                System.out.print("Nombre: ");
                String nom = scanner.nextLine();
                System.out.print("Apellido: ");
                String ape = scanner.nextLine();
                agenda.eliminarContacto(new Contacto(nom, ape, ""));
            }
            case 6 -> {
                System.out.print("Nombre: ");
                String nom = scanner.nextLine();
                System.out.print("Apellido: ");
                String ape = scanner.nextLine();
                System.out.print("Nuevo teléfono: ");
                String nuevoTel = scanner.nextLine();
                agenda.modificarTelefono(nom, ape, nuevoTel);
            }
            case 7 -> {
                agenda.agendaLlena();
                agenda.espacioLibres();
            }
            case 8 -> System.out.println("👋 ¡Hasta luego!");
            default -> System.out.println("⚠️ Opción no válida. Intenta de nuevo.");

public class Menu {
}
