package com.fitnesstime.fitnesstime.Modelo;

import com.orm.SugarRecord;


/**
 * Created by julian on 03/04/16.
 */
public class EjercicioCarga{

    private String nombre;
    private int series;
    private String diaDeLaSemana;
    private int repeticiones;

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

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
