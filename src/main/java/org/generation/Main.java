package org.generation;

public class Main {
    public static void main(String[] args) {
        /// 1. Creamos la agenda base para el sistema
        Agenda agenda = new Agenda();

        System.out.println("==PRUEBA 1== | Añadir contacto válido");
        Contacto c1 = new Contacto("Devani", "Moreno", "Domínguez", "8119000000", "dev@gmail.com", "Monterrey", 1995, "8110000000");
        agenda.anadirContacto(c1);
    }
}
