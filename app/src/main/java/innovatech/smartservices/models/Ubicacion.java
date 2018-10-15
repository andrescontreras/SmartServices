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

    public Ubicacion(String posicionGPS, String ubicacion, String direccion) {
        PosicionGPS = posicionGPS;
        Ubicacion = ubicacion;
        Direccion = direccion;
    }

    public String getApto() {
        return apto;
    }

    public void setApto(String apto) {
        this.apto = apto;
    }

    public String getPosicionGPS() {
        return PosicionGPS;
    }

    public void setPosicionGPS(String posicionGPS) {
        PosicionGPS = posicionGPS;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
}
