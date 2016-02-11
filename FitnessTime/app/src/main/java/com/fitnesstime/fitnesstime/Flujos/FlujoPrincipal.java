package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityLoggin;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipal;
import com.fitnesstime.fitnesstime.ModelosFlujo.Principal;

import java.util.ArrayList;

/**
 * Created by julian on 10/02/16.
 */
public class FlujoPrincipal extends Flujo<Principal> {

    public FlujoPrincipal() {
        super();
        activitys = new ArrayList<Class<? extends ActivityFlujo>>();

        activitys.add(ActivityPrincipal.class);
    }

    @Override
    public Principal getEntidad() {
        return entidad;
    }

    @Override
    public Principal crearEntidad() {
        return new Principal();
    }
}
