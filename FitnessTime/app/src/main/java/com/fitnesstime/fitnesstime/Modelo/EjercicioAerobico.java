package com.fitnesstime.fitnesstime.Modelo;

import io.realm.RealmObject;
import io.realm.annotations.*;

/**
 * Created by julian on 10/04/16.
 */
public class EjercicioAerobico extends RealmObject {

    private String nombre;
    private int series;
    private String diaDeLaSemana;
    private int tiempoActivo;
    private int tiempoDescanso;

    public int getTiempoActivo() {
        return tiempoActivo;
    }

    public void setTiempoActivo(int tiempoActivo) {
        this.tiempoActivo = tiempoActivo;
    }

    public int getTiempoDescanso() {
        return tiempoDescanso;
    }

    public void setTiempoDescanso(int tiempoDescanso) {
        this.tiempoDescanso = tiempoDescanso;
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
