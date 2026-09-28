package org.generation;

public class Agenda {
    private Contacto[] contactos;
    private int tamanoMaximo;
    private int contadorcontactos; // Para ubicar la posición

    /**
     * Metodo constructor por defecto
     */
    public Agenda() {
        this.tamanoMaximo = 10;   // Tamaño por defecto
        this.contactos = new Contacto[tamanoMaximo];
        this.contadorcontactos = 0;
    }

    /**
     * Método constructor indicando el tamaño máximo
     *
     * @param _tamanoMaximo Número de contactos maximo que se almacenaran en el arreglo de contactos
     */
    public Agenda(int _tamanoMaximo) {
        this.tamanoMaximo = _tamanoMaximo;
        this.contactos = new Contacto[_tamanoMaximo];
        this.contadorcontactos = 0;
    }

    /**
     * Método para almacenar un contacto nuevo en la agenda de coontactos(arregolo contactos)
     *
     * @param _contacto objeto de tipo contactos que viene desde la clase main con los datos del contacto a almacenar
     */
    public void anadirContacto(Contacto _contacto) {
        // Validación de campos vacíos
        /**if(_contacto == null){
            System.out.println("Error: El contacto no puede ser nulo");
            return;
        }*/

        if (_contacto.getNombre() == null || _contacto.getNombre().trim().isEmpty() ||
                _contacto.getApellidoPaterno() == null || _contacto.getApellidoPaterno().trim().isEmpty() ||
                _contacto.getApellidoMaterno() == null || _contacto.getApellidoMaterno().trim().isEmpty()) {
            System.out.println("Error: El nombre y los apellidos no pueden estar vacíos.");
            return;
        }

        // Valida agenda llena
        if (agendaLlena()) {
            System.out.println("La agenda está llena. No se pueden agregar más contactos.");
            return;
        }

        // Validación contactos duplicados
        if (existeContacto(_contacto)) {
            System.out.println("No se puede añadir: El contacto ya existe en la agenda.");
            return;
        }

        // Se agrega contacto
        contactos[contadorcontactos] = _contacto; // Guarda el contacto en la posición libre actual
        contadorcontactos++;
        System.out.println("Contacto añadido exitosamente.");
    }

    /**
     * Método para validar si un contacto ya existe dentro de la agenda de contactos
     *
     * @param _contacto Objeto de tipo contacto que tre la información del contacto y que se desea validar
     * @return devuelve true si el contacto ya exite almacenado en la agenda o false si el contacto no existe en la agenda
     */
    public boolean existeContacto(Contacto _contacto) {
        return false;
    }

    /**
     * Muestra en pantalla todos los contactos almacenados en la agenda, con sus respectivos datos de cada uno
     */
    public void listarContactos() {
    }

    /**
     * Método para buscar un contacto dentro de la agenda por nombre y apellido
     *
     * @param nombre   valor del nombre que se buscara dentro de la agenda
     * @param apellido valor del apellido que se buscara dentro de la agenda
     * @return si encuentra el contacto devolvera un objeto de tipo contacto con todos sus datos y en caso contrario
     * devolvera un mensaje confirmando que no se encontro ningun contacto con los datos proporcionados
     */
    //public Contacto buscarContacto(String nombre, String apellido){
    //return null;
    public Contacto buscaContacto(String nombre) {
        return null;
    }


    /**
     * Metodo para eliminar un contacto de la agenda
     *
     * @param contacto variable de tipo Contacto que trae la informnacion del contacto que se debera buscar y eliminar
     *                 en la agenda(arreglo contactos)
     */
    public void eliminarContacto(Contacto contacto) {
    }

    /**
     * Método para modificar un contacto de la agenda el cual devera validarse primeramente que exista
     *
     * @param nombre        valor del nombre del contacto que se decea modificar
     * @param apellido      valor del apellido del contacto que se decea modificar
     * @param nuevoTelefono valor del nuevo teléfono del contacto
     */
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono) {
        return;
    }

    /**
     * Método para validar si la agenda esta llena
     *
     * @return devuelve true si la agenda está llena o false si la agenda aún tien espacios para almacenar más contactos
     */
    public boolean agendaLlena() {
        return contadorcontactos >= tamanoMaximo;
    }

    /**
     * Método para validar cuantos espacios libres tiene la agenda si retorna un numero mayor que cero
     * aún cuenta con espacios, si el numero retornado es menor o igual a cero significa que la agenda esta llena.
     *
     * @return
     */
    public int espacioLibres() {
        return tamanoMaximo - contadorcontactos;
    }
}