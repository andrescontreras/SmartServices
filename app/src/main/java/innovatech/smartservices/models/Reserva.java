package innovatech.smartservices.models;

import innovatech.smartservices.helpers.EstadoReserva;

public class Reserva {
    String id;
    String idServicio;
    String idUsuSolicitante;
    int hora;
    String fecha;
    EstadoReserva estado;

    public Reserva(){

    }
    public Reserva(String id,String idServicio, String idUsuSolicitante, int hora, String fecha, EstadoReserva estado) {
        this.id=id;
        this.idServicio = idServicio;
        this.idUsuSolicitante = idUsuSolicitante;
        this.hora = hora;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getIdUsuSolicitante() {
        return idUsuSolicitante;
    }

    public void setIdUsuSolicitante(String idUsuSolicitante) {
        this.idUsuSolicitante = idUsuSolicitante;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
