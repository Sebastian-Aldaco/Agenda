
<<<<<<< HEAD
=======
public class Main {
    public static void main(String[] args) {
        /// 1. Creamos la agenda base para el sistema
        Agenda agenda = new Agenda();

        System.out.println("==PRUEBA 1== | Añadir contacto válido");
        Contacto c1 = new Contacto("Devani", "Moreno", "Domínguez", "8119000000", "dev@gmail.com", "Monterrey", 1995, "8110000000");
        agenda.anadirContacto(c1);

        // PRUEBA 2: Nombre vacío
        System.out.println("\n== PRUEBA 2: Nombre vacío ==");

        Contacto c2 = new Contacto(
                "",
                "Moreno",
                "Domínguez",
                "8111111111",
                "test@gmail.com",
                "Monterrey",
                1995,
                "8111111112"
        );

        agenda.anadirContacto(c2);

        // PRUEBA 3: Apellido paterno vacío
        System.out.println("\n== PRUEBA 3: Apellido paterno vacío ==");

        Contacto c3 = new Contacto(
                "Ana",
                "",
                "García",
                "8111111111",
                "ana@gmail.com",
                "Monterrey",
                1995,
                "8111111112"
        );

        agenda.anadirContacto(c3);

        // PRUEBA 4: Apellido materno vacío
        System.out.println("\n== PRUEBA 4: Apellido materno vacío ==");

        Contacto c4 = new Contacto(
                "Carlos",
                "Gómez",
                "",
                "8111111111",
                "carlos@gmail.com",
                "Monterrey",
                1995,
                "8111111112"
        );

        agenda.anadirContacto(c4);


    }
}
>>>>>>> origin/main
