package innovatech.smartservices.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String id;
    private String nombre;
    private String cedula;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String email;
    private List<String>srvPublicados = new ArrayList<String>();
    private List<String>srvSolicitados = new ArrayList<String>();

    public Usuario(){

    }

    public Usuario(String id,String nombre, String cedula, String ciudad, String direccion, String telefono, String email) {
        this.id=id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return this.id;
    }
    public void setServicio(String idServicio){
        this.srvPublicados.add(idServicio);
    }

    public void setServicioSolicitado(String reservado){this.srvSolicitados.add(reservado);}

    public List<String> getSrvPublicados() {
        return srvPublicados;
    }

    public void setSrvPublicados(List<String> srvPublicados) {
        this.srvPublicados = srvPublicados;
    }

    public List<String> getSrvSolicitados() {
        return srvSolicitados;
    }

    public void setSrvSolicitados(List<String> srvSolicitados) {
        this.srvSolicitados = srvSolicitados;
    }
}

