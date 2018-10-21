package innovatech.smartservices.models;

public class Ubicacion {
    private String apto;
    private String avenida;
    private String calle;
    private String edificio;
    private String nomenclaruta;

    private String PosicionGPS;
    private String Ubicacion;
    private String Direccion;
    public Ubicacion(){

    }
    public Ubicacion(String posicionGPS, String ubicacion, String direccion) {
        PosicionGPS = posicionGPS;
        Ubicacion = ubicacion;
        Direccion = direccion;
    }


    // datos de ubicacion exactos generagos por google
    private double latitud; // se obtienen por medio de place.getLng()
    private double longitud; //// se obtienen por medio de place.getLng()
    private String direccion; // Returns a human readable address for this Place. place.getAddress()

    public Ubicacion(String direccion, double latitud, double longitud) {
        direccion = direccion;
        latitud = latitud;
        longitud = longitud;
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
}
