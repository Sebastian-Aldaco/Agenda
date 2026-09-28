package org.generation;

import java.util.ArrayList;

public class Contacto {    private String nombre;
    private String apellido;
    private Integer telefono;
    public static ArrayList<Contacto> listaContactos = new ArrayList<Contaco>();

    public Contacto(String nombre, String apellido, Integer telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public Contacto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
    public static boolean existeContacto(Contacto c) {
        return listaContactos.contains(c);
    }

    public static void crearContacto(Contacto contacto) {
        if (listaContactos.size() >= tamanoMaximo) {
            System.out.println("La agenda está llena, no se pueden añadir más contactos.");
            return;
        }
        if (existeContacto(contacto)) {
            System.out.println("El contacto ya existe en la agenda.");
        } else {
            listaContactos.add(contacto);
            System.out.println("Contacto agregado");
        }
    }
    public static void buscarContacto(String nombre) {
        boolean encontrado = false;

        for (Contacto c : listaContactos) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("--- Contacto encontrado ---");
                System.out.println("Nombre: " + c.getNombre());
                System.out.println("Teléfono: " + c.getTelefono());
                encontrado = true;
                break; // Una vez encontrado, detenemos la búsqueda
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún contacto con el nombre: " + nombre);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Contacto otro = (Contacto) obj;
        return this.nombre.equalsIgnoreCase(otro.nombre);
    }


}
