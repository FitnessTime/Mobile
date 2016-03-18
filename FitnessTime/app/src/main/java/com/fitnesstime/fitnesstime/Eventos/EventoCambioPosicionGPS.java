package com.fitnesstime.fitnesstime.Eventos;

import android.location.Location;

/**
 * Created by julian on 17/03/16.
 */
public class EventoCambioPosicionGPS
{
    private Location posicion;

    public EventoCambioPosicionGPS(Location loc)
    {
        this.posicion = loc;
    }

    public Location getPosicion() {
        return posicion;
    }

    public void setPosicion(Location posicion) {
        this.posicion = posicion;
    }
}
