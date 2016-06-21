package com.fitnesstime.fitnesstime.Modelo;

import com.fitnesstime.fitnesstime.Dominio.Ejercicio;

/**
 * Created by julian on 15/06/16.
 */
public class EjercicioMarca {

    private Ejercicio ejercicio;
    private int mejorMarca;

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getMejorMarca() {
        return mejorMarca;
    }

    public void setMejorMarca(int mejorMarca) {
        this.mejorMarca = mejorMarca;
    }
}
