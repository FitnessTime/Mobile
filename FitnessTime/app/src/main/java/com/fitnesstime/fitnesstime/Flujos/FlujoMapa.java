package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityMapa;
import com.fitnesstime.fitnesstime.ModelosFlujo.Mapa;

import java.util.ArrayList;

/**
 * Created by julian on 17/03/16.
 */
public class FlujoMapa extends Flujo<Mapa> {

    public FlujoMapa() {
        super();
        activitys = new ArrayList<>();

        activitys.add(ActivityMapa.class);
    }

    @Override
    public Mapa getEntidad() {
        return entidad;
    }

    @Override
    public Mapa crearEntidad() {
        return new Mapa();
    }
}
