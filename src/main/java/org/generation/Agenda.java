package org.generation;

import java.util.Arrays;
import java.util.Comparator;

public class Agenda {

    // =====================================================
    // PARTE DE ROSARIO
    // listarContactos() + eliminarContacto()
    // =====================================================

    private Contacto[] contactos;
    private int tamanoMaximo;
    private int contadorContactos; // Para ubicar la posición

    /**
     * Metodo constructor por defecto
     */
    public Agenda() {
        this(10);
    }

    /**
     * Método constructor indicando el tamaño máximo
     *
     * @param _tamanoMaximo Número de contactos maximo que se almacenaran en el arreglo de contactos
     */
    public Agenda(int _tamanoMaximo) {
        if (_tamanoMaximo < 0) {
            throw new IllegalArgumentException("El tamaño máximo no puede ser negativo.");
        }
        this.tamanoMaximo = _tamanoMaximo;
        this.contactos = new Contacto[_tamanoMaximo];
        this.contadorContactos = 0;
    }

    /**
     * Método constructor para crear agenda y el tamaño maximo de la agenda
     *
     * @param _contactos Arreglo que almacenara los contactos
     * @param _tamanoMaximo Número de contactos maximo que se almacenaran en el arreglo de contactos
     */
    public Agenda(Contacto[] _contactos, int _tamanoMaximo) {
        this(_tamanoMaximo);
        if (_contactos != null) {
            for (Contacto contacto : _contactos) {
                if (contacto != null && contadorContactos < tamanoMaximo) {
                    contactos[contadorContactos] = contacto;
                    contadorContactos++;
                }
            }
        }
    }

    /**
     * Método para almacenar un contacto nuevo en la agenda de contactos(arreglo contactos)
     *
     * @param _contacto objeto de tipo contactos que viene desde la clase main con los datos del contacto a almacenar
     */
    public void anadirContacto(Contacto _contacto) {
        if (_contacto == null) {
            System.out.println("Error: El contacto no puede ser nulo.");
            return;
        }

        if (_contacto.getNombre() == null || _contacto.getNombre().trim().isEmpty() ||
                _contacto.getApellidoPaterno() == null || _contacto.getApellidoPaterno().trim().isEmpty()) {
            System.out.println("Error: El nombre y el apellido paterno no pueden estar vacíos.");
            return;
        }

        if (agendaLlena()) {
            System.out.println("La agenda está llena. No se pueden agregar más contactos.");
            return;
        }

        if (existeContacto(_contacto)) {
            System.out.println("No se puede añadir: El contacto ya existe en la agenda.");
            return;
        }

        contactos[contadorContactos] = _contacto;
        contadorContactos++;
        System.out.println("Contacto añadido exitosamente.");
    }

    /**
     * Método para validar si un contacto ya existe dentro de la agenda de contactos
     *
     * @param _contacto Objeto de tipo contacto que trae la información del contacto
     * @return devuelve true si el contacto ya existe almacenado en la agenda
     */
    public boolean existeContacto(Contacto _contacto) {
        if (_contacto == null) {
            return false;
        }
        for (int i = 0; i < contadorContactos; i++) {
            if (contactos[i].equals(_contacto)) {
                return true;
            }
        }
        return false;
    }

    public boolean existeContacto(String nombre, String apellidoPaterno) {
        for (int i = 0; i < contadorContactos; i++) {
            if (coincide(contactos[i].getNombre(), nombre) &&
                    coincide(contactos[i].getApellidoPaterno(), apellidoPaterno)) {
                return true;
            }
        }
        return false;
    }

    public Contacto[] obtenerContactos() {
        return Arrays.copyOf(contactos, contadorContactos);
    }

    public int getTamanoMaximo() {
        return tamanoMaximo;
    }

    public boolean agregarContacto(Contacto contacto) {
        if (!esValido(contacto) || agendaLlena() || existeContacto(contacto)) {
            return false;
        }

        contactos[contadorContactos++] = contacto;
        return true;
    }

    public boolean actualizarContacto(Contacto original, Contacto actualizado) {
        if (!esValido(actualizado) || original == null) {
            return false;
        }

        int indiceOriginal = -1;
        for (int i = 0; i < contadorContactos; i++) {
            if (contactos[i] == original) {
                indiceOriginal = i;
                break;
            }
        }
        if (indiceOriginal < 0) {
            return false;
        }

        for (int i = 0; i < contadorContactos; i++) {
            if (i != indiceOriginal && contactos[i].equals(actualizado)) {
                return false;
            }
        }

        copiarDatos(actualizado, contactos[indiceOriginal]);
        return true;
    }

