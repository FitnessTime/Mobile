package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityRegistroDatosFisicos;
import com.fitnesstime.fitnesstime.Activities.ActivityRegistroDatosPersonales;
import com.fitnesstime.fitnesstime.Activities.ActivityRegistrosFecha;
import com.fitnesstime.fitnesstime.ModelosFlujo.Registro;

import java.util.ArrayList;

/**
 * Created by julian on 07/02/16.
 */
public class FlujoRegistro extends Flujo<Registro>{

    public FlujoRegistro() {
        super();
        activitys = new ArrayList<Class<? extends ActivityFlujo>>();

        activitys.add(ActivityRegistroDatosPersonales.class);
        activitys.add(ActivityRegistrosFecha.class);
        activitys.add(ActivityRegistroDatosFisicos.class);
    }

    @Override
    public Registro getEntidad() {
        return entidad;
    }

    @Override
    public Registro crearEntidad() {
        return new Registro();
    }
}
