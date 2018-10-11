package innovatech.smartservices.models;

public class Usuario {

    private String nombre;
    private int cedula;
    private String ciudad;
    private String direccion;
    private String barrio;
    private int telefono;
    private String email;

    public Usuario(){

    }

    public Usuario(String nombre, int cedula, String ciudad, String direccion, String barrio, int telefono, String email) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.barrio = barrio;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        /*Sensei es cagá*/
        //linea de conflicto
        // conflicto

    }
}