    public boolean quitarContacto(Contacto contacto) {
        if (contacto == null) {
            return false;
        }

        for (int i = 0; i < contadorContactos; i++) {
            if (contactos[i] == contacto) {
                for (int j = i; j < contadorContactos - 1; j++) {
                    contactos[j] = contactos[j + 1];
                }
                contactos[--contadorContactos] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Muestra en pantalla todos los contactos almacenados en la agenda, con sus respectivos datos
     */
    public void listarContactos() {
        if (contadorContactos == 0) {
            System.out.println("La agenda no tiene contactos.");
            return;
        }

        Arrays.sort(
                contactos,
                0,
                contadorContactos,
                Comparator.comparing(Contacto::getNombre, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(Contacto::getApellidoPaterno, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(Contacto::getApellidoMaterno, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER))
        );

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
     * Método para buscar un contacto por nombre.
     *
     * @param nombre nombre que se desea buscar
     * @return contacto encontrado o null
     */
    public Contacto buscaContacto(String nombre) {
        for (int i = 0; i < contadorContactos; i++) {
            Contacto contacto = contactos[i];
            if (coincide(contacto.getNombre(), nombre)) {
                mostrarContacto(contacto);
                return contacto;
            }
        }
        System.out.println("No se encontró ningún contacto con el nombre: " + nombre);
        return null;
    }

    /**
     * Método para buscar un contacto dentro de la agenda por nombre y apellidos
     *
     * @param nombre valor del nombre que se buscara dentro de la agenda
     * @param apellidoMaterno apellido materno del contacto
     * @param apellidoPaterno apellido paterno del contacto
     */
    public void buscarContacto(String nombre, String apellidoMaterno, String apellidoPaterno) {
        for (int i = 0; i < contadorContactos; i++) {
            Contacto contacto = contactos[i];
            if (coincide(contacto.getNombre(), nombre) &&
                    coincide(contacto.getApellidoMaterno(), apellidoMaterno) &&
                    coincide(contacto.getApellidoPaterno(), apellidoPaterno)) {
                mostrarContacto(contacto);
                return;
            }
        }
        System.out.println("No se encontró ningún contacto con el nombre: " + nombre);
    }

    /**
     * Elimina un contacto de la agenda.
     *
     * @param contacto contacto que se desea eliminar
     */
    public void eliminarContacto(Contacto contacto) {
        if (contacto == null) {
            System.out.println("El contacto no es válido.");
            return;
        }

        for (int i = 0; i < contadorContactos; i++) {
            if (contactos[i].equals(contacto)) {
                eliminarContactoEnIndice(i);
                return;
            }
        }
        System.out.println("El contacto no existe en la agenda.");
    }

    public void eliminarContacto(String nombre, String apellidoPaterno) {
        for (int i = 0; i < contadorContactos; i++) {
            if (coincide(contactos[i].getNombre(), nombre) &&
                    coincide(contactos[i].getApellidoPaterno(), apellidoPaterno)) {
                eliminarContactoEnIndice(i);
                return;
            }
        }
        System.out.println("El contacto no existe en la agenda.");
    }

    private void eliminarContactoEnIndice(int indice) {
        for (int i = indice; i < contadorContactos - 1; i++) {
            contactos[i] = contactos[i + 1];
        }
        contadorContactos--;
        contactos[contadorContactos] = null;
        System.out.println("Contacto eliminado correctamente.");
    }

    /**
     * Método para modificar un contacto de la agenda, deberá validarse primeramente que exista
     *
     * @param nombre valor del nombre del contacto que se desea modificar
     * @param apellido valor del apellido del contacto que se desea modificar
     * @param nuevoTelefono valor del nuevo teléfono del contacto
     */
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        for (int i = 0; i < contadorContactos; i++) {
            Contacto contacto = contactos[i];
            if (coincide(contacto.getNombre(), nombre) &&
                    coincide(contacto.getApellidoPaterno(), apellido)) {
                contacto.setTelefono(nuevoTelefono);
                System.out.println("Teléfono actualizado con éxito para " + nombre + " " + apellido + ": " + nuevoTelefono);
                return;
            }
        }
        System.out.println("No se encontró ningún contacto con el nombre: " + nombre + " " + apellido);
    }

    /**
     * Método para validar si la agenda esta llena
     *
     * @return devuelve true si la agenda está llena o false si la agenda aún tiene espacios
     */
    public boolean agendaLlena() {
        return contadorContactos >= tamanoMaximo;
    }

    /**
     * Método para validar cuantos espacios libres tiene la agenda
     *
     * @return espacios disponibles para almacenar contactos
     */
    public int espacioLibres() {
        return tamanoMaximo - contadorContactos;
    }

    public int espaciosLibres() {
        return espacioLibres();
    }

    private boolean coincide(String valor, String busqueda) {
        return valor != null && busqueda != null && valor.equalsIgnoreCase(busqueda);
    }

    private boolean esValido(Contacto contacto) {
        return contacto != null
                && contacto.getNombre() != null && !contacto.getNombre().trim().isEmpty()
                && contacto.getApellidoPaterno() != null && !contacto.getApellidoPaterno().trim().isEmpty();
    }

    private void copiarDatos(Contacto origen, Contacto destino) {
        destino.setNombre(origen.getNombre());
        destino.setApellidoPaterno(origen.getApellidoPaterno());
        destino.setApellidoMaterno(origen.getApellidoMaterno());
        destino.setTelefono(origen.getTelefono());
        destino.setCorreo(origen.getCorreo());
        destino.setDireccion(origen.getDireccion());
        destino.setAnioNacimiento(origen.getAnioNacimiento());
        destino.setTelefonoDeEmergencia(origen.getTelefonoDeEmergencia());
    }

    private void mostrarContacto(Contacto contacto) {
        System.out.println("--- Contacto encontrado ---");
        System.out.println("Nombre: " + contacto.getNombre());
        System.out.println("Apellido paterno: " + contacto.getApellidoPaterno());
        System.out.println("Apellido materno: " + contacto.getApellidoMaterno());
        System.out.println("Teléfono: " + contacto.getTelefono());
    }
}
