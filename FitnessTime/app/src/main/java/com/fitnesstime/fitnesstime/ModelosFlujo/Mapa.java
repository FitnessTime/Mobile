package com.fitnesstime.fitnesstime.ModelosFlujo;

import android.location.Location;

import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by julian on 17/03/16.
 */
public class Mapa {

    private PolylineOptions linea = null;

    public PolylineOptions getLinea() {
        return linea;
    }

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

    public void setLinea(PolylineOptions linea) {
        this.linea = linea;
    }
}
