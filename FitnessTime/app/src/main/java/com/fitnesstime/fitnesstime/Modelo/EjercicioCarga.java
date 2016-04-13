package com.fitnesstime.fitnesstime.Modelo;

import com.orm.SugarRecord;

/**
 * Created by julian on 03/04/16.
 */
public class EjercicioCarga extends Ejercicio{

    private int repeticiones;

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }
}
