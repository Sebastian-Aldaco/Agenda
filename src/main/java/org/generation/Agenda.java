package org.generation;

import java.util.Arrays;
import java.util.Comparator;

public class Agenda {

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
        // Comprobamos si realmente existen contactos.
        if (contactos == null || contadorcontactos == 0) {
            System.out.println("La agenda no tiene contactos.");
            return;
        }
        /*
         * Ordenamos solamente la parte ocupada del arreglo.
         * Desde la posición 0 hasta contadorcontactos.
         * De esta manera no intentamos ordenar las posiciones null.
         */
        Arrays.sort(
                contactos,
                0,
                contadorcontactos,
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

        // Mostramos únicamente los contactos existentes.
        for (int i = 0; i < contadorcontactos; i++) {
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
     * Método para buscar un contacto por nombre.
     * NOTA:
     * Se mantiene temporalmente la implementación recibida
     * de los compañeros.
     *
     * @param nombre nombre que se desea buscar
     * @return contacto encontrado o null
     */
    public Contacto buscaContacto(String nombre) {
        return null;
    }

    /**
     * Elimina un contacto de la agenda.
     * Se utiliza equals() de Contacto para determinar si el
     * contacto almacenado coincide con el contacto recibido.
     * Después de eliminarlo se recorren hacia la izquierda
     * los elementos posteriores para evitar espacios vacíos
     * dentro del arreglo.
     *
     * @param contacto contacto que se desea eliminar
     */
    public void eliminarContacto(Contacto contacto) {

        // Validamos el parámetro recibido.
        if (contacto == null) {
            System.out.println("El contacto no es válido.");
            return;
        }
        // Comprobamos si la agenda está vacía.
        if (contactos == null || contadorcontactos == 0) {
            System.out.println("La agenda no tiene contactos.");
            return;
        }
        // Buscamos solamente entre las posiciones ocupadas.
        for (int i = 0; i < contadorcontactos; i++) {
            /*
             * equals() pertenece a Contacto.java.
             * Si está implementado correctamente, aquí no tenemos
             * que repetir la comparación de nombre y apellidos.
             */
            if (contactos[i].equals(contacto)) {
                /*
                 * Desplazamos hacia la izquierda todos los contactos
                 * posteriores al que eliminamos.
                 */
                for (int j = i; j < contadorcontactos - 1; j++) {
                    contactos[j] = contactos[j + 1];
                }
                // Ahora existe un contacto menos.
                contadorcontactos--;
                // Liberamos la última posición que estaba ocupada.
                contactos[contadorcontactos] = null;
                System.out.println(
                        "Contacto eliminado correctamente."
                );
                return;
            }
        }
        // El ciclo terminó sin encontrar coincidencias.
        System.out.println(
                "El contacto no existe en la agenda."
        );
    }
}