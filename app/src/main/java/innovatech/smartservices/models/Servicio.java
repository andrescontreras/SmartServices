package innovatech.smartservices.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Servicio {

    String descripcion;
    String nombre;
    int precio;
    int promedioCalificacion;
    List<File> fotos= new ArrayList<File>();
    int fechaActivacion;
    Boolean posicionamiento;
    String tipo;
    List<Ubicacion> ubicacion;
    List<Disponiblidad>calendario;

    public Servicio(){

    }

    public Servicio(String descripcion, String nombre, int precio, int promedioCalificacion, List<File> fotos, int fechaActivacion, Boolean posicionamiento, String tipo, List<Ubicacion> ubicacion, List<Disponiblidad> calendario) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.promedioCalificacion = promedioCalificacion;
        this.fotos = fotos;
        this.fechaActivacion = fechaActivacion;
        this.posicionamiento = posicionamiento;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.calendario = calendario;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<File> getFotos() {
        return fotos;
    }

    public void setFotos(List<File> fotos) {
        this.fotos = fotos;
    }

    public int getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(int fechaActivacion) {
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

    public List<Disponiblidad> getCalendario() {
        return calendario;
    }

    public void setCalendario(List<Disponiblidad> calendario) {
        this.calendario = calendario;
    }
}

