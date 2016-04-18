package com.fitnesstime.fitnesstime.Modelo;

import com.orm.SugarRecord;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by julian on 23/02/16.
 */

public class Rutina extends RealmObject{

    private String descripcion = "";
    private String aclaracion = "";
    private String fechaInicio = "";
    private String fechaFin = "";
    private boolean esDeCarga;
    private String idUsuario = "";
    private RealmList<EjercicioCarga> ejerciciosCarga = new RealmList<>();
    private RealmList<EjercicioAerobico> ejerciciosAerobicos = new RealmList<>();

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

    public RealmList<EjercicioCarga> getEjerciciosCarga() {
        return ejerciciosCarga;
    }

    public void setEjerciciosCarga(RealmList<EjercicioCarga> ejerciciosCarga) {
        this.ejerciciosCarga = ejerciciosCarga;
    }

    public RealmList<EjercicioAerobico> getEjerciciosAerobicos() {
        return ejerciciosAerobicos;
    }

    public void setEjerciciosAerobicos(RealmList<EjercicioAerobico> ejerciciosAerobicos) {
        this.ejerciciosAerobicos = ejerciciosAerobicos;
    }
}
