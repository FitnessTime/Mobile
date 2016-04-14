package com.fitnesstime.fitnesstime.Modelo;

import io.realm.annotations.*;

/**
 * Created by julian on 10/04/16.
 */
public class EjercicioAerobico extends Ejercicio {

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
}
