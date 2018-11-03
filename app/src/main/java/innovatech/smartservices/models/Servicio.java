package innovatech.smartservices.models;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Servicio {
    String id;
    String idUsuario;
    String incluye;
    String noIncluye;
    String adicional;
    String nombre;
    int precio;
    int promedioCalificacion;
    List<String> fotos= new ArrayList<String>();
    String fechaActivacion;
    Boolean posicionamiento;
    String tipo;
    List<Ubicacion> ubicacion=new ArrayList<>();
    List<Integer> disponibilidadDias=new ArrayList<>();
    List<Integer> disponibilidadHoras=new ArrayList<>();
    List<UsuarioxServicio> relacionUsuario=new ArrayList<UsuarioxServicio>();

    public Servicio(){

    }

    public Servicio(String id,String incluye, String noIncluye, String adicional, String nombre, int precio, int promedioCalificacion, List<String> fotos, String fechaActivacion, Boolean posicionamiento, String tipo, List<Ubicacion> ubicacion, List<Integer> disponibilidadDias, List<Integer> disponibilidadHoras) {
        this.id=id;
        this.incluye = incluye;
        this.noIncluye = noIncluye;
        this.adicional = adicional;
        this.nombre = nombre;
        this.precio = precio;
        this.promedioCalificacion = promedioCalificacion;
        this.fotos = fotos;
        this.fechaActivacion = fechaActivacion;
        this.posicionamiento = posicionamiento;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.disponibilidadDias = disponibilidadDias;
        this.disponibilidadHoras = disponibilidadHoras;
    }

    public String getIncluye() {
        return incluye;
    }

    public void setIncluye(String incluye) {
        this.incluye = incluye;
    }

    public String getNoIncluye() {
        return noIncluye;
    }

    public void setNoIncluye(String noIncluye) {
        this.noIncluye = noIncluye;
    }

    public String getAdicional() {
        return adicional;
    }

    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getPromedioCalificacion() {
        return promedioCalificacion;
    }

    public void setPromedioCalificacion(int promedioCalificacion) {
        this.promedioCalificacion = promedioCalificacion;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(String fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public Boolean getPosicionamiento() {
        return posicionamiento;
    }

    public void setPosicionamiento(Boolean posicionamiento) {
        this.posicionamiento = posicionamiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Ubicacion> getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(List<Ubicacion> ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Integer> getDisponibilidadDias() {
        return disponibilidadDias;
    }

    public void setDisponibilidadDias(List<Integer> disponibilidadDias) {
        this.disponibilidadDias = disponibilidadDias;
    }

    public List<Integer> getDisponibilidadHoras() {
        return disponibilidadHoras;
    }

    public void setDisponibilidadHoras(List<Integer> disponibilidadHoras) {
        this.disponibilidadHoras = disponibilidadHoras;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return this.id;
    }
    public void setIdUsuario(String id){
        this.idUsuario= id;
    }
    public String getIdUsuario(){
        return this.idUsuario;
    }
    public void addDias(int dia){
        this.disponibilidadDias.add(dia);
    }
    public void addUbicacion(Ubicacion ubi){
        this.ubicacion.add(ubi);
    }
    public void addHoras(int hora){
        this.disponibilidadHoras.add(hora);
    }
    public void addImagen(String imagen){
        fotos.add(imagen);
    }

    public List<UsuarioxServicio> getRelaciones() {
        return relacionUsuario;
    }

    public void setRelaciones(List<UsuarioxServicio> relacionUsuario) {
        this.relacionUsuario = relacionUsuario;
    }
}




