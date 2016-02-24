package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipalRutina;
import com.fitnesstime.fitnesstime.ModelosFlujo.Principal;
import com.fitnesstime.fitnesstime.ModelosFlujo.Rutina;

import java.util.ArrayList;

/**
 * Created by julian on 23/02/16.
 */
public class FlujoRutinas extends Flujo<Rutina> {

    public FlujoRutinas() {
        super();
        activitys = new ArrayList<Class<? extends ActivityFlujo>>();

        activitys.add(ActivityPrincipalRutina.class);
    }

    @Override
    public Rutina getEntidad() {
        return null;
    }

    @Override
    public Rutina crearEntidad() {
        return null;
    }
}
