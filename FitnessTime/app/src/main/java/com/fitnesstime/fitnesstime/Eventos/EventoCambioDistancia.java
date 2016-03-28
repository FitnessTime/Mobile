package com.fitnesstime.fitnesstime.Eventos;

import android.location.Location;

/**
 * Created by julian on 21/03/16.
 */
public class EventoCambioDistancia {

    private Location posicion;

    public EventoCambioDistancia(Location loc)
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
