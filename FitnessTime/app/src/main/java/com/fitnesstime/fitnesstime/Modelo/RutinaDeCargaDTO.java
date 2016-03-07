package com.fitnesstime.fitnesstime.Modelo;

import java.util.Date;

/**
 * Created by julian on 04/03/16.
 */
public class RutinaDeCargaDTO {

    private int id;
    private Date inicio;
    private Date fin;
    private String descripcion;
    private String aclaracion;
    private boolean estaSincronizado;
    private long version;

    public RutinaDeCargaDTO(int id, Date inicio, Date fin, String descripcion, int version)
    {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
        this.descripcion = descripcion;
        this.aclaracion = "";
        this.estaSincronizado = false;
        this.version = version;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAclaracion() {
        return aclaracion;
    }

    public void setAclaracion(String aclaracion) {
        this.aclaracion = aclaracion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEstaSincronizado() {
        return estaSincronizado;
    }

    public void setEstaSincronizado(boolean estaSincronizado) {
        this.estaSincronizado = estaSincronizado;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
