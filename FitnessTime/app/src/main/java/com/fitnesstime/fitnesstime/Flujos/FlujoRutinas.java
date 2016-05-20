package com.fitnesstime.fitnesstime.Flujos;

import com.fitnesstime.fitnesstime.Activities.ActivityEjercicio;
import com.fitnesstime.fitnesstime.Activities.ActivityFlujo;
import com.fitnesstime.fitnesstime.Activities.ActivityPrincipalRutina;
import com.fitnesstime.fitnesstime.Activities.ActivityRegistroFechaRutina;
import com.fitnesstime.fitnesstime.Application.FitnessTimeApplication;
import com.fitnesstime.fitnesstime.Dominio.Ejercicio;
import com.fitnesstime.fitnesstime.Modelo.SecurityToken;
import com.fitnesstime.fitnesstime.Dominio.Rutina;

import java.util.ArrayList;

/**
 * Created by julian on 23/02/16.
 */
public class FlujoRutinas extends Flujo<Rutina> {

    // Solo para ejercicios induviduales.
    private boolean modoIndividual = false;
    private Ejercicio ejercicio;

    public FlujoRutinas() {
        super();
        activitys = new ArrayList<Class<? extends ActivityFlujo>>();

        activitys.add(ActivityPrincipalRutina.class);
        activitys.add(ActivityRegistroFechaRutina.class);
        activitys.add(ActivityEjercicio.class);
    }

    @Override
    public Rutina getEntidad() {
        return entidad;
    }

    public void setEntidad(Rutina rutina) {
        entidad = rutina;
    }

    @Override
    public Rutina crearEntidad() {
        Rutina rutina = new Rutina();
        rutina.setEstaSincronizado(false);
        rutina.setVersionWeb(0);
        rutina.setVersion(0);
        rutina.setIdUsuario(FitnessTimeApplication.getIdUsuario());
        return rutina;
    }

    public boolean isModoIndividual() {
        return modoIndividual;
    }

    public void setModoIndividual(boolean modoIndividual) {
        this.modoIndividual = modoIndividual;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }
}
