package innovatech.smartservices.models;

import java.io.Serializable;

import innovatech.smartservices.helpers.RelacionEnum;

public class UsuarioxServicio implements Serializable                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    {
    String idUsuario;
    String idServicio;
    RelacionEnum relacion;

    public UsuarioxServicio(){

    }
    public UsuarioxServicio(String idUsuario, String idServicio, RelacionEnum relacion) {
        this.idUsuario = idUsuario;
        this.idServicio = idServicio;
        this.relacion = relacion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public RelacionEnum getRelacion() {
        return relacion;
    }

    public void setRelacion(RelacionEnum relacion) {
        this.relacion = relacion;
    }
}
