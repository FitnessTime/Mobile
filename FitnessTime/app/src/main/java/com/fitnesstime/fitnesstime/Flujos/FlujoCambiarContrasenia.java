package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityCambiarContrasenia;
import com.fitnesstime.fitnesstime.ModelosFlujo.CambiarContrasenia;

import java.util.ArrayList;

/**
 * Created by julian on 27/03/16.
 */
public class FlujoCambiarContrasenia extends Flujo<CambiarContrasenia> {

    public FlujoCambiarContrasenia() {
        super();
        activitys = new ArrayList<>();

        activitys.add(ActivityCambiarContrasenia.class);
    }

    @Override
    public CambiarContrasenia getEntidad() {
        return entidad;
    }

    @Override
    public CambiarContrasenia crearEntidad() {
        return new CambiarContrasenia();
    }
}
