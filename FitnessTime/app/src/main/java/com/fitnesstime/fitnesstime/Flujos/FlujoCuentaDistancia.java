package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityCuentaDistancia;
import com.fitnesstime.fitnesstime.ModelosFlujo.CuentaDistancia;

import java.util.ArrayList;

/**
 * Created by julian on 21/03/16.
 */
public class FlujoCuentaDistancia extends Flujo<CuentaDistancia> {

    public FlujoCuentaDistancia() {
        super();
        activitys = new ArrayList<>();

        activitys.add(ActivityCuentaDistancia.class);
    }

    @Override
    public CuentaDistancia getEntidad() {
        return entidad;
    }

    @Override
    public CuentaDistancia crearEntidad() {
        return new CuentaDistancia();
    }
}
