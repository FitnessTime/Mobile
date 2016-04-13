package com.fitnesstime.fitnesstime.Modelo;

/**
 * Created by julian on 10/04/16.
 */
public abstract class Ejercicio {

    protected String nombre;
    protected int series;
    protected String diaDeLaSemana;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getDiaDeLaSemana() {
        return diaDeLaSemana;
    }

    public void setDiaDeLaSemana(String diaDeLaSemana) {
        this.diaDeLaSemana = diaDeLaSemana;
    }
}
