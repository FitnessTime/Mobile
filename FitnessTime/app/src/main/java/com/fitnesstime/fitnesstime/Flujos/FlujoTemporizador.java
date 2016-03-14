package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityTemporizador;
import com.fitnesstime.fitnesstime.ModelosFlujo.Temporizador;

import java.util.ArrayList;

/**
 * Created by julian on 13/03/16.
 */
public class FlujoTemporizador extends Flujo<Temporizador> {

    public FlujoTemporizador() {
        super();
        activitys = new ArrayList<>();
        activitys.add(ActivityTemporizador.class);
    }

    @Override
    public Temporizador getEntidad() {
        return entidad;
    }

    @Override
    public Temporizador crearEntidad() {
        return new Temporizador();
    }
}
