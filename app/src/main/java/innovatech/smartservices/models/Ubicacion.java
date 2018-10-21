package innovatech.smartservices.models;

public class Ubicacion {

    // datos de ubicacion exactos generagos por google
    private double latitud; // se obtienen por medio de place.getLng()
    private double longitud; //// se obtienen por medio de place.getLng()
    private String direccion; // Returns a human readable address for this Place. place.getAddress()
    private String nombre;

    public Ubicacion(){

    }
    public Ubicacion(String direccion, String nombre, double latitud, double longitud) {
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
