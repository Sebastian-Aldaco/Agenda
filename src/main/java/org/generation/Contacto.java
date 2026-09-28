package org.generation;


import java.util.Objects;

public class Contacto {
//Se declara la clase Contacto con sus respectivos atributos a trabajar para la agenda.

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String correo;
    private String direccion;
    private Integer anioNacimiento;
    private String telefonoDeEmergencia;


// atributos publicos , cambiar a privado si es necesario



    // en poo suelen los placeholders tener el mismo nombre que el atributo

// Constructor principal de 8 campos
    public Contacto(String nombre, String apellidoPaterno , String apellidoMaterno, String telefono, String correo,
                    String direccion, Integer anioNacimiento, String telefonoDeEmergencia) {

        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.anioNacimiento = anioNacimiento;
        this.telefonoDeEmergencia = telefonoDeEmergencia;

    }

    // Constructor que recibe 3 argumentos para el Menú.java
public Contacto(String nombre, String apellidoPaterno, String telefono){
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = "";
        this.telefono = telefono;
        this.correo = "";
        this.direccion = "";
        this.anioNacimiento = 0;
        this.telefonoDeEmergencia = "";
}

    //Getter and setter

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public String getTelefonoDeEmergencia() {
        return telefonoDeEmergencia;
    }

    public void setTelefonoDeEmergencia(String telefonoDeEmergencia) {
        this.telefonoDeEmergencia = telefonoDeEmergencia;
    }


    public void showDetails(){
    } //metodo para mostrar la información de la Agenda.


    // Validación duplicados
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Contacto contacto = (Contacto) obj;
        return nombre.equalsIgnoreCase(contacto.nombre) &&
                apellidoPaterno.equalsIgnoreCase(contacto.apellidoPaterno) &&
                apellidoMaterno.equalsIgnoreCase(contacto.apellidoMaterno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                nombre != null ? nombre.toLowerCase() : "",
                apellidoPaterno != null ? apellidoPaterno.toLowerCase() : "",
                apellidoMaterno != null ? apellidoMaterno.toLowerCase() : ""
        );
    }

}




