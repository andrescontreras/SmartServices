package innovatech.smartservices.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String id;
    private String nombre;
    private int cedula;
    private String ciudad;
    private String direccion;
    private String barrio;
    private int telefono;
    private String email;
    private List<String>idServicios = new ArrayList<String>();
    List<UsuarioxServicio> relacionServicio=new ArrayList<UsuarioxServicio>();

    public Usuario(){

    }

    public Usuario(String id,String nombre, int cedula, String ciudad, String direccion, String barrio, int telefono, String email) {
        this.id=id;
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
    }
    public List<String>getListaIDServicios(){
        return this.idServicios;
    }
    public String getServicio(String id){
        return null;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return this.id;
    }
    public void setListaServicios(List<String> servicios){
        this.idServicios = servicios;
    }
    public void setServicio(String idServicio){
        this.idServicios.add(idServicio);
    }

    public List<String> getIdServicios() {
        return idServicios;
    }

    public void setIdServicios(List<String> idServicios) {
        this.idServicios = idServicios;
    }

    public List<UsuarioxServicio> getRelacionServicio() {
        return relacionServicio;
    }

    public void setRelacionServicio(List<UsuarioxServicio> relacionServicio) {
        this.relacionServicio = relacionServicio;
    }
}

