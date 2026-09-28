package org.generation;
import java.security.PublicKey;

public class Agenda {
    private Contacto [] contactos;
    private int tamanoMaximo;

    /**
     * Metodo constructor por defecto
     */
    public Agenda(){}

    /**
     * Método constructor para crear agenda y el tamaño maximo de la agenda
     * @param _contactos Arreglo vacío que almacenara los contactos
     * @param _tamanoMaximo Número de contactos maximo que se almacenaran en el arreglo de contactos
     */
    public Agenda(Contacto [] _contactos, int _tamanoMaximo){
        this.contactos = _contactos;
        this.tamanoMaximo = _tamanoMaximo;
    }

    /**
     * Método para almacenar un contacto nuevo en la agenda de contactos(arreglo contactos)
     * @param _contacto objeto de tipo contactos que viene desde la clase main con los datos del contacto a almacenar
     */
    public void setTamanoMaximo(Contacto _contacto){

    }

    /**
     * Método para validar si un contacto ya existe dentro de la agenda de contactos
     * @param _contacto Objeto de tipo contacto que tre la información del contacto y que se desea validar
     * @return devuelve true si el contacto ya exite almacenado en la agenda o false si el contacto no existe en la agenda
     */
    public boolean existeContacto(Contacto _contacto){

    }

    /**
     * Muestra en pantalla todos los contactos almacenados en la agenda, con sus respectivos datos de cada uno
     */
    public void listarContactos(){

    }

    /**
     * Método para buscar un contacto dentro de la agenda por nombre y apellido
     * @param nombre valor del nombre que se buscara dentro de la agenda
     * @param apellido valor del apellido que se buscara dentro de la agenda
     * @return si encuentra el contacto devolvera un objeto de tipo contacto con todos sus datos y en caso contrario
     * devolvera un mensaje confirmando que no se encontro ningun contacto con los datos proporcionados
     */
    public Contacto buscarContacto(String nombre, String apellido){

    }

    /**
     * Metodo para eliminar un contacto de la agenda
     * @param contacto variable de tipo Contacto que trae la informnacion del contacto que se debera buscar y eliminar
     *                 en la agenda(arreglo contactos)
     */
    public void eliminarContacto(Contacto contacto){

    }

    /** Anahi
     * Método para modificar un contacto de la agenda, deberá validarse primeramente que exista
     * @param nombre    valor del nombre del contacto que se desea modificar
     * @param apellido valor del apellido del contacto que se desea modificar
     * @param nuevoTelefono valor del nuevo teléfono del contacto
     */
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono){
        if (this.contactos == null) {
            System.out.println(" La agenda no tiene contactos registrados.");
            return;
        }

        boolean encontrado = false;

        for (Contacto c : this.contactos) {
            if (c != null && c.getNombre() != null && c.getApellidoPaterno() != null) {
                if (c.getNombre().equalsIgnoreCase(nombre) && c.getApellidoPaterno().equalsIgnoreCase(apellido)) {
                    c.setTelefono(nuevoTelefono);
                    System.out.println(" Teléfono actualizado con éxito para " + nombre + " " + apellido + ": " + nuevoTelefono);
                    encontrado = true;
                    break;
                }
            }
        }

        if (!encontrado) {
            System.out.println(" No se encontró ningún contacto con el nombre: " + nombre + " " + apellido);
        }
    }

    /**
     * Método para validar si la agenda esta llena
     * @return devuelve true si la agenda está llena o false si la agenda aún tiene espacios para almacenar más contactos
     */
    public boolean agendaLlena(){
        if (this.contactos == null) {
            System.out.println("️ La agenda no está inicializada o no tiene espacio ocupado.");
            return false;
        }

        int cont = 0;
        for (Contacto c : this.contactos) {
            if (c != null) {
                cont++;
            }
        }

        if (cont >= this.tamanoMaximo) {
            System.out.println("⚠️ La agenda está llena. No hay espacio disponible para nuevos contactos.");
            return true;
        } else {
            System.out.println(" La agenda aún tiene espacio disponible.");
            return false;
        }
    }

    /**
     * Método para validar cuantos espacios libres tiene la agenda si retorna un numero mayor que cero
     * aún cuenta con espacios, si el numero retornado es menor o igual a cero significa que la agenda esta llena.
     * @return
     */
    public int espaciosLibres(){
        if (this.contactos == null) {
            System.out.println(" Espacios disponibles en la agenda: " + this.tamanoMaximo + " de " + this.tamanoMaximo);
            return this.tamanoMaximo;
        }

        int cont = 0;
        for (Contacto c : this.contactos) {
            if (c != null) {
                cont++;
            }
        }

        int libres = this.tamanoMaximo - cont;
        System.out.println(" Espacios disponibles en la agenda: " + libres + " de " + this.tamanoMaximo);
        return libres;
    }
}
