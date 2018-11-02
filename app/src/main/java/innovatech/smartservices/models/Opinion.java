package innovatech.smartservices.models;

import java.util.Date;

public class Opinion {
    private String id;
    private String idServ;
    String usuario="";
    private int calificacion;
    private String descripcion;
    private String fecha ;

    public Opinion(){}
    public Opinion(String id, String idServ,String usuario, int calificacion, String descripcion, String fecha) {
        this.id = id;
        this.idServ = idServ;
        this.usuario = usuario;
        this.calificacion = calificacion;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdServ() {
        return idServ;
    }

    public void setIdServ(String idServ) {
        this.idServ = idServ;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
