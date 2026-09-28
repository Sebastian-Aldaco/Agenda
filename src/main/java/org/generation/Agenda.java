package org.generation;

import java.util.Arrays;
import java.util.Comparator;

public class Agenda {

    private Contacto[] contactos;
    private int tamanioMaximo;
    private int contadorContactos;

    // =====================================================
    // PARTE DE ROSARIO
    // listarContactos() + eliminarContacto()
    // =====================================================

    /**
     * Muestra todos los contactos almacenados en la agenda.
     * Los ordena alfabéticamente por nombre, apellido paterno
     * y apellido materno, sin distinguir mayúsculas y minúsculas.
     * Formato:
     * Nombre ApellidoPaterno ApellidoMaterno - Teléfono
     */
    public void listarContactos() {

        // Verificamos si la agenda está vacía.
        if (contactos == null || contadorContactos == 0) {
            System.out.println("La agenda no tiene contactos.");
            return;
        }
        /*
         * Ordenamos solamente las posiciones del arreglo
         * que contienen contactos.
         * Esto evita ordenar las posiciones null que todavía
         * están disponibles en la agenda.
         */
        Arrays.sort(
                contactos,
                0,
                contadorContactos,
                Comparator.comparing(
                        Contacto::getNombre,
                        String.CASE_INSENSITIVE_ORDER
                ).thenComparing(
                        Contacto::getApellidoPaterno,
                        String.CASE_INSENSITIVE_ORDER
                ).thenComparing(
                        Contacto::getApellidoMaterno,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        // Recorremos únicamente los contactos existentes.
        for (int i = 0; i < contadorContactos; i++) {
            Contacto contacto = contactos[i];
            System.out.println(
                    contacto.getNombre() + " "
                            + contacto.getApellidoPaterno() + " "
                            + contacto.getApellidoMaterno() + " - "
                            + contacto.getTelefono()
            );
        }
    }


    /**
     * Elimina un contacto de la agenda.
     * Se utiliza equals() de la clase Contacto para determinar
     * si el contacto almacenado es igual al contacto recibido.
     * Después de eliminarlo, los contactos posteriores se
     * desplazan una posición hacia la izquierda para evitar
     * espacios vacíos dentro del arreglo.
     *
     * @param contacto contacto que se desea eliminar
     */
    public void eliminarContacto(Contacto contacto) {
        // Verificamos que el contacto recibido sea válido.
        if (contacto == null) {
            System.out.println("El contacto no es válido.");
            return;
        }
        // Verificamos si la agenda está vacía.
        if (contactos == null || contadorContactos == 0) {
            System.out.println("La agenda no tiene contactos.");
            return;
        }
        // Buscamos únicamente entre los contactos existentes.
        for (int i = 0; i < contadorContactos; i++) {
            /*
             * equals() ya está implementado en Contacto.java.
             * Por eso no necesitamos volver a comparar aquí
             * nombre y apellidos manualmente.
             */
            if (contactos[i].equals(contacto)) {
                /*
                 * Movemos hacia la izquierda todos los contactos
                 * que están después del contacto eliminado.
                 */
                for (int j = i; j < contadorContactos - 1; j++) {
                    contactos[j] = contactos[j + 1];
                }
                // Disminuimos la cantidad real de contactos.
                contadorContactos--;
                // Dejamos libre la última posición ocupada.
                contactos[contadorContactos] = null;
                System.out.println("Contacto eliminado correctamente.");
                return;
            }
        }
        // Si terminó el ciclo y no hubo coincidencia.
        System.out.println("El contacto no existe en la agenda.");
    }

}