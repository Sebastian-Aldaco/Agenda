package org.generation;


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
}




