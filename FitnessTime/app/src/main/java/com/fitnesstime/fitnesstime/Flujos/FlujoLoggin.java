package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityLoggin;
import com.fitnesstime.fitnesstime.ModelosFlujo.Loggin;

import java.util.ArrayList;

/**
 * Created by julian on 30/11/15.
 */
public class FlujoLoggin extends Flujo<Loggin> {

    public FlujoLoggin() {
        super();
        activitys = new ArrayList<Class<? extends ActivityFlujo>>();

        activitys.add(ActivityLoggin.class);
    }

    @Override
    public Loggin getEntidad() {
        return entidad;
    }

    @Override
    public Loggin crearEntidad() {
        return new Loggin();
    }
}
