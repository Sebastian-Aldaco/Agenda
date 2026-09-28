package org.generation;

import java.security.PublicKey;

public class Agenda {
    private Contacto [] contactos;
    private int tamañoMaximo;
    private int contadorContactos; // Para ubicar la posición

    /**
     * Metodo constructor por defecto
     */
    public Agenda(){
        // Tamaño por defecto
        this.tamañoMaximo = 10;
        this.contactos = new Contacto[tamañoMaximo];
        this.contadorContactos = 0;
    }

    /**
     * Método constructor para crear agenda y el tamaño maximo de la agenda
     * @param _contactos Arreglo vacío que almacenara los contactos
     * @param _tamañoMaximo Número de contactos maximo que se almacenaran en el arreglo de contactos
     */
    public Agenda(Contacto [] _contactos, int _tamañoMaximo){
        this.contactos = _contactos;
        this.tamañoMaximo = _tamañoMaximo;
    }

    /**
     * Método para almacenar un contacto nuevo en la agenda de coontactos(arregolo contactos)
     * @param _contacto objeto de tipo contactos que viene desde la clase main con los datos del contacto a almacenar
     */
    public void añadirContacto(Contacto _contacto){

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

    /**
     * Método para modificar un contacto de la agenda el cual devera validarse primeramente que exista
     * @param nombre    valor del nombre del contacto que se decea modificar
     * @param apellido valor del apellido del contacto que se decea modificar
     * @param nuevoTelefono valor del nuevo teléfono del contacto
     */
    public void modificarTelefono(String nombre, String apellido, String nuevoTelefono){

    }

    /**
     * Método para validar si la agenda esta llena
     * @return devuelve true si la agenda está llena o false si la agenda aún tien espacios para almacenar más contactos
     */
    public boolean agendaLlena(){

    }

    /**
     * Método para validar cuantos espacios libres tiene la agenda si retorna un numero mayor que cero
     * aún cuenta con espacios, si el numero retornado es menor o igual a cero significa que la agenda esta llena.
     * @return
     */
    public  int espaciosLibres(){

    }
}

/*
* public void addContacto(Contacto c){
    // Verifica si hay espacio
    if (agendaLlena){
        return;
    }

    // Validación de duplicados
    if (existeContacto(c)){
        System.out.println("No se puede añadir, el contacto ya existe en la agenda.");
        return;
    }

    // Arreglo
    contactos[contadorContactos] = c;
    contadorContactos++;
    System.out.println("Contacto añadido exitosamente.");
}*/