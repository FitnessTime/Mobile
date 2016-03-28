package com.fitnesstime.fitnesstime.ModelosFlujo;

import android.location.Location;

/**
 * Created by julian on 21/03/16.
 */
public class CuentaDistancia {

    private Location posicion;
    private double distanciaAnterior = 0.0;

    public Location getPosicion() {
        return posicion;
    }

    public void setPosicion(Location posicion) {
        this.posicion = posicion;
    }

    public double getDistanciaAnterior() {
        return distanciaAnterior;
    }

    public void setDistanciaAnterior(double distanciaAnterior) {
        this.distanciaAnterior = distanciaAnterior;
    }
}
