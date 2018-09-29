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

    public Servicio(){

    }

    public Servicio(String descripcion, String nombre, int precio, int promedioCalificacion) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.promedioCalificacion = promedioCalificacion;
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

}
