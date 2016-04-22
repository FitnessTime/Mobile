package com.fitnesstime.fitnesstime.Modelo;

import com.orm.SugarRecord;

/**
 * Created by julian on 23/02/16.
 */

public class Rutina{

    private String descripcion = "";
    private String aclaracion = "";
    private String fechaInicio = "";
    private String fechaFin = "";
    private boolean esDeCarga = false;
    private String idUsuario = "";

    public Rutina(){

    }


    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean getEsDeCarga() {
        return esDeCarga;
    }

    public void setEsDeCarga(boolean esDeCarga) {
        this.esDeCarga = esDeCarga;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isEsDeCarga() {
        return esDeCarga;
    }

}
