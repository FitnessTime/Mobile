package com.fitnesstime.fitnesstime.Adapters;

import java.util.Date;

/**
 * Created by julian on 06/03/16.
 */
public class ItemRutina {

    private Date inicioRutina;
    private Date finRutina;
    private String descripcion;

    public ItemRutina(Date inicioRutina, Date finRutina, String descripcion) {
        this.inicioRutina = inicioRutina;
        this.finRutina = finRutina;
        this.descripcion = descripcion;
    }

    public Date getInicioRutina() {
        return inicioRutina;
    }

    public void setInicioRutina(Date inicioRutina) {
        this.inicioRutina = inicioRutina;
    }

    public Date getFinRutina() {
        return finRutina;
    }

    public void setFinRutina(Date finRutina) {
        this.finRutina = finRutina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
